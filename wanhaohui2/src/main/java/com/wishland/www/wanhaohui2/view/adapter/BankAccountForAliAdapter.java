package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.OfflinePayBean;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.StringUtil;

import java.util.List;

/**
 * Created by admin on 2017/10/29.
 */

public class BankAccountForAliAdapter extends SuperAdapter<OfflinePayBean.DataBean.ParaBean.AccountBean> {
    private List<Integer> viewType;
    private String imgUrl;

    public BankAccountForAliAdapter(Activity ctx, List<Integer> viewType) {
        super(ctx);
        this.viewType = viewType;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return mInflater.inflate(R.layout.item_bank_account0, parent, false);
        } else if (viewType == 1) {
            return mInflater.inflate(R.layout.item_bank_account1, parent, false);
        }
        return mInflater.inflate(R.layout.item_bank_account0, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_bank_type);
        TextView tvName = (TextView) view.findViewById(R.id.tv_bank_name);
//        TextView tvAccountNumber = (TextView) view.findViewById(R.id.tv_bank_number);
//        TextView tvUserName = (TextView) view.findViewById(R.id.tv_bank_user);
        OfflinePayBean.DataBean.ParaBean.AccountBean accountBean = getItem(position);
        tvName.setText(accountBean.getAlipayName());
//        tvAccountNumber.setText(StringUtil.spaceAt4(accountBean.getAlipayID()));
//        tvUserName.setText(accountBean.getAlipayName());
        if (imgUrl != null) {
            FrescoUtil.loadPicOnNet(simpleDraweeView, imgUrl);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewType.get(position);
    }

    public void setViewType(List<Integer> d) {
        viewType.clear();
        viewType.addAll(d);
        notifyDataSetChanged();
    }

    public void setImageUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
