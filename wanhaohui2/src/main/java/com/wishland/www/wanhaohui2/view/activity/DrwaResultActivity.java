package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.AccountBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class DrwaResultActivity extends BaseStyleActivity {

    @BindView(R.id.tv_draw_num)
    TextView drawNumber;
    @BindView(R.id.tv_to_num)
    TextView bankName;
    @BindView(R.id.tv_bank_number)
    TextView bankNumber;
    @BindView(R.id.tv_draw_statu_num)
    TextView payStatus;
    @BindView(R.id.tv_pay_status)
    TextView tv_pay_status;
    @BindView(R.id.iv_draw_success)
    ImageView payImage;
    @BindView(R.id.rl_draw_back)
    RelativeLayout back;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.iv_pay_img)
    ImageView iv_pay_img;
    @BindView(R.id.iv_finish_img)
    ImageView iv_finish_img;
    @BindView(R.id.tv_pay_text)
    TextView tv_pay_text;
    @BindView(R.id.tv_finish)
    TextView tv_finish;


    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initDate() {
        setTitle("取款");
        ButterKnife.bind(this);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        bankInfoResquest();

        Intent intent = getIntent();
        String name = intent.getStringExtra("BankName");
        String number = intent.getStringExtra("BankNumber");
        String submitMoney = intent.getStringExtra("SubmitMoney");
        String status = intent.getStringExtra("PayStatus");

        drawNumber.setText(submitMoney);
//        bankName.setText(name);
//        bankNumber.setText("尾号" + "**" + number.substring(number.length() - 2));
        payStatus.setText(status);
        if (status.equals("失败")) {
            payImage.setImageDrawable(getResources().getDrawable(R.drawable.draw_failed));
            tv_pay_status.setText("提款失败");
        } else if (status.equals("成功")) {
            view1.setBackgroundResource(R.color.view_green);
            view2.setBackgroundResource(R.color.view_green);
            iv_pay_img.setImageDrawable(getResources().getDrawable(R.drawable.pay_2));
            iv_finish_img.setImageDrawable(getResources().getDrawable(R.drawable.finish_2));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DrwaResultActivity.this,DrawHistoryActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_drwa_result, R.layout.base_toolbar_back);
    }

    private void bankInfoResquest() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAccount(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("个人页面请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("个人页面请求失败：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        AccountBean accountBean = instance.getGson().fromJson(string, AccountBean.class);
                        AccountBean.DataBean.BalanceInfoBean balanceInfo = accountBean.getData().getBalanceInfo();
                        AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();
                        String name = financeInfo.getBank();
                        String number = financeInfo.getBankAccount();

                        bankName.setText(name);
                        bankNumber.setText("尾号" + "**" + number.substring(number.length() - 2));

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(DrwaResultActivity.this, LoginActivity.class,"DrwaResultActivity");
                        } else {
                            ToastUtil.showShort(DrwaResultActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
