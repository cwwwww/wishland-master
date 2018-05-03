package com.wishland.www.wanhaohui2.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.EGameBean;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.bean.MindCollectionBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/13.
 */

public class EGameAdapter extends SuperAdapter<HomeGameBean.DataBean.GameBean.ItemsBean> {


    public EGameAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1) {
            view = mInflater.inflate(R.layout.item_normal_egame, parent, false);
        } else if (viewType == 2) {
            view = mInflater.inflate(R.layout.item_hot_egame, parent, false);
        } else if (viewType == 3) {
            view = mInflater.inflate(R.layout.item_latest_egame, parent, false);
        }
        return view;
    }

    @Override
    public int getViewType(int position) {
        return 1;
    }

    @Override
    protected void onBindItemView(View view, int viewType, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv_game);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        HomeGameBean.DataBean.GameBean.ItemsBean itemsBean = getItem(position);

        FrescoUtil.loadGifPicOnNet(simpleDraweeView, itemsBean.getImg());
        tvName.setText(itemsBean.getName());

    }
}
