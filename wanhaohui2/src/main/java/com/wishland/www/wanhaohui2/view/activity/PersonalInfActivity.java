package com.wishland.www.wanhaohui2.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.AccountBean;
import com.wishland.www.wanhaohui2.bean.PersonInfoBean;
import com.wishland.www.wanhaohui2.model.AccountDataSP;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.JudgeUserUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.StringUtil;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/10.
 */

public class PersonalInfActivity extends BaseStyleActivity {
    Unbinder unbinder;
    @BindView(R.id.rl_bank_change)
    RelativeLayout rl_bank_change;
    @BindView(R.id.personalaccount_user_SR)
    EditText personalaccount_user_SR;
    @BindView(R.id.tv_account_phoneNum)
    EditText tv_account_phoneNum;
    @BindView(R.id.tv_account_wecat)
    EditText tv_account_wecat;
    @BindView(R.id.tv_account_qq)
    EditText tv_account_qq;
    @BindView(R.id.tv_account_address)
    EditText tv_account_address;
    @BindView(R.id.tv_tv_account_email)
    EditText tv_tv_account_email;
    @BindView(R.id.ll_account_change_psw)
    LinearLayout ll_account_change_psw;
    @BindView(R.id.rl_bank_info_nothing)
    FrameLayout rl_bank_info_nothing;
    @BindView(R.id.rl_bank_info)
    RelativeLayout rl_bank_info;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.tv_jiejika)
    TextView tv_jiejika;
    @BindView(R.id.tv_bank_number)
    TextView tv_bank_number;
    @BindView(R.id.tv_bank_address)
    TextView tv_bank_address;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.empty_layout)
    EmptyLayout empty_layout;

    private Model instance;
    private UserSP userSP;
    private AccountDataSP accountDataSP;
    private TextView tv_btn;
    private Map<String, String> map;
    private String uMobile;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("个人资料");
        setTvTitle("保存");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        accountDataSP = instance.getAccountDataSP();
        map = new HashMap<>();
    }

    @Override
    public void onResume() {
        super.onResume();

        empty_layout.setLoadingMessage("更新信息中...");
        empty_layout.showLoading();
        PersionRequest();
        bankInfoResquest();
        if ("".equals(tv_account_phoneNum.getText().toString())) {
            tv_right.setVisibility(View.GONE);
            tv_account_phoneNum.setFocusableInTouchMode(true);
            tv_account_phoneNum.setFocusable(true);
            tv_account_phoneNum.requestFocus();
        } else {
            tv_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_personal_info, R.layout.base_toolbar_back_btn);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        unbinder = ButterKnife.bind(this);


    }

    @OnClick({R.id.tv_tv_account_email, R.id.rl_bank_change, R.id.ll_account_change_psw, R.id.rl_bank_info_nothing, R.id.tv_account_phoneNum, R.id.tv_btn})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bank_change:
                showDelateDialog();
                break;
            case R.id.ll_account_change_psw:
                startActivity(new Intent(this, ChangePswActivity.class));
                break;
            case R.id.rl_bank_info_nothing:
                Intent intent = new Intent(this, BindBankActivity.class);
                intent.putExtra("pay_name", personalaccount_user_SR.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_account_phoneNum:
                if (!"".equals(tv_account_phoneNum.getText().toString()) && tv_account_phoneNum.getText().toString().contains("*")) {
                    startActivity(new Intent(this, ChangePhoneActivity.class));
                }
                break;
            case R.id.tv_tv_account_email:
//                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.tv_btn:
                submitUserInfo();
                break;
        }
    }

    private void showDelateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PersonalInfActivity.this);
        alertDialogBuilder.setMessage("银行卡信息需要客服人工修改，确定联系客服吗？");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                empty_layout.setLoadingMessage("连接客服中...");
                empty_layout.showLoading();
                WebUtil.toWebActivity(PersonalInfActivity.this, BaseApi.CustomHtml5, "");
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

    private void submitUserInfo() {
        String wecat = tv_account_wecat.getText().toString();
        String qq = tv_account_qq.getText().toString();
        String address = tv_account_address.getText().toString();
        String uemail = tv_tv_account_email.getText().toString();
        String umobile = tv_account_phoneNum.getText().toString();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String uName = personalaccount_user_SR.getText().toString();
        if (map != null) {
            map.clear();
        }

        map.put("usex", "0");
        if (!"".equals(wecat)) {
            map.put("uwx", wecat);
        }
        if (!"".equals(qq)) {
            map.put("uqq", qq);
        }
        if (!"".equals(uemail)) {
            map.put("uemail", uemail);
        }
        if (!"".equals(address)) {
            map.put("uaddr", address);
        }
        if (uMobile == null || "".equals(uMobile)) {
            if (umobile != null && !"".equals(umobile)) {
                map.put("umobile", umobile);
            } else {
                umobile = "";
            }
        } else {
            umobile = "";
        }

        if (!"".equals(uName)) {
            map.put("uname", uName);
        }

        instance.apiChangeUserInfo("", 0, wecat, qq, uName, umobile, uemail, address, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("个人页面修改完成");
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
                    LogUtil.e("个人页面修改成功" + string);
                    if (status == 200) {
                        ToastUtil.showShort(PersonalInfActivity.this, "保存成功");
                        finish();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(PersonalInfActivity.this, LoginActivity.class, "PersonallInfActivity");
                        } else {
                            ToastUtil.showShort(PersonalInfActivity.this, errorMsg);
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


    private void PersionRequest() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiInfo(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("个人页面请求完成");
                empty_layout.hide();
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("个人页面请求失败：" + e.getMessage());
                empty_layout.hide();
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("个人页面请求成功" + string);
                    empty_layout.hide();
                    if (status == 200) {
                        PersonInfoBean accountBean = instance.getGson().fromJson(string, PersonInfoBean.class);
//                        setAccount(accountBean);
                        setAccountDataSP(accountBean);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(PersonalInfActivity.this, LoginActivity.class, "PersonallInfActivity");
                        } else {
                            ToastUtil.showShort(PersonalInfActivity.this, errorMsg);
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
                    LogUtil.e("个人页面请求成功" + string);
                    if (status == 200) {
                        AccountBean accountBean = instance.getGson().fromJson(string, AccountBean.class);
                        AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();
                        if (financeInfo == null || financeInfo.getBank() == null || financeInfo.getBankAccount() == null) {
                            rl_bank_info_nothing.setVisibility(View.VISIBLE);
                            rl_bank_info.setVisibility(View.GONE);
                        } else {
                            rl_bank_info_nothing.setVisibility(View.GONE);
                            rl_bank_info.setVisibility(View.VISIBLE);
                            setBankInfo(financeInfo);
                        }

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(PersonalInfActivity.this, LoginActivity.class, "PersonallInfActivity");
                        } else {
                            ToastUtil.showShort(PersonalInfActivity.this, errorMsg);
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

    private void setBankInfo(AccountBean.DataBean.FinanceInfoBean financeInfo) {
        tv_bank_name.setText(financeInfo.getBank());
        //银行卡号显示处理
        if (financeInfo.getBankAccount().length() >= 4) {
            tv_bank_number.setText(StringUtil.spaceAt4("************" + financeInfo.getBankAccount().substring(financeInfo.getBankAccount().length() - 4)));
        } else {
            tv_bank_number.setText(StringUtil.spaceAt4("************" + financeInfo.getBankAccount()));
        }
        tv_bank_address.setText(financeInfo.getAccountAddress());
        if (!"".equals(financeInfo.getBankImg()) && financeInfo.getBankImg() != null) {
            Glide.with(PersonalInfActivity.this)
                    .load(financeInfo.getBankImg())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Drawable drawable = new BitmapDrawable(resource);
                            rl_bank_info.setBackgroundDrawable(drawable);
                        }
                    });
        }
    }


//    private void setAccount(AccountBean accountBean) {
//        final AccountBean.DataBean.AccountInfoBean accountInfo = accountBean.getData().getAccountInfo();
//        final AccountBean.DataBean.BalanceInfoBean balanceInfo = accountBean.getData().getBalanceInfo();
//        final AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();
//        accountDataSP.setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, financeInfo.getPayName());
//
//        personalaccount_user_SR.setText(userSP.getString(UserSP.LOGIN_USERNAME));
//        tv_account_phoneNum.setText("");
//
//    }

    private void setAccountDataSP(final PersonInfoBean accountBean) {
        PersonInfoBean.DataBean data = accountBean.getData();
        uMobile = data.getUmobile();
        userSP.setMolibe(UserSP.MOLIBE_NUMBER, data.getUmobile() + "");
        if ("".equals(data.getUmobile())) {
            tv_right.setVisibility(View.GONE);
            tv_account_phoneNum.setFocusableInTouchMode(true);
            tv_account_phoneNum.setFocusable(true);
            tv_account_phoneNum.requestFocus();
        } else {
            tv_right.setVisibility(View.VISIBLE);
            tv_account_phoneNum.setFocusable(false);
            tv_account_phoneNum.setFocusableInTouchMode(false);
            tv_account_phoneNum.setText(data.getUmobile() + "");
        }
        //真实姓名只能修改一次
        if (!"".equals(data.getUname())) {
            personalaccount_user_SR.setText(data.getUname() + "");
            personalaccount_user_SR.setFocusable(false);
            personalaccount_user_SR.setFocusableInTouchMode(false);
        } else {
            personalaccount_user_SR.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        tv_tv_account_email.setText(data.getUemail() + "");
        if (!(data.getUwx() == null)) {
            tv_account_wecat.setText(data.getUwx());
        }
        if (!(data.getUwx() == null)) {
            tv_account_qq.setText(data.getUqq());
        }
        if (!(data.getUwx() == null)) {
            tv_account_address.setText(data.getUaddr());
        }

        instance.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
//                AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();
//                accountDataSP.setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, financeInfo.getPayName());
//                accountDataSP.setA_Bank_Name(AccountDataSP.ACCOUNT_BANK_NAME, financeInfo.getBank() + "");
//                accountDataSP.setA_Bank_Number(AccountDataSP.ACCOUNT_BANK_NUMBER, financeInfo.getBankAccount() + "");
//                accountDataSP.setA_Bank_Address(AccountDataSP.ACCOUNT_BANK_ADDRESS, financeInfo.getAccountAddress() + "");
//                accountDataSP.setA_Bank_money(AccountDataSP.ACCOUNT_BANK_MONEY, accountBean.getData().getBalanceInfo().getBalance() + "");


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
