package com.wishland.www.wanhaohui2.view.customlayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;
import com.wishland.www.wanhaohui2.bean.SelectAccountBean;
import com.wishland.www.wanhaohui2.view.adapter.BalanceDetailAdapter;
import com.wishland.www.wanhaohui2.view.adapter.SelectAccountTestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/15.
 */

public class SelectAccountDialog extends Dialog implements View.OnClickListener {
    private TextView titleTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private OnItemClickListener onItemClickListener;
    private String positiveName;
    private String negativeName;
    private String title;
    private RecyclerView recycleView;
    private List<LineMoneyDataBean.LineMoneyData.WalletBean> list=new ArrayList<>();
    private SelectAccountTestAdapter adapter;
    private ImageView iv_close_dialog;

    public SelectAccountDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public SelectAccountDialog(Context context, List<LineMoneyDataBean.LineMoneyData.WalletBean> list, OnCloseListener listener, OnItemClickListener onItemClickListener) {
        super(context);
        this.mContext = context;
        this.list=list;
        this.listener = listener;
        this.onItemClickListener=onItemClickListener;
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.DialogBottom);
    }

    public SelectAccountDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public SelectAccountDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected SelectAccountDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public SelectAccountDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public SelectAccountDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public SelectAccountDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_accout);
        setCanceledOnTouchOutside(true);
        initView();
    }


    private void initView(){
        titleTxt = (TextView)findViewById(R.id.dialog_title);
        iv_close_dialog = (ImageView)findViewById(R.id.iv_close_dialog);
        recycleView = (RecyclerView) findViewById(R.id.rl_select_account);
        recycleView.setLayoutManager(new GridLayoutManager(mContext,3));
        recycleView.addItemDecoration(new MyDividerItemDecoration(mContext,GridLayoutManager.VERTICAL));
        recycleView.addItemDecoration(new MyDividerItemDecoration(mContext,GridLayoutManager.HORIZONTAL));
        adapter = new SelectAccountTestAdapter(mContext,list);
        recycleView.setAdapter(adapter);
        iv_close_dialog.setOnClickListener(this);
        adapter.setOnItemClickListener(new SelectAccountTestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onItemClickListener.onItemClickListener(SelectAccountDialog.this,list.get(position));
            }
        });

        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_dialog:
                if(listener != null){
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

    public interface OnItemClickListener{
        void onItemClickListener(Dialog dialog,LineMoneyDataBean.LineMoneyData.WalletBean bean);
    }
}
