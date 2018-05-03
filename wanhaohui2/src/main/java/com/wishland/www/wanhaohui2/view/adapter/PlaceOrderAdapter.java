package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.PlaceOrderListBean;
import com.wishland.www.wanhaohui2.bean.QuestDealBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class PlaceOrderAdapter extends SuperAdapter<PlaceOrderListBean.DataBean.PlaceOrderData> {

    private OnItemClickListener mOnItemClickListener = null;
    private SetNameListener setNameListener = null;
    private String type;
    private Activity ctx;
    private TextView orderType;

    public PlaceOrderAdapter(Activity ctx, String type) {
        super(ctx);
        this.type = type;
        this.ctx = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_place_order, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        PlaceOrderListBean.DataBean.PlaceOrderData queryBean = getItem(position);
        TextView drawDate = (TextView) view.findViewById(R.id.tv_draw_date);
        TextView drawTime = (TextView) view.findViewById(R.id.tv_draw_time);
        TextView xzMoney = (TextView) view.findViewById(R.id.tv_xiazhu_money);
        TextView cjMoney = (TextView) view.findViewById(R.id.tv_caijin_money);
        orderType = (TextView) view.findViewById(R.id.tv_draw_status);

        if (queryBean != null) {
            drawDate.setText(queryBean.getBettime().substring(5, 10));
            drawTime.setText(queryBean.getBettime().substring(11, 16));
            xzMoney.setText(queryBean.getBet() + "");
            cjMoney.setText(queryBean.getWin() + "");
            orderType.setText(type);
            if (queryBean.getWin() > queryBean.getBet()) {
                cjMoney.setTextColor(ctx.getResources().getColor(R.color.text_color3));
            } else {
                cjMoney.setTextColor(ctx.getResources().getColor(R.color.view_green));
            }
        }


    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface SetNameListener {
        void setName(String name);
    }

    public void setName(String name) {
        orderType.setText(name);
    }

}
