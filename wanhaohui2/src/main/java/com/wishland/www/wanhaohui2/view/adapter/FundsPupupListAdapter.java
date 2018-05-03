package com.wishland.www.wanhaohui2.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.AccountBankBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/3.
 */

public class FundsPupupListAdapter extends BastListAdapter {

    private List<AccountBankBean.DataBean> list;

    public FundsPupupListAdapter(List<AccountBankBean.DataBean> list) {
        super(list);
        this.list =list;
        Log.i("linearResponse",list.toString());
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_popupwindow_item, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        }else{
            viewholder = (ViewHolder) convertView.getTag();
        }
        if(!"".equals(list.get(position).getBankname())){
            viewholder.fundsPopupItemText.setText(list.get(position).getBankname());
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.funds_popup_item_text)
        TextView fundsPopupItemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
