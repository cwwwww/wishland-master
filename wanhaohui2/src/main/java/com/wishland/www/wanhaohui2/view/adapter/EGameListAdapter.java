package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.EGameListBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/12/3.
 */

public class EGameListAdapter extends SuperAdapter<EGameListBean.DataBean> {
    private Context mContext;
    private Model model;
    private UserSP userSP;
    private Handler mHandler;
    private boolean isNeedRefresh = false;
    private Activity activity;
    private String platformName = "";
    List<Integer> cbList = new ArrayList<>();


    public EGameListAdapter(Activity ctx, Model model, UserSP userSP, Handler handler, String platformName) {
        super(ctx);
        this.activity = ctx;
        this.mContext = ctx;
        this.model = model;
        this.userSP = userSP;
        this.mHandler = handler;
        this.platformName = platformName;

    }

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_egame_list, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_e_game);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        CheckBox cbCollect = (CheckBox) view.findViewById(R.id.cb_collect);
        LinearLayout llCollect = (LinearLayout) view.findViewById(R.id.ll_collect);
        final EGameListBean.DataBean gameBean = getItem(position);
        FrescoUtil.loadGifPicOnNet(simpleDraweeView, gameBean.getImg());
        tvName.setText(gameBean.getSubname());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = gameBean.getUrl();
                WebUtil.toWebGameActivity(
                        mContext,
                        url.replace("[token]", Model.getInstance().getUserSP().getString("token")).toString(),
                        HomeFragment.GAME_CATEGORY.ELECTRIC_GAME.getTitle(),
                        platformName,
                        gameBean.getSubname());

            }
        });
        if (gameBean.isFavorite() || cbList.contains(position)) {
            cbCollect.setChecked(true);
        } else {
            if (cbList.contains(position)) {
                cbCollect.setChecked(true);
            } else {
                cbCollect.setChecked(false);
            }
        }

        setCBClickListener(llCollect, cbCollect, gameBean, position);
    }

    private void setCBClickListener(LinearLayout llCollect, final CheckBox cb, final EGameListBean.DataBean itemsBean, final int position) {

        llCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == 1) {
                    isNeedRefresh = true;
                    Message message = mHandler.obtainMessage();
                    message.what = 0;
                    mHandler.sendMessage(message);
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                        cancelCollectGame(cb, itemsBean, position);
                    } else {
                        cb.setChecked(true);
                        collectGame(cb, itemsBean, position);
                    }
                } else {
                    model.skipLoginActivity(activity, LoginActivity.class, "EGameListAdapter");
                }
            }
        });
    }

    private void cancelCollectGame(final CheckBox cb, final EGameListBean.DataBean itemsBean, final int position) {
        final Message message = mHandler.obtainMessage();
        message.what = 1;
        String token = userSP.getToken("token");
        Map<String, String> map = new HashMap<>();
        map.put("fid", itemsBean.getFid());
        model.apiDeleteCollection(token, NetUtil.getParamsPro(map).get("signature"), itemsBean.getFid(), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mHandler.sendMessage(message);
                ToastUtil.showShort(mContext, "取消收藏游戏失败！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mHandler.sendMessage(message);
                try {
                    String deleteCollectGame = responseBody.string();
                    JSONObject jsonObject = model.getJsonObject(deleteCollectGame);
                    int status = jsonObject.optInt("status");
                    model.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        if (cbList.contains(position)) {
                            cbList.remove((Integer) position);
                        }
                        itemsBean.setFavorite(false);
                        ToastUtil.showShort(mContext, "取消收藏游戏成功！");
                    } else {
                        cb.setChecked(true);
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            model.skipLoginActivity(activity, LoginActivity.class, "EGameListAdapter");
                        } else {
                            ToastUtil.showShort(mContext, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void collectGame(final CheckBox cb, final EGameListBean.DataBean itemsBean, final int position) {
        final Message message = mHandler.obtainMessage();
        message.what = 1;
        String token = userSP.getToken("token");
        Map<String, String> map = new HashMap<>();
        map.put("fid", itemsBean.getFid());
        model.apiAddCollection(token, NetUtil.getParamsPro(map).get("signature"), itemsBean.getFid(), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                mHandler.sendMessage(message);
                ToastUtil.showShort(mContext, "收藏游戏失败！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mHandler.sendMessage(message);
                try {
                    String successCollectGame = responseBody.string();
                    JSONObject jsonObject = model.getJsonObject(successCollectGame);
                    int status = jsonObject.optInt("status");
                    model.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        if (!cbList.contains(position)) {
                            cbList.add(position);
                        }
                        ToastUtil.showShort(mContext, "收藏游戏成功！");
                    } else {
                        cb.setChecked(false);
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            model.skipLoginActivity(activity, LoginActivity.class, "EGameListAdapter");
                        } else {
                            ToastUtil.showShort(mContext, errorMsg);
                        }
                    }
                    Log.e("cww", successCollectGame);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
