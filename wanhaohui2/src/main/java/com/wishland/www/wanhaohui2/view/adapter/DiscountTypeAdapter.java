package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.DiscountTypeBean;

/**
 * Created by admin on 2017/10/15.
 */

public class DiscountTypeAdapter extends SuperAdapter<DiscountTypeBean> {
    public DiscountTypeAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_discount_type, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_type.setText(getItem(position).getType());
    }
}
