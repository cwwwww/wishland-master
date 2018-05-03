package com.wishland.www.wanhaohui2.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.AccountBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.MD5Utils;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

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

public class DrawActivity extends BaseStyleActivity {

    @BindView(R.id.et_draw_money)
    EditText et_draw_money;
    @BindView(R.id.et_draw_password)
    EditText et_draw_password;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.rl_add_bank)
    RelativeLayout rl_add_bank;
    @BindView(R.id.ll_add_bank)
    LinearLayout ll_add_bank;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.tv_bank_number)
    TextView tv_bank_number;
    @BindView(R.id.iv_bank_icon)
    ImageView iv_bank_icon;
    @BindView(R.id.empty_layout)
    EmptyLayout empty_layout;

    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private TextView tv_btn;
    private String bankName = "";
    private String bankNumber = "";
    private String submitMoney = "";

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("取款");
        setTvTitle("取款记录");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        bankInfoResquest();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_draw, R.layout.base_toolbar_back_btn);
        ButterKnife.bind(this);

        tv_btn = (TextView) findViewById(R.id.tv_btn);
        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawActivity.this, DrawHistoryActivity.class));
            }
        });
    }

    @OnClick({R.id.rl_submit_draw, R.id.rl_add_bank, R.id.rl_bank_change})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_submit_draw:
                if (bankName == null || bankNumber == null) {
                    ToastUtil.showShort(DrawActivity.this, "请先绑定银行卡");
                    return;
                }

                if ("".equals(et_draw_money.getText().toString())) {
                    ToastUtil.showShort(DrawActivity.this, "取款金额不能为空");
                    return;
                }

                if (Double.valueOf(et_draw_money.getText().toString().trim()) < 100) {
                    ToastUtil.showShort(DrawActivity.this, "最低取款金额为100元");
                    return;
                }

                showDiacountDialog(et_draw_money.getText().toString());
                break;
            case R.id.rl_add_bank:
                startActivity(new Intent(this, BindBankActivity.class));
                break;

            case R.id.rl_bank_change:
                showDelateDialog();
                break;
        }
    }

    private void showDelateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DrawActivity.this);
        alertDialogBuilder.setMessage("银行卡信息需要客服人工修改，确定联系客服吗？");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                empty_layout.setLoadingMessage("连接客服中...");
                empty_layout.showLoading();
                WebUtil.toWebActivity(DrawActivity.this, BaseApi.CustomHtml5,"");
            }
        });
        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void submitDrawInfo(String psw) {
//        if ("".equals(et_draw_password.getText().toString())) {
//            ToastUtil.showShort(DrawActivity.this, "取款密码不能为空");
//            return;
//        }

        empty_layout.setLoadingMessage("正在提交...");
        empty_layout.showLoading();

        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        final String money = et_draw_money.getText().toString();
//        String password = et_draw_password.getText().toString();
        String md5Psw = MD5Utils.toMD5(psw);

        if (map.size() != 0) {
            map.clear();
        }
        map.put("passWord", md5Psw);
        map.put("Amount", money);

        instance.apiEssayMoney(md5Psw, money, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                empty_layout.hide();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    Log.i("linearResponse", string);
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("取款请求完成" + string);
                    empty_layout.hide();
                    if (status == 200) {
                        ToastUtil.showShort(DrawActivity.this, "取款申请已提交");
                        cleanView();
                        startActivity(new Intent(DrawActivity.this, DrawHistoryActivity.class));
//                        Intent intent = new Intent(DrawActivity.this,DrwaResultActivity.class);
//                        intent.putExtra("BankName", bankName);
//                        intent.putExtra("BankNumber", bankNumber);
//                        intent.putExtra("SubmitMoney", money);
//                        intent.putExtra("PayStatus", "审核中");
//                        startActivity(intent);
//                        finish();

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(DrawActivity.this, LoginActivity.class,"DrawActivity");
                        } else {
                            ToastUtil.showShort(DrawActivity.this, errorMsg);
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

    private void cleanView() {
        bankInfoResquest();
        et_draw_money.setText("");
        et_draw_password.setText("");
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
                        bankName = financeInfo.getBank();
                        bankNumber = financeInfo.getBankAccount();

                        textView6.setText(balanceInfo.getBalance() + "");
                        if (financeInfo == null || bankName == null) {
                            rl_add_bank.setVisibility(View.VISIBLE);
                            ll_add_bank.setVisibility(View.GONE);
                        } else {
                            ll_add_bank.setVisibility(View.VISIBLE);
                            rl_add_bank.setVisibility(View.GONE);
                            tv_bank_name.setText(financeInfo.getBank());
                            tv_bank_number.setText("尾号" + "**" + financeInfo.getBankAccount().substring(financeInfo.getBankAccount().length() - 2));
                        }

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(DrawActivity.this, LoginActivity.class,"DrawActivity");
                        } else {
                            ToastUtil.showShort(DrawActivity.this, errorMsg);
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

    private void showDiacountDialog(String money) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);

        RelativeLayout loginDialog = (RelativeLayout) getLayoutInflater().inflate(R.layout.drawing_dialog, null);
        Button submit = (Button) loginDialog.findViewById(R.id.bt_submit_discount);
        TextView tv_bank_number = (TextView) loginDialog.findViewById(R.id.tv_bank_number);
        TextView tv_bank_name = (TextView) loginDialog.findViewById(R.id.tv_bank_name);
        TextView tv_pay_money = (TextView) loginDialog.findViewById(R.id.tv_pay_money);
        final EditText et_password = (EditText) loginDialog.findViewById(R.id.et_password);
        tv_bank_number.setText("尾号" + "**" + bankNumber.substring(bankNumber.length() - 2));
        tv_bank_name.setText(bankName);
        tv_pay_money.setText(money);

        alertDialogBuilder.setView(loginDialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(et_password.getText().toString())) {
                    ToastUtil.showShort(DrawActivity.this, "支付密码不能为空");
                    return;
                }

                submitDrawInfo(et_password.getText().toString());
                alertDialog.dismiss();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        empty_layout.hide();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
