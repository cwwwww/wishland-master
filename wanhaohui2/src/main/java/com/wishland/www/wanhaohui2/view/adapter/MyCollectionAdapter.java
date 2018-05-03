package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.MemberRatingBean;
import com.wishland.www.wanhaohui2.bean.MindCollectionBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/12/3.
 */

public class MyCollectionAdapter extends SuperAdapter<MindCollectionBean.DataBean> {
    private Context mContext;
    private UserSP userSP;
    private Model instance;
    private boolean isNeedRefresh;
    private Activity activity;
    private Handler mHandler;
    EmptyLayout emptyLayout;
    List<Integer> mViewType;

    public MyCollectionAdapter(Activity ctx, UserSP userSP, Model instance, Handler mHandler, EmptyLayout emptyLayout, List<Integer> viewType) {
        super(ctx);
        this.mContext = ctx;
        this.userSP = userSP;
        this.activity = ctx;
        this.instance = instance;
        this.mHandler = mHandler;
        this.emptyLayout = emptyLayout;
        this.mViewType = viewType;
    }

    public boolean isNeedRefresh() {
        return isNeedRefresh;
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return mInflater.inflate(R.layout.item_my_game_collection, parent, false);
        } else if (viewType == 1) {
            return mInflater.inflate(R.layout.item_my_game_collection_delete, parent, false);
        }
        return mInflater.inflate(R.layout.item_my_game_collection, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        LinearLayout llCollectionGame = (LinearLayout) view.findViewById(R.id.ll_collection_game);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_game);
        TextView tv = (TextView) view.findViewById(R.id.tv_name);
        LinearLayout llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        MindCollectionBean.DataBean dataBean = getItem(position);
        String fid = dataBean.getFid();
        final String para = dataBean.getPara();
        if (para != null && !"".equals(para)) {
            llCollectionGame.setVisibility(View.VISIBLE);
            final Map<String, String> paraMap = new Gson().fromJson(para, Map.class);
            tv.setText(paraMap.get("name"));
            String img;
            final String url = paraMap.get("url_m");
            if (paraMap.size() == 0) {
                return;
            }
            if (dataBean.getCategory().equals("cp")) {
                img = paraMap.get("img");
            } else {
                img = paraMap.get("img_plat");
            }
            if (img == null) {
                FrescoUtil.loadGifPicOnNet(simpleDraweeView, "res://com.wishland.www.wanhaohui2/" + R.drawable.default_img);
            } else {
                FrescoUtil.loadGifPicOnNet(simpleDraweeView, img);
            }
            llCollectionGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                       Log.e("cww", url.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)));
                    WebUtil.toWebActivity(mContext, url.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), paraMap.get("name"));
                }
            });
        } else {
            llCollectionGame.setVisibility(View.GONE);
        }

        setCBClickListener(llDelete, fid);

    }

    private void setCBClickListener(final LinearLayout llDelete, final String id) {
        if (llDelete != null) {
            llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userSP.getInt(UserSP.LOGIN_SUCCESS) == 1) {
                        emptyLayout.showLoading();
                        emptyLayout.setLoadingMessage("正在取消收藏...");
                        final Message message = mHandler.obtainMessage();
                        message.what = 0;
                        String token = userSP.getToken("token");
                        Map<String, String> map = new HashMap<>();
                        map.put("fid", id);
                        instance.apiDeleteCollection(token, NetUtil.getParamsPro(map).get("signature"), id, new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                emptyLayout.hide();
                                mHandler.sendMessage(message);
                                ToastUtil.showShort(mContext, "取消收藏游戏失败！");
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                emptyLayout.hide();
                                mHandler.sendMessage(message);
                                try {
                                    String deleteCollectGame = responseBody.string();
                                    JSONObject jsonObject = instance.getJsonObject(deleteCollectGame);
                                    int status = jsonObject.optInt("status");
                                    instance.setToken_SP(jsonObject.optString("token"));
                                    if (status == 200) {
                                        isNeedRefresh = true;
                                        ToastUtil.showShort(mContext, "取消收藏游戏成功！");
                                    } else {
                                        String errorMsg = jsonObject.optString("errorMsg");
                                        if (errorMsg.equals("用户未登录")) {
                                            instance.skipLoginActivity(activity, LoginActivity.class, "MyCollectionAdapter");
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
                    } else {
                        instance.skipLoginActivity(activity, LoginActivity.class, "MyCollectionAdapter");
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mViewType.size() == 0) {
            return 0;
        } else {
            return mViewType.get(position);
        }
    }

    public void setViewType(List<Integer> d) {
        mViewType.clear();
        mViewType.addAll(d);
        notifyDataSetChanged();
    }

}

