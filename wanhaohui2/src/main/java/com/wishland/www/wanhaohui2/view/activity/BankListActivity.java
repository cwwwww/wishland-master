package com.wishland.www.wanhaohui2.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.BankListBean;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.view.adapter.BankListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BankListActivity extends BaseStyleActivity {
    Unbinder unbinder;
    @BindView(R.id.rv_bank_list)
    RecyclerView rv_bank_list;

    private BankListAdapter adapter;
    private List<BankListBean> bankListBeen = new ArrayList<>();

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("更换银行卡");
        for (int i = 0; i < 40; i++) {
            BankListBean bankListBean = new BankListBean();
            bankListBeen.add(bankListBean);
        }
        adapter.setData(bankListBeen);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bank_list, R.layout.base_toolbar_back);
        unbinder = ButterKnife.bind(this);

        rv_bank_list.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new BankListAdapter(this);
        rv_bank_list.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
