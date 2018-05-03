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
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
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
 * Created by admin on 2017/12/3.
 */

public class CatchFishAdapter extends SuperAdapter<HomeGameBean.DataBean.GameBean.ItemsBean> {
    private Model instance;
    private UserSP userSP;
    private Handler mHandler;
    private Context mContext;
    private boolean isNeedRefresh = false;
    private Activity activity;
    private List<Integer> cbList = new ArrayList<>();
    private String gameImage;

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }

    public CatchFishAdapter(Activity ctx, Model instance, UserSP userSP, Handler mHandler, String gameImage) {
        super(ctx);
        this.activity = ctx;
        this.instance = instance;
        this.userSP = userSP;
        this.mHandler = mHandler;
        this.mContext = ctx;
        this.gameImage = gameImage;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_catch_fish, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_e_game);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        CheckBox cbCollect = (CheckBox) view.findViewById(R.id.cb_collect);
        LinearLayout llCollect = (LinearLayout) view.findViewById(R.id.ll_collect);
        HomeGameBean.DataBean.GameBean.ItemsBean itemsBean = getItem(position);
        FrescoUtil.loadGifPicOnNet(simpleDraweeView, itemsBean.getImg());
        tvName.setText(itemsBean.getName());
        if (itemsBean.getFavorite() || cbList.contains(position)) {
            cbCollect.setChecked(true);
        } else {
            if (cbList.contains(position)) {
                cbCollect.setChecked(true);
            } else {
                cbCollect.setChecked(false);
            }
        }
        setCBClickListener(llCollect, cbCollect, itemsBean, position);
    }

    private void setCBClickListener(LinearLayout llCollect, final CheckBox cb, final HomeGameBean.DataBean.GameBean.ItemsBean itemsBean, final int position) {

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
                    instance.skipLoginActivity(activity, LoginActivity.class, "CatchFishAdapter");
                }
            }
        });
    }

    private void cancelCollectGame(final CheckBox cb, final HomeGameBean.DataBean.GameBean.ItemsBean itemsBean, final int position) {
        final Message message = mHandler.obtainMessage();
        message.what = 1;
        String token = userSP.getToken("token");
        Map<String, String> map = new HashMap<>();
        map.put("fid", itemsBean.getFid());
        instance.apiDeleteCollection(token, NetUtil.getParamsPro(map).get("signature"), itemsBean.getFid(), new Subscriber<ResponseBody>() {
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
                    JSONObject jsonObject = instance.getJsonObject(deleteCollectGame);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
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
                            instance.skipLoginActivity(activity, LoginActivity.class, "CatchFishAdapter");
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

    private void collectGame(final CheckBox cb, final HomeGameBean.DataBean.GameBean.ItemsBean itemsBean, final int position) {
        final Message message = mHandler.obtainMessage();
        message.what = 1;
        String token = userSP.getToken("token");
        Map<String, String> map = new HashMap<>();
        map.put("fid", itemsBean.getFid());
        instance.apiAddCollection(token, NetUtil.getParamsPro(map).get("signature"), itemsBean.getFid(), new Subscriber<ResponseBody>() {
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
                    JSONObject jsonObject = instance.getJsonObject(successCollectGame);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        if (!cbList.contains(position)) {
                            cbList.add(position);
                        }
                        ToastUtil.showShort(mContext, "收藏游戏成功！");
                    } else {
                        cb.setChecked(false);
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(activity, LoginActivity.class, "CatchFishAdapter");
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

    private void setPara(Map<String, String> paraMap, HomeGameBean.DataBean.GameBean.ItemsBean itemsBean) {
        paraMap.put("game", "捕鱼达人");
        paraMap.put("href", URLEncoder.encode(itemsBean.getHref()));
        paraMap.put("img", gameImage);
        paraMap.put("name", itemsBean.getName());
        paraMap.put("type", itemsBean.getType());
    }
}
