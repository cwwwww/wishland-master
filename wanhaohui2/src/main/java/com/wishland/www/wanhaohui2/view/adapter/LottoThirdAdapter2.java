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
import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.CaipiaoBean;
import com.wishland.www.wanhaohui2.bean.MindCollectionBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/12/4.
 */

public class LottoThirdAdapter2 extends SuperAdapter<CaipiaoBean.ItemsBean.SubcreditBean> {


    private boolean isNeedRefresh = false;
    private Handler mHandler;
    private Model model;
    private UserSP userSP;
    private Activity activity;
    private Context mContext;
    private List<Integer> cbList = new ArrayList<>();


    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    public LottoThirdAdapter2(Activity ctx, Model model, UserSP userSP, Handler handler) {
        super(ctx);
        this.mHandler = handler;
        this.model = model;
        this.userSP = userSP;
        this.activity = ctx;
        this.mContext = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_caipiao_game, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_game);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        CheckBox cbCollect = (CheckBox) view.findViewById(R.id.cb_collect);
        LinearLayout llCollect = (LinearLayout) view.findViewById(R.id.ll_collect);
        CaipiaoBean.ItemsBean.SubcreditBean subgameBean = getItem(position);
        FrescoUtil.loadGifPicOnNet(simpleDraweeView, subgameBean.getImg());
        tvName.setText(subgameBean.getName());

        if (subgameBean.isFavorite() || cbList.contains(position)) {
            cbCollect.setChecked(true);
        } else {
            if (cbList.contains(position)) {
                cbCollect.setChecked(true);
            } else {
                cbCollect.setChecked(false);
            }
        }
        setCBClickListener(llCollect, cbCollect, subgameBean, position);
    }

    private void setCBClickListener(LinearLayout llCollect, final CheckBox cb, final CaipiaoBean.ItemsBean.SubcreditBean subgameBean, final int position) {

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
                        cancelCollectGame(cb, subgameBean, position);
                    } else {
                        cb.setChecked(true);
                        collectGame(cb, subgameBean, position);
                    }
                } else {
                    model.skipLoginActivity(activity, LoginActivity.class, "LottoThirdAdapter2");
                }
            }
        });
    }

    private void cancelCollectGame(final CheckBox cb, final CaipiaoBean.ItemsBean.SubcreditBean subgameBean, final int position) {
        final Message message = mHandler.obtainMessage();
        message.what = 1;
        String token = userSP.getToken("token");
        Map<String, String> map = new HashMap<>();
        map.put("fid", subgameBean.getFid());
        model.apiDeleteCollection(token, NetUtil.getParamsPro(map).get("signature"), subgameBean.getFid(), new Subscriber<ResponseBody>() {
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
                        subgameBean.setFavorite(false);
                        ToastUtil.showShort(mContext, "取消收藏游戏成功！");
                    } else {
                        cb.setChecked(true);
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            model.skipLoginActivity(activity, LoginActivity.class, "LottoThirdAdapter2");
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

    private void collectGame(final CheckBox cb, final CaipiaoBean.ItemsBean.SubcreditBean subgameBean, final int position) {
        final Message message = mHandler.obtainMessage();
        message.what = 1;
        String token = userSP.getToken("token");
        Map<String, String> map = new HashMap<>();
        map.put("fid", subgameBean.getFid());
        model.apiAddCollection(token, NetUtil.getParamsPro(map).get("signature"), subgameBean.getFid(), new Subscriber<ResponseBody>() {
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
                            model.skipLoginActivity(activity, LoginActivity.class, "LottoThirdAdapter2");
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
