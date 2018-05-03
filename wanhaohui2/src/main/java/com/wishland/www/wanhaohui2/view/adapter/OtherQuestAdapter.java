package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.QuestDealBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class OtherQuestAdapter extends SuperAdapter<QuestDealBean.QuestDealData.OtherBean> {

    private OnItemClickListener mOnItemClickListener = null;
    private int type;
    private Activity ctx;

    public OtherQuestAdapter(Activity ctx, int type) {
        super(ctx);
        this.type = type;
        this.ctx = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_draw_history, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        QuestDealBean.QuestDealData.OtherBean queryBean = getItem(position);
        TextView drawDate = (TextView) view.findViewById(R.id.tv_draw_date);
        TextView drawTime = (TextView) view.findViewById(R.id.tv_draw_time);
        TextView bankName = (TextView) view.findViewById(R.id.tv_draw_bank);
        TextView bankNumber = (TextView) view.findViewById(R.id.tv_draw_bank_number);
        TextView drawNumber = (TextView) view.findViewById(R.id.tv_draw_number);
        TextView drawId = (TextView) view.findViewById(R.id.tv_draw_id);
        TextView drawStatus = (TextView) view.findViewById(R.id.tv_draw_status);
        LinearLayout ll_draw_list = (LinearLayout) view.findViewById(R.id.ll_draw_list);

        ll_draw_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });

        if (queryBean != null) {
            drawDate.setText(queryBean.getChinaTime().substring(5, 10));
            drawTime.setText(queryBean.getChinaTime().substring(11, 16));
            drawNumber.setText(queryBean.getSum() + "");
            drawId.setText(queryBean.getSerialNum() + "");
//            drawStatus.setText(queryBean.getNotes() + "");
//            drawStatus.setTextColor(ctx.getResources().getColor(R.color.text_color2));
            if ("审核中".equals(queryBean.getState())) {
                drawStatus.setText(queryBean.getState());
                drawStatus.setTextColor(ctx.getResources().getColor(R.color.text_color3));
            } else if ("成功".equals(queryBean.getState()) || "汇款成功".equals(queryBean.getState())) {
                drawStatus.setText(queryBean.getState());
                drawStatus.setTextColor(ctx.getResources().getColor(R.color.view_green));
            } else if ("系统审核中".equals(queryBean.getState())) {
                drawStatus.setText(queryBean.getState());
                drawStatus.setTextColor(ctx.getResources().getColor(R.color.text_color3));
            } else {
                drawStatus.setText(queryBean.getState());
                drawStatus.setTextColor(ctx.getResources().getColor(R.color.text_color2));
            }


        }


    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
