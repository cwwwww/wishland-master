package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.BetTypeBean;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class PlaceTitileAdapter extends SuperAdapter<BetTypeBean.BetTypeData> {

    private OnItemClickListener mOnItemClickListener = null;
    private String title;
    private Activity ctx;

    public PlaceTitileAdapter(Activity ctx, String title) {
        super(ctx);
        this.ctx = ctx;
        this.title = title;
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_place_order_two, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        BetTypeBean.BetTypeData item = getItem(position);
        TextView name = ((TextView) view.findViewById(R.id.tv_place_title));
        if (title.equals(item.getName())) {
            name.setBackground(ctx.getResources().getDrawable(R.drawable.shape_my_discount_item));
        }else{
            name.setBackground(ctx.getResources().getDrawable(R.drawable.shape_place_order_item));
        }
        name.setText(item.getName());
        name.setOnClickListener(new View.OnClickListener() {
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
