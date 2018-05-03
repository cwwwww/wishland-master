package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseActivity;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.model.AccountDataSP;
import com.wishland.www.wanhaohui2.model.MobclickEvent;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.CodeUtils;
import com.wishland.www.wanhaohui2.utils.JudgeUserUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.greenrobot.eventbus.EventBus;
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
 * Created by admin on 2017/10/10.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_first_pass_word)
    EditText etFirstPassWord;
    @BindView(R.id.et_second_pass_word)
    EditText etSecondPassWord;
    @BindView(R.id.et_true_name)
    EditText etTrueName;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.iv_register_code)
    ImageView ivCode;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.tv_fast_register)
    TextView tvFastRegister;
    @BindView(R.id.tv_normal_register)
    TextView tvNormalRegister;
    @BindView(R.id.ll_fast_register)
    LinearLayout llFastRegister;
    @BindView(R.id.ll_normal_register)
    LinearLayout llNormalRegister;
    @BindView(R.id.tv_fast_view)
    TextView tvFastView;
    @BindView(R.id.tv_normal_view)
    TextView tvNormalView;
    @BindView(R.id.cb_eye1)
    CheckBox cbEye1;
    @BindView(R.id.cb_eye2)
    CheckBox cbEye2;
    @BindView(R.id.tv_code)
    TextView sendVertify;
    @BindView(R.id.login_edit_passwork)
    EditText vertifyCode;
    @BindView(R.id.login_edit_username)
    EditText phoneNumber;

    private Model instance;
    private UserSP userSP;
    private CodeUtils codeUtils;
    private Map<String, String> map;
    private String userName;
    private String firstPassWord;
    private String secondPassWord;
    private String time;
    private String phone;
    private String token;
    private int recLen = 60;
    private Timer timer;
    private TimerTask timerTask;
    private int isFastReg = 0;
    private String rightCode;
    private String fromType;

    @Override

    protected void initVariable() {
        map = new HashMap<>();
        codeUtils = new CodeUtils();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        fromType = getIntent().getStringExtra("fromType");
    }

    @Override
    protected void initDate() {

    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getSecurityCode();
        initEditView();
    }

    private void initEditView() {
        etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etUserName.setCompoundDrawables(getDrawableById(R.drawable.icon_user_focus), null, null, null);
                } else {
                    etUserName.setCompoundDrawables(getDrawableById(R.drawable.icon_user_no), null, null, null);
                }
            }
        });

        etFirstPassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etFirstPassWord.setCompoundDrawables(getDrawableById(R.drawable.icon_pass_word_focus), null, null, null);
                } else {
                    etFirstPassWord.setCompoundDrawables(getDrawableById(R.drawable.icon_pass_word_no), null, null, null);
                }
            }
        });

        etSecondPassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etSecondPassWord.setCompoundDrawables(getDrawableById(R.drawable.icon_pass_word_focus1), null, null, null);
                } else {
                    etSecondPassWord.setCompoundDrawables(getDrawableById(R.drawable.icon_pass_word_no1), null, null, null);
                }
            }
        });

        etTrueName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etTrueName.setCompoundDrawables(getDrawableById(R.drawable.icon_phone_focus), null, null, null);
                } else {
                    etTrueName.setCompoundDrawables(getDrawableById(R.drawable.icon_phone_no), null, null, null);
                }
            }
        });

        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etCode.setCompoundDrawables(getDrawableById(R.drawable.icon_protect_focus), null, null, null);
                } else {
                    etCode.setCompoundDrawables(getDrawableById(R.drawable.icon_protect_no), null, null, null);
                }
            }
        });

        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    phoneNumber.setCompoundDrawables(getDrawableById(R.drawable.icon_phone_focus), null, null, null);
                } else {
                    phoneNumber.setCompoundDrawables(getDrawableById(R.drawable.icon_phone_no), null, null, null);
                }
            }
        });

        vertifyCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    vertifyCode.setCompoundDrawables(getDrawableById(R.drawable.icon_protect_focus), null, null, null);
                } else {
                    vertifyCode.setCompoundDrawables(getDrawableById(R.drawable.icon_protect_no), null, null, null);
                }
            }
        });
    }

    @OnClick({R.id.ll_login, R.id.bt_register, R.id.iv_register_code, R.id.cb_eye1, R.id.cb_eye2, R.id.tv_code, R.id.ll_fast, R.id.ll_normal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_login: {
                if (fromType != null && fromType.equals("main")) {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    finish();
                }
            }
            break;
            case R.id.tv_code: {
                sendQrCode();
            }
            break;
            case R.id.bt_register: {
                if (isFastReg == 0) {
                    emptyLayout.showLoading();
                    register();
                } else {
//                    startActivity(new Intent(RegisterActivity.this, SetLoginPWActivity.class));
                    String phoneNum = phoneNumber.getText().toString().trim();
                    String vertifyNum = this.vertifyCode.getText().toString().trim();
                    if (JudgeUserUtil.judgeQphone(phoneNum, 11)) {
                        if (JudgeUserUtil.judgeQphone(vertifyNum, 4)) {
                            fastReg();
                        } else {
                            ToastUtil.showShort(this, "验证码格式错误,请重新输入.");
                        }
                    } else {
                        ToastUtil.showShort(this, "手机号格式错误,请重新输入.");
                    }
                }
            }
            break;
            case R.id.iv_register_code: {
                getSecurityCode();
            }
            break;
            case R.id.ll_fast: {
                llFastRegister.setVisibility(View.VISIBLE);
                llNormalRegister.setVisibility(View.GONE);
                tvFastRegister.setTextColor(getResources().getColor(R.color.text_color8));
                tvFastView.setVisibility(View.VISIBLE);
                tvNormalRegister.setTextColor(getResources().getColor(R.color.text_color1));
                tvNormalView.setVisibility(View.GONE);
                isFastReg = 1;
            }
            break;
            case R.id.ll_normal: {
                llFastRegister.setVisibility(View.GONE);
                llNormalRegister.setVisibility(View.VISIBLE);
                tvFastRegister.setTextColor(getResources().getColor(R.color.text_color1));
                tvFastView.setVisibility(View.GONE);
                tvNormalRegister.setTextColor(getResources().getColor(R.color.text_color8));
                tvNormalView.setVisibility(View.VISIBLE);
                isFastReg = 0;
            }
            break;
            case R.id.cb_eye1: {
                if (cbEye1.isChecked()) {
                    etFirstPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etFirstPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etFirstPassWord.setSelection(etFirstPassWord.getText().toString().trim().length());
            }
            break;
            case R.id.cb_eye2: {
                if (cbEye2.isChecked()) {
                    etSecondPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etSecondPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etSecondPassWord.setSelection(etSecondPassWord.getText().toString().trim().length());
            }
            break;
        }
    }

    private void fastReg() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        final String mobile = phoneNumber.getText().toString().trim();
        String verify = vertifyCode.getText().toString().trim();
        if ("".equals(mobile)) {
            if ("".equals(verify)) {
                ToastUtil.showShort(this, "验证码格式不正确");
                return;
            }
            ToastUtil.showShort(this, "手机号不能为空");
            return;
        }
        emptyLayout.showLoading();
        if (map.size() != 0) {
            map.clear();
        }
        map.put("code", verify);

        emptyLayout.setLoadingMessage("验证码验证中");
        instance.apiVerifyCode(verify, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showUI(RegisterActivity.this, "网络异常...");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        ToastUtil.showShort(RegisterActivity.this, "请设置登录密码");
                        Intent intent = new Intent(RegisterActivity.this, SetLoginPWActivity.class);
                        intent.putExtra("mobile", mobile);
                        startActivity(intent);
                    } else {
                        ToastUtil.showShort(RegisterActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void register() {
        userName = etUserName.getText().toString().trim();
        firstPassWord = etFirstPassWord.getText().toString().trim();
        secondPassWord = etSecondPassWord.getText().toString().trim();
        phone = etTrueName.getText().toString().trim();
        String verityCode = etCode.getText().toString().trim();
        token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (JudgeUserUtil.judgeUsername(userName)) {
            if (JudgeUserUtil.judgePassword(firstPassWord) && JudgeUserUtil.judgePassword(secondPassWord)) {
                if (firstPassWord.equals(secondPassWord)) {
                    if (JudgeUserUtil.judgeQphone(phone, 11) || TextUtils.isEmpty(phone)) {
                        if (TextUtils.isEmpty(verityCode) || !verityCode.equals(rightCode)) {
                            ToastUtil.showShort(RegisterActivity.this, "请输入正确的验证码！");
                            return;
                        }


                        if (map.size() != 0) {
                            map.clear();
                        }
                        map.put("username", userName);
                        map.put("rpassword", firstPassWord);
                        if (!TextUtils.isEmpty(phone)) {
                            map.put("mobile", phone);
                        }
                        map.put("code", verityCode);
                        map.put("device", BaseApi.DEVICE);
                        instance.apiSignIn(userName, firstPassWord, phone, verityCode, BaseApi.DEVICE, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {
                                LogUtil.e("注册完成");
                                emptyLayout.hide();
                            }

                            @Override
                            public void onError(Throwable e) {
                                emptyLayout.hide();
                                LogUtil.e("注册异常：" + e.getMessage());
                                MobclickAgent.onEvent(RegisterActivity.this, MobclickEvent.REGISTER_STATE, "Error");
                            }

                            @Override
                            public void onNext(ResponseBody json) {
                                emptyLayout.hide();

                                try {
                                    String string = json.string();
                                    JSONObject jsonObject = instance.getJsonObject(string);
                                    instance.setToken_SP(jsonObject.optString("token"));
                                    int status = jsonObject.optInt("status");
                                    if (status == 200) {
                                        ToastUtil.showShort(RegisterActivity.this, "注册成功");
                                        deleteUserSP();
                                        EventBus.getDefault().post(new SuccessBean("success", "register"));
                                        MobclickAgent.onEvent(RegisterActivity.this, MobclickEvent.REGISTER_STATE, "Success");
                                    } else {
                                        MobclickAgent.onEvent(RegisterActivity.this, MobclickEvent.REGISTER_STATE, String.valueOf(status));
                                        String error = jsonObject.optString("errorMsg");
                                        ToastUtil.showShort(RegisterActivity.this, error);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        emptyLayout.hide();
                        ToastUtil.showShort(RegisterActivity.this, "手机号码格式不正确！");
                    }
                } else {
                    emptyLayout.hide();
                    ToastUtil.showShort(this, "两次密码不一致，请重新输入");
                }
            } else {
                emptyLayout.hide();
                ToastUtil.showShort(this, "密码格式不正确，请设置6-20位数字和字母组合的密码！");
            }
        } else {
            emptyLayout.hide();
            ToastUtil.showShort(this, "账号格式不正确，请输入字母开头6-12位字母数字");
        }
    }

    private void sendQrCode() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String mobile = phoneNumber.getText().toString().trim();
        if ("".equals(mobile)) {
            ToastUtil.showShort(this, "手机号不能为空");
            return;
        }
        emptyLayout.setLoadingMessage("验证码发送中");
        emptyLayout.showLoading();

        if (map.size() != 0) {
            map.clear();
        }
        map.put("exist", "false");
        map.put("mobile", mobile);
        map.put("get", "true");

        instance.apiSms("false", mobile, "true", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("发送验证码请求完成");
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("发送验证码请求失败" + e.toString());
                ToastUtil.showUI(RegisterActivity.this, "网络异常...");
            }

            @Override
            public void onNext(ResponseBody json) {
                emptyLayout.hide();
                try {
                    String string = json.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        String code = jsonObject.optJSONObject("data").optString("code");
                        ToastUtil.showShort(RegisterActivity.this, "发送成功");
//                        Log.e("cww", code + "验证码");
//                        ToastUtil.showShort(RegisterActivity.this, code);
                        setTimer();
                    } else {
                        ToastUtil.showShort(RegisterActivity.this, jsonObject.optString("errorMsg"));
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
                case 1:
                    sendVertify.setText(recLen + "S");
                    sendVertify.setClickable(false);
                    if (recLen < 1) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        sendVertify.setClickable(true);
                        sendVertify.setText("重新获取");
                        recLen = 60;
                        sendVertify.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape));
                        sendVertify.setTextColor(getResources().getColor(R.color.blue));
                    }
            }
        }
    };

    private void setTimer() {
        timer = new Timer();
        sendVertify.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape_gray));
        sendVertify.setTextColor(getResources().getColor(R.color.white));

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

    private void deleteUserSP() {
        userSP.setSuccess(UserSP.LOGIN_SUCCESS, 1);
        userSP.setUserName(UserSP.LOGIN_USERNAME, userName);
        userSP.setCheckedSP(UserSP.LOGIN_CHECKED, false);
        instance.getAccountDataSP().setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, phone);

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void getSecurityCode() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiCode(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("验证码请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("验证码请求异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody code) {
                try {
                    String string = code.string();

                    JSONObject json = instance.getJsonObject(string);
                    int status = json.optInt("status");
                    instance.setToken_SP(json.optString("token"));
                    if (status == 200) {
                        JSONObject jsonObject = json.optJSONObject("data");
                        rightCode = jsonObject.optInt("code") + "";
                        Bitmap bitmap = codeUtils.createBitmap(rightCode);
                        ivCode.setImageBitmap(bitmap);
                    } else {
                        ToastUtil.showShort(RegisterActivity.this, "网络异常");
                    }

                    LogUtil.e("验证码请求成功");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public Drawable getDrawableById(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }
}
