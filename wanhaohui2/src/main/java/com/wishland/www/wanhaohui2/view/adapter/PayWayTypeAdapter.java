package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.MyApplication;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.PayWayTypeBean;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;

import java.util.List;

/**
 * Created by admin on 2017/10/26.
 */

public class PayWayTypeAdapter extends SuperAdapter<PayWayTypeBean.DataBean.PayListBean> {

    List<Integer> mViewType;

    public PayWayTypeAdapter(Activity ctx, List<Integer> mViewType) {
        super(ctx);
        this.mViewType = mViewType;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return mInflater.inflate(R.layout.item_pay_type0, parent, false);
        } else if (viewType == 1) {
            return mInflater.inflate(R.layout.item_pay_type1, parent, false);
        }
        return mInflater.inflate(R.layout.item_pay_type0, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        SimpleDraweeView ivPayType = (SimpleDraweeView) view.findViewById(R.id.iv_pay_type);
        TextView tvPayType = (TextView) view.findViewById(R.id.tv_pay_name);
        PayWayTypeBean.DataBean.PayListBean payListBean = getItem(position);
        FrescoUtil.loadGifPicOnNet(ivPayType, payListBean.getTitle_img());
        if (viewType == 1) {
            tvPayType.setTextColor(MyApplication.baseContext.getResources().getColor(R.color.text_color3));
        } else {
            tvPayType.setTextColor(MyApplication.baseContext.getResources().getColor(R.color.text_color1));
        }
        tvPayType.setText(payListBean.getTitle());
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
