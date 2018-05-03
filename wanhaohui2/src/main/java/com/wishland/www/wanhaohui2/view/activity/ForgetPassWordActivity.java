package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.CodeBean;
import com.wishland.www.wanhaohui2.bean.VerifyCodeBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/11/9.
 */

public class ForgetPassWordActivity extends BaseStyleActivity {
    @BindView(R.id.bt_find_custom_dis)
    Button btFindCustomDis;
    @BindView(R.id.bt_find_phone)
    Button btFindPhone;
    @BindView(R.id.bt_find_phone_dis)
    Button btFindPhoneDis;
    @BindView(R.id.bt_find_email)
    Button btFindEmail;
    @BindView(R.id.bt_find_email_dis)
    Button btFindEmailDis;
    @BindView(R.id.edit_phone_number)
    EditText edPhoneNumber;
    @BindView(R.id.edit_phone_code)
    EditText edPhoneCode;
    @BindView(R.id.edit_email)
    EditText edEmail;
    @BindView(R.id.edit_email_code)
    EditText edEmailCode;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.tv_get_code1)
    TextView tvGetCode1;
    @BindView(R.id.tv_get_code2)
    TextView tvGetCode2;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    private int recLen = 60;
    private Timer timer;
    private TimerTask timerTask;

    private int recLenEmail = 60;
    private Timer timerEmail;
    private TimerTask timerTaskEmail;
    private Model model;
    private UserSP userSP;
    private String mPhoneCode;
    private String mEmailCode;


    @Override
    protected void initVariable() {
        model = Model.getInstance();
        userSP = model.getUserSP();
    }

    @Override
    protected void initDate() {
        setTitle("找回密码");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forget_pass_word, R.layout.base_toolbar_back);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.bt_find_phone_dis, R.id.bt_find_email_dis, R.id.bt_find_custom_dis, R.id.bt_verify, R.id.tv_get_code1, R.id.tv_get_code2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_find_phone_dis: {
                llPhone.setVisibility(View.VISIBLE);
                llEmail.setVisibility(View.GONE);
                btFindCustomDis.setVisibility(View.VISIBLE);
                btFindEmail.setVisibility(View.GONE);
                btFindEmailDis.setVisibility(View.VISIBLE);
                btFindPhone.setVisibility(View.VISIBLE);
                btFindPhoneDis.setVisibility(View.GONE);
            }
            break;
            case R.id.bt_find_email_dis: {
                llPhone.setVisibility(View.GONE);
                llEmail.setVisibility(View.VISIBLE);
                btFindCustomDis.setVisibility(View.VISIBLE);
                btFindEmail.setVisibility(View.VISIBLE);
                btFindEmailDis.setVisibility(View.GONE);
                btFindPhone.setVisibility(View.GONE);
                btFindPhoneDis.setVisibility(View.VISIBLE);
            }
            break;
            case R.id.bt_find_custom_dis: {
                Intent intent = new Intent(ForgetPassWordActivity.this, DetailsHtmlPageActivity.class);
                intent.setAction(BaseApi.NEWHTML);
                intent.putExtra(BaseApi.HTML5DATA, BaseApi.CustomHtml5);
                startActivity(intent);
            }
            break;
            case R.id.tv_get_code1: {
                getPhoneCode();
            }
            break;
            case R.id.tv_get_code2: {
                getEmailCode();
            }
            break;
            case R.id.bt_verify: {
                if (btFindPhone.getVisibility() == View.VISIBLE) {
                    verifyPhoneCode();
                } else if (btFindEmail.getVisibility() == View.VISIBLE) {
                    verifyEmailCode();
                }
            }
            break;
        }
    }

    private void getPhoneCode() {
        final String phoneNumber = edPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.showShort(ForgetPassWordActivity.this, "请输入您绑定的手机号码！");
            return;
        }
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        final Map<String, String> map = new HashMap();
        map.put("exist", "true");
        map.put("mobile", phoneNumber);
        map.put("get", "true");
        model.apiSms("true", phoneNumber, "true", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e(e.getMessage());
                ToastUtil.showShort(ForgetPassWordActivity.this, "获取验证码异常");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = model.getJsonObject(s);
                    model.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        setTimerPhone();
                        CodeBean codeBean = new Gson().fromJson(s, CodeBean.class);
                        mPhoneCode = codeBean.getData().getCode();
                        //      ToastUtil.showShort(ForgetPassWordActivity.this, mPhoneCode);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        LogUtil.e("cww", errorMsg);
                        ToastUtil.showShort(ForgetPassWordActivity.this, errorMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getEmailCode() {
        final String edEmailNumber = edEmail.getText().toString().trim();
        if (TextUtils.isEmpty(edEmailNumber)) {
            ToastUtil.showShort(ForgetPassWordActivity.this, "请输入您绑定的电子邮箱！");
            return;
        }
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        final Map<String, String> map = new HashMap();
        map.put("email", edEmailNumber);
        map.put("get", "true");
        map.put("exist", "true");

        model.apiEmail(edEmailNumber, "true", "true", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e(e.getMessage());
                ToastUtil.showShort(ForgetPassWordActivity.this, "获取验证码异常");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = model.getJsonObject(s);
                    model.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        setTimerEmail();
                        CodeBean codeBean = new Gson().fromJson(s, CodeBean.class);
                        mEmailCode = codeBean.getData().getCode();
                        //      ToastUtil.showShort(ForgetPassWordActivity.this, mEmailCode);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        LogUtil.e("cww", errorMsg);
                        ToastUtil.showShort(ForgetPassWordActivity.this, errorMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void verifyEmailCode() {
        String email = edEmail.getText().toString().trim();
        String emailCode = edEmailCode.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showShort(ForgetPassWordActivity.this, "请输入您绑定的邮箱！");
            return;
        }
        if (TextUtils.isEmpty(emailCode)) {
            ToastUtil.showShort(ForgetPassWordActivity.this, "请输入验证码！");
            return;
        }
        emptyLayout.showLoading();
        //验证码是否有效
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap();
        map.put("code", emailCode);
        model.apiVerifyCode(emailCode, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(ForgetPassWordActivity.this, "验证码不正确！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = model.getJsonObject(s);
                    model.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        VerifyCodeBean verifyCodeBean = new Gson().fromJson(s, VerifyCodeBean.class);
                        if (verifyCodeBean.getData().isVerify()) {
                            Intent intent = new Intent(ForgetPassWordActivity.this, FindPassWordActivity.class);
                            intent.putExtra("code", mEmailCode);
                            startActivityForResult(intent, 0);
                        } else {
                            ToastUtil.showShort(ForgetPassWordActivity.this, "验证码不正确！");
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        LogUtil.e("cww", errorMsg);
                        ToastUtil.showShort(ForgetPassWordActivity.this, errorMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void verifyPhoneCode() {
        String phoneNumber = edPhoneNumber.getText().toString().trim();
        String phoneCode = edPhoneCode.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtil.showShort(ForgetPassWordActivity.this, "请输入您绑定的手机号码！");
            return;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            ToastUtil.showShort(ForgetPassWordActivity.this, "请输入验证码！");
            return;
        }
        emptyLayout.showLoading();
        //验证码是否有效
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap();
        map.put("code", phoneCode);
        model.apiVerifyCode(phoneCode, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(ForgetPassWordActivity.this, "验证码不正确！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = model.getJsonObject(s);
                    model.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        VerifyCodeBean verifyCodeBean = new Gson().fromJson(s, VerifyCodeBean.class);
                        if (verifyCodeBean.getData().isVerify()) {
                            Intent intent = new Intent(ForgetPassWordActivity.this, FindPassWordActivity.class);
                            intent.putExtra("code", mPhoneCode);
                            startActivityForResult(intent, 0);
                        } else {
                            ToastUtil.showShort(ForgetPassWordActivity.this, "验证码不正确！");
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        LogUtil.e("cww", errorMsg);
                        ToastUtil.showShort(ForgetPassWordActivity.this, errorMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    tvGetCode1.setText(recLen + "s");
                    tvGetCode1.setClickable(false);
                    if (recLen < 1) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        tvGetCode1.setClickable(true);
                        tvGetCode1.setText("重新获取");
                        recLen = 60;
                        tvGetCode1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape));
                        tvGetCode1.setTextColor(getResources().getColor(R.color.blue));
                    }
                }
                break;
                case 2: {
                    tvGetCode2.setText(recLenEmail + "s");
                    tvGetCode2.setClickable(false);
                    if (recLenEmail < 1) {
                        if (timerEmail != null) {
                            timerEmail.cancel();
                        }
                        if (timerTaskEmail != null) {
                            timerTaskEmail.cancel();
                        }
                        tvGetCode2.setClickable(true);
                        tvGetCode2.setText("重新获取");
                        recLenEmail = 60;
                        tvGetCode2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape));
                        tvGetCode2.setTextColor(getResources().getColor(R.color.blue));
                    }
                }
                break;

            }
        }
    };

    private void setTimerPhone() {
        timer = new Timer();
        tvGetCode1.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape_gray));
        tvGetCode1.setTextColor(getResources().getColor(R.color.white));

        timerTask = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void setTimerEmail() {
        timerEmail = new Timer();
        tvGetCode2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape_gray));
        tvGetCode2.setTextColor(getResources().getColor(R.color.white));

        timerTaskEmail = new TimerTask() {
            @Override
            public void run() {
                recLenEmail--;
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        };
        timerEmail.schedule(timerTaskEmail, 0, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
