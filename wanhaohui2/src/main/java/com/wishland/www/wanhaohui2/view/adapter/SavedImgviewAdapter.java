package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.QuestionBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class SavedImgviewAdapter extends SuperAdapter<String> {

    private OnItemClickListener mOnItemClickListener = null;
    private ShowItemCb showItemCb;

    public SavedImgviewAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_save_img, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        final String item = getItem(position);
        TextView tv_save_img = (TextView) view.findViewById(R.id.tv_save_img);

        tv_save_img.setText(item);
        tv_save_img.setOnClickListener(new View.OnClickListener() {
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


    public static interface ShowItemCb{
        void showItemCb(View view, int position);
    }

    public void showItemCbListener(ShowItemCb showItemCb){
        this.showItemCb=showItemCb;
    }
}
