package com.wishland.www.wanhaohui2.view.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.DiscountBean;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;

/**
 * Created by admin on 2017/10/12.
 */

public class DiscountAdapter extends SuperAdapter<DiscountBean.DataBean> {

    private Context mContext;

    public DiscountAdapter(Activity ctx) {
        super(ctx);
        this.mContext = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_discount, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        DiscountBean.DataBean dataBean = getItem(position);

//        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_discount);
        ImageView simpleDraweeView = (ImageView) view.findViewById(R.id.sdv_discount);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_start = (TextView) view.findViewById(R.id.tv_start);
        TextView tv_end = (TextView) view.findViewById(R.id.tv_end);
        TextView tv_game = (TextView) view.findViewById(R.id.tv_game);

//        FrescoUtil.loadGifPicOnNet(simpleDraweeView, dataBean.getImgUrl());
        Glide.with(mContext)
                .load(dataBean.getImgUrl())
                .crossFade()
                .fitCenter()
                .animate(setAnimation())
                .placeholder(mContext.getResources().getDrawable(R.drawable.default_img3))
                .into(simpleDraweeView);
        tv_title.setText(dataBean.getTitle());
        tv_start.setText(dataBean.getEnd());
        tv_end.setText(dataBean.getStart());
        tv_game.setText(dataBean.getGame());

    }

    private ViewPropertyAnimation.Animator setAnimation() {
        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha(0f);

                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };
        return animationObject;
    }
}
