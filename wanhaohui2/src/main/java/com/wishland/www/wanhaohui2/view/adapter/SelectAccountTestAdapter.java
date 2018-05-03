package com.wishland.www.wanhaohui2.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;
import com.wishland.www.wanhaohui2.bean.SelectAccountBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by admin on 2017/10/15.
 */

public class SelectAccountTestAdapter extends RecyclerView.Adapter<SelectAccountTestAdapter.ViewHolder> {
    private List<LineMoneyDataBean.LineMoneyData.WalletBean> list=new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener = null;

    public SelectAccountTestAdapter(Context mContext,List<LineMoneyDataBean.LineMoneyData.WalletBean> list){
        this.mContext=mContext;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_account,parent,false);
       ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_account_name.setText(list.get(position).getName());
        if("0.00".equals(list.get(position).getAmout())){
            holder.tv_account_counts.setTextColor(mContext.getResources().getColor(R.color.text_color2));
        }else{
            holder.tv_account_counts.setTextColor(mContext.getResources().getColor(R.color.text_color1));
        }
        holder.tv_account_counts.setText(list.get(position).getAmout());
        holder.rl_select_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_select_account;
        public TextView tv_account_name;
        public TextView tv_account_counts;
        public ViewHolder(View view){
            super(view);
            rl_select_account = (RelativeLayout) view.findViewById(R.id.rl_select_account);
            tv_account_name = (TextView) view.findViewById(R.id.tv_account_name);
            tv_account_counts = (TextView) view.findViewById(R.id.tv_account_counts);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

}
