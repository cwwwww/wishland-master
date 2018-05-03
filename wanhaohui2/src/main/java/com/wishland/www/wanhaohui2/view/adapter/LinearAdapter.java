package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.AccountBankBean;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;
import com.wishland.www.wanhaohui2.bean.MessageBean;

import org.w3c.dom.Text;

import static com.wishland.www.wanhaohui2.R.id.rl_contactNameTV;

/**
 * Created by Tom on 2017/10/13.
 */

public class LinearAdapter extends SuperAdapter<LineMoneyDataBean.LineMoneyData.WalletBean> {

    private OnItemClickListener mOnItemClickListener = null;

    public LinearAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_linear_info, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        LineMoneyDataBean.LineMoneyData.WalletBean item = getItem(position);
        TextView name = ((TextView) view.findViewById(R.id.funds_popup_item_text));
//        TextView count = ((TextView) view.findViewById(R.id.funds_popup_item_count));
        RelativeLayout ll_linear_list = ((RelativeLayout) view.findViewById(R.id.ll_linear_list));

        name.setText(item.getName());
//        count.setText(item.getAmout());

        ll_linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
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

}
