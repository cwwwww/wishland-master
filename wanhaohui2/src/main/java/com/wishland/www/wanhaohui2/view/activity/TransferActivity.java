package com.wishland.www.wanhaohui2.view.activity;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;

/**
 * Created by admin on 2017/10/11.
 */

public class TransferActivity extends BaseStyleActivity {
    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("额度转换");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_transfer, R.layout.base_toolbar_back);
    }
}
