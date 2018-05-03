package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.BankListBean;
import com.wishland.www.wanhaohui2.bean.MyDiscountBean;

/**
 * Created by admin on 2017/10/13.
 */

public class BankListAdapter extends SuperAdapter<BankListBean> {

    public BankListAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_bank_list, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
//        getItem(position);
    }
}
