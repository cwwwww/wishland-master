package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HotGameBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.HotGameMoreActivity;
import com.wishland.www.wanhaohui2.view.activity.LiveVideoActivity;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.fragment.HomeFragment;

import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by admin on 2017/12/3.
 */

public class HotGameAdapter extends SuperAdapter<HotGameBean.DataBean> {
    private Context mContext;
    private UserSP userSP;
    private HomeFragment fragment;
    private Model instance;
    private Activity mActivity;

    public HotGameAdapter(Activity ctx, UserSP userSP, HomeFragment fragment, Model instance) {
        super(ctx);
        this.mContext = ctx;
        this.userSP = userSP;
        this.fragment = fragment;
        this.instance = instance;
        this.mActivity = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_my_game_collection, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
//        LinearLayout llCollectionGame = (LinearLayout) view.findViewById(R.id.ll_collection_game);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_game);
        TextView tv = (TextView) view.findViewById(R.id.tv_name);
        if (position == 7) {
            tv.setText("更多");
            FrescoUtil.loadGifPicOnNet(simpleDraweeView, "res://com.wishland.www.wanhaohui2/" + R.drawable.icon_home_game_more);
            return;
        }
        final HotGameBean.DataBean dataBean = getItem(position);
        final String para = dataBean.getPara();
        if (para != null && !"".equals(para)) {
            final Map<String, String> paraMap = new Gson().fromJson(para, Map.class);
            tv.setText(paraMap.get("name"));
            //判断游戏类型
            String img;
            if ("cp".equals(dataBean.getCategory())) {
                img = paraMap.get("img");
            } else {
                img = paraMap.get("img_plat");
            }
//            final String url = paraMap.get("url_m");
            if (img == null) {
                FrescoUtil.loadGifPicOnNet(simpleDraweeView, "res://com.wishland.www.wanhaohui2/" + R.drawable.default_img);
            } else {
                FrescoUtil.loadGifPicOnNet(simpleDraweeView, img.replace("[host]", "whh4488.com"));
            }
//            llCollectionGame.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (instance.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
////                        if (BaseApi.GAME_MODEL_OPEN && !dataBean.isTrymode()) {
//                        if (BaseApi.GAME_MODEL_OPEN) {
////                            ToastUtil.showShort(mContext, "该游戏不能试玩！");
////                            return;
//                        } else {
//                            instance.skipLoginActivity(mActivity, LoginActivity.class, "HotGameAdapter");
//                            return;
//                        }
//                    }
//                    WebUtil.toWebActivity(mContext, url.replace("[host]", "whh4488.com").replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), paraMap.get("name"));
//                }
//            });
        }
    }
}
