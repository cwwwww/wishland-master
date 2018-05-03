package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePhoneActivity extends BaseStyleActivity {

    Unbinder unbinder;
    //    @BindView(R.id.rl_send_vertify)
//    RelativeLayout sendVertify;
    @BindView(R.id.empty_layout)
    EmptyLayout empty_layout;
    @BindView(R.id.tv_moblie_num)
    TextView moblieNumber;


    private Model instance;
    private UserSP userSP;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("验证方式");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_phone, R.layout.base_toolbar_back);
        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.rl_send_vertify, R.id.rl_send_person})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_send_vertify:
                startActivity(new Intent(this, VerifyPhoneNumberActivity.class));
                break;
            case R.id.rl_send_person:
                empty_layout.setLoadingMessage("正在连接客服...");
                empty_layout.showLoading();
                Intent intent = new Intent(this, DetailsHtmlPageActivity.class);
                intent.setAction(BaseApi.NEWHTML);
                intent.putExtra(BaseApi.HTML5DATA, BaseApi.CustomHtml5);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        empty_layout.hide();
        moblieNumber.setText(userSP.getMolibe());

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
