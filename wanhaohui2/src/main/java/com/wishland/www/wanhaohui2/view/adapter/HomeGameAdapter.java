package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;

/**
 * Created by admin on 2017/10/12.
 */

public class HomeGameAdapter extends SuperAdapter<HomeGameBean.DataBean.GameBean> {
    private Context mContext;

    public HomeGameAdapter(Activity ctx, Context context) {
        super(ctx);
        this.mContext = context;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_home_game, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        HomeGameBean.DataBean.GameBean dataBean = getItem(position);
        view.setTag(dataBean.getTitle());
        TextView tv = (TextView) view.findViewById(R.id.tv_name);
        SimpleDraweeView sdv = (SimpleDraweeView) view.findViewById(R.id.sdv_game);

        if (position == getCount() - 1) {
            tv.setText(dataBean.getTitle());
            FrescoUtil.loadGifPicOnNet(sdv,Uri.parse("res://com.wishland.www.wanhaohui2/" + R.drawable.icon_home_game_more).toString());
            return;
        }
        FrescoUtil.loadGifPicOnNet(sdv, dataBean.getImg());
        tv.setText(dataBean.getTitle());
    }
}
