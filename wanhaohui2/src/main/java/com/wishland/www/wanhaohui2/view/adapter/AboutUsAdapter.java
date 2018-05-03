package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.AboutUsBean;
import com.wishland.www.wanhaohui2.bean.QuestionBean;

/**
 * Created by Tom on 2017/10/13.
 */

public class AboutUsAdapter extends SuperAdapter<AboutUsBean.AboutUsData.AboutContent> {


    public AboutUsAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_about_us, parent, false);
    }

    @Override
    protected void onBindItemView(View view, int viewType, final int position) {
        final AboutUsBean.AboutUsData.AboutContent item = getItem(position);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_about_us_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_about_us_content);

        if(!"".equals(item.getTitle())){
            tv_title.setText(item.getTitle());
        }
        tv_content.setText(item.getContent());

    }

}
