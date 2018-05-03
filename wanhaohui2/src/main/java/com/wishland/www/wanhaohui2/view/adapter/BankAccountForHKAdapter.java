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

public class BankAccountForHKAdapter extends SuperAdapter<OfflinePayBean.DataBean.ParaBean.BankBean> {

    private List<Integer> viewType;
    private String imgUrl;

    public BankAccountForHKAdapter(Activity ctx, List<Integer> viewType) {
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
        TextView tvBankName = (TextView) view.findViewById(R.id.tv_bank_name);
//        TextView tvBankNumber = (TextView) view.findViewById(R.id.tv_bank_number);
//        TextView tvBankUser = (TextView) view.findViewById(R.id.tv_bank_user);
//        TextView tvBankPlace = (TextView) view.findViewById(R.id.tv_bank_place);
        OfflinePayBean.DataBean.ParaBean.BankBean bankBean = getItem(position);
        tvBankName.setText(bankBean.getBank());
//        tvBankNumber.setText(StringUtil.spaceAt4(bankBean.getCardid()));
//        tvBankUser.setText(bankBean.getName());
//        tvBankPlace.setText(bankBean.getAddress());
//        if (imgUrl != null) {
//            FrescoUtil.loadPicOnNet(simpleDraweeView, imgUrl);
//        }
        FrescoUtil.loadGifPicOnNet(simpleDraweeView, bankBean.getImg());

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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
