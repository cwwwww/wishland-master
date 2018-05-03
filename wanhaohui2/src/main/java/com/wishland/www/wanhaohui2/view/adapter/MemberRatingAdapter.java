package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.BalanceDetailBean;
import com.wishland.www.wanhaohui2.bean.MemberRatingBean;

/**
 * Created by admin on 2017/10/13.
 */

public class MemberRatingAdapter extends SuperAdapter<MemberRatingBean.DataBean> {
    public Context mContext;

    public MemberRatingAdapter(Activity ctx, Context context) {
        super(ctx);
        this.mContext = context;
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_member_rating, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        MemberRatingBean.DataBean item = getItem(position);
        ImageView iv_vip_icon = (ImageView) view.findViewById(R.id.iv_vip_icon);
        TextView tv_vip_money = (TextView) view.findViewById(R.id.tv_vip_money);
        TextView tv_touzhu_money = (TextView) view.findViewById(R.id.tv_touzhu_money);
        TextView tv_quanbu_money = (TextView) view.findViewById(R.id.tv_quanbu_money);
        TextView tv_tj = (TextView) view.findViewById(R.id.tv_tj);

        Glide.with(mContext).load(item.getLevelimg()).into(iv_vip_icon);
        tv_vip_money.setText(item.getAmount());
        tv_tj.setText(item.getUpgradetype());
        tv_touzhu_money.setText("周投注额 >= " + item.getZtzje() + "元");
        tv_quanbu_money.setText("周存款额 >=" + item.getCkje() + "元");


    }
}
