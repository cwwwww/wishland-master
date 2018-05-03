package com.wishland.www.wanhaohui2.view.adapter;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.MessageBean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.MessageBean;
import com.wishland.www.wanhaohui2.view.activity.MessageDetailActivity;
import com.wishland.www.wanhaohui2.view.activity.MyMessageActivity;
import com.wishland.www.wanhaohui2.view.adapter.MyMessageAdapter;

import java.util.List;

/**
 * Created by admin on 2017/11/10.
 */

public class MyMessageRefreshAdapter extends CommonBaseAdapter<MessageBean.DataBean.DataListBean> {

    private DelateItemListener delateItemListener = null;
    private CheckInterface checkInterface = null;
    private boolean isShow = false;//是否显示编辑/完成
    private Context mContext;

    public MyMessageRefreshAdapter(Context context, List<MessageBean.DataBean.DataListBean> datas, boolean isLoadMore) {
        super(context, datas, isLoadMore);
        this.mContext = context;
    }

    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(final ViewHolder viewHolder, final MessageBean.DataBean.DataListBean item, final int position) {
        viewHolder.setText(R.id.tv_message_name, item.getTitle());
        viewHolder.setText(R.id.tv_message_date, item.getTime());
        viewHolder.setText(R.id.tv_message_info, item.getDetailedInfo());
        CheckBox view = (CheckBox) viewHolder.getView(R.id.cb_deleta);
        view.setChecked(item.isChoosed());

        if (item.getIsNew() == 1) {
            viewHolder.getView(R.id.view_is_look).setVisibility(View.VISIBLE);
        } else {
            viewHolder.getView(R.id.view_is_look).setVisibility(View.GONE);
        }

        viewHolder.setOnClickListener(R.id.cb_deleta, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setChoosed(((CheckBox) v).isChecked());
                if (checkInterface != null) {
                    checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                }

            }
        });

        viewHolder.setOnClickListener(R.id.rl_contactNameTV, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.getView(R.id.view_is_look).setVisibility(View.GONE);
                Intent intent = new Intent(mContext, MessageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", item);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        //判断是否在编辑状态下
        if (isShow) {
            viewHolder.getView(R.id.cb_deleta).setVisibility(View.VISIBLE);
        } else {
            viewHolder.getView(R.id.cb_deleta).setVisibility(View.GONE);
        }

    }


    @Override
    protected int getItemLayoutId() {
        return R.layout.item_my_message;
    }

    public static interface DelateItemListener {
        void delateItemClick(View view, int position);
    }

    public void delateItemListener(DelateItemListener listener) {
        this.delateItemListener = listener;
    }


    public static interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }
}
