package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.MessageBean;
import com.wishland.www.wanhaohui2.bean.MyMessageBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class MyMessageAdapter extends SuperAdapter<MessageBean.DataBean.DataListBean> {

    private OnItemClickListener mOnItemClickListener = null;
    private DelateItemListener delateItemListener = null;
    private CheckBox checkBox;
    private ShowItemCb showItemCb;

    public MyMessageAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_my_message, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        final MessageBean.DataBean.DataListBean item = getItem(position);
        RelativeLayout rl_contactNameTV = ((RelativeLayout) view.findViewById(R.id.rl_contactNameTV));
        final RelativeLayout rl_delete_message = ((RelativeLayout) view.findViewById(R.id.rl_delete_message));
        final RelativeLayout rl_message_info = ((RelativeLayout) view.findViewById(R.id.rl_message_info));
        final View view_is_look = view.findViewById(R.id.view_is_look);
        TextView tv_message_name = (TextView) view.findViewById(R.id.tv_message_name);
        TextView tv_message_date = (TextView) view.findViewById(R.id.tv_message_date);
        final TextView tv_message_info = (TextView) view.findViewById(R.id.tv_message_info);
        checkBox = (CheckBox) view.findViewById(R.id.cb_deleta);
        if (item.getIsNew() == 1) {
            view_is_look.setVisibility(View.VISIBLE);
        } else {
            view_is_look.setVisibility(View.GONE);
        }
        tv_message_name.setText(item.getTitle());
        tv_message_date.setText(item.getTime());
        tv_message_info.setText(item.getDetailedInfo());
        rl_contactNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
//                    if(!item.isSee()){
//                        rl_message_info.setVisibility(View.VISIBLE);
//                        view_is_look.setVisibility(View.GONE);
//                        item.setSee(true);
//                    }else{
//                        rl_message_info.setVisibility(View.GONE);
//                        item.setSee(false);
//                    }

                }
            }
        });
        rl_delete_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delateItemListener != null) {
                    delateItemListener.delateItemClick(v, position);
                }
            }
        });
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface DelateItemListener {
        void delateItemClick(View view, int position);
    }

    public void delateItemListener(DelateItemListener listener) {
        this.delateItemListener = listener;
    }

    public static interface ShowItemCb{
        void showItemCb(View view,int position);
    }

    public void showItemCbListener(ShowItemCb showItemCb){
        this.showItemCb=showItemCb;
    }
}
