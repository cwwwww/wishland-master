package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.BalanceDetailBean;
import com.wishland.www.wanhaohui2.bean.BankListBean;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;

/**
 * Created by admin on 2017/10/13.
 */

public class BalanceDetailAdapter extends SuperAdapter<LineMoneyDataBean.LineMoneyData.WalletBean> {

    public BalanceDetailAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_balance_detail, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        LineMoneyDataBean.LineMoneyData.WalletBean item = getItem(position);
        TextView name = (TextView) view.findViewById(R.id.tv_balance_detail_name);
        TextView counts = (TextView) view.findViewById(R.id.tv_balance_detail_counts);
        name.setText(item.getName());
        counts.setText(item.getAmout());
    }
}
