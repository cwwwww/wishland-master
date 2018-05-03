package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.MessageBean;
import com.wishland.www.wanhaohui2.bean.QuestionBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class QuestionAdapter extends SuperAdapter<QuestionBean.QuestionData.ContentBean> {

    private OnItemClickListener mOnItemClickListener = null;
    private ShowItemCb showItemCb;

    public QuestionAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_question, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        final QuestionBean.QuestionData.ContentBean item = getItem(position);
        RelativeLayout rl_question_title = ((RelativeLayout) view.findViewById(R.id.rl_question_title));
        final LinearLayout ll_question_content = ((LinearLayout) view.findViewById(R.id.ll_question_content));
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);

        tv_title.setText(item.getTitle());
        tv_content.setText(item.getContent());
        rl_question_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(v, position);
                    if(!item.isSee()){
                        ll_question_content.setVisibility(View.VISIBLE);
                        item.setSee(true);
                    }else{
                        ll_question_content.setVisibility(View.GONE);
                        item.setSee(false);
                    }

//                }
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
