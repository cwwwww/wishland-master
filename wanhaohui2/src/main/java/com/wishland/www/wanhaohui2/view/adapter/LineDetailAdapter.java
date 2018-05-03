package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.LineDetailBean;
import com.wishland.www.wanhaohui2.bean.MyDiscountBean;

/**
 * Created by admin on 2017/10/13.
 */

public class LineDetailAdapter extends SuperAdapter<LineDetailBean.DataBean.ListBean> {

    private Context context;

    public LineDetailAdapter(Activity ctx) {
        super(ctx);
        this.context = ctx;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_line_list, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        LineDetailBean.DataBean.ListBean queryBean = getItem(position);
        TextView drawDate = (TextView) view.findViewById(R.id.tv_draw_date);
        TextView drawTime = (TextView) view.findViewById(R.id.tv_draw_time);
        TextView drawNumber = (TextView) view.findViewById(R.id.tv_draw_number);
        TextView drawId = (TextView) view.findViewById(R.id.tv_draw_id);
        TextView drawStatus = (TextView) view.findViewById(R.id.tv_draw_status);

        drawDate.setText(queryBean.getChinaTime().substring(5, 10));
        drawTime.setText(queryBean.getChinaTime().substring(11, 16));
        drawNumber.setText(queryBean.getSum() + "");
        drawId.setText(queryBean.getSerialNum() + "");
        drawStatus.setText(queryBean.getFrom()+" > "+queryBean.getTo());
//        if ("审核中".equals(queryBean.getResult())) {
//            drawStatus.setText("审核中");
//            drawStatus.setTextColor(context.getResources().getColor(R.color.text_color3));
//        } else if ("成功".equals(queryBean.getResult()) || "汇款成功".equals(queryBean.getResult())) {
//            drawStatus.setTextColor(context.getResources().getColor(R.color.view_green));
//            drawStatus.setText("成功");
//        } else if ("系统审核中".equals(queryBean.getResult())) {
//            drawStatus.setText("审核中");
//            drawStatus.setTextColor(context.getResources().getColor(R.color.text_color3));
//        } else {
//            drawStatus.setText("取消");
//            drawStatus.setTextColor(context.getResources().getColor(R.color.text_color2));
//        }
    }
}
