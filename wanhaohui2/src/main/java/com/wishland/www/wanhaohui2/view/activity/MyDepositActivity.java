package com.wishland.www.wanhaohui2.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;

public class MyDepositActivity extends BaseStyleActivity {


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("存款");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_deposit, R.layout.base_toolbar_back);
    }
}
