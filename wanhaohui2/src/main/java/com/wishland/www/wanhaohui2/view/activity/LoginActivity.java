package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseActivity;
import com.wishland.www.wanhaohui2.bean.LoginAdvertiseBean;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.bean.UrlPingInfo;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeUserUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.MD5Utils;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_CHECKED;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_PASSWORD;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_TOKEN;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_TYPE;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_USERNAME;
import static com.wishland.www.wanhaohui2.model.UserSP.Login_OUT;

/**
 * Created by admin on 2017/10/11.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_password)
    EditText etPassWord;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.ll_fast_login)
    LinearLayout llFastLogin;
    @BindView(R.id.ll_normal_login)
    LinearLayout llNormalLogin;
    @BindView(R.id.tv_fast_login)
    TextView tvFastLogin;
    @BindView(R.id.tv_normal_login)
    TextView tvNormalLogin;
    @BindView(R.id.tv_fast_view)
    TextView tvFastView;
    @BindView(R.id.tv_normal_view)
    TextView tvNormalView;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.bt_switch)
    SwitchButton switchButton;
    @BindView(R.id.cb_eye)
    CheckBox cbEye;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.login_edit_username)
    TextView phoneNumber;
    @BindView(R.id.login_edit_passwork)
    TextView vertifyNum;
    @BindView(R.id.iv_login_advertising)
    ImageView loginAdvertising;
    @BindView(R.id.tx_connection_problem)
    TextView tx_connection_problem;
    @BindView(R.id.iv_cha1)
    ImageView ivCha1;
    @BindView(R.id.iv_cha2)
    ImageView ivCha2;
    private String userName;
    private String userPassWord;
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private int recLen = 60;
    private Timer timer;
    private TimerTask timerTask;
    private int isFastLogin = 1;
    private String advertiseUrl = "";


    @Override
    protected void initVariable() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
    }

    @Override
    protected void initDate() {
        getAdvertisingInfo();
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUser();
        pingAllUrl();
        initEditView();
    }

    private void initEditView() {
        etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etUserName.setCompoundDrawables(getDrawableById(R.drawable.icon_user_focus), null, null, null);
                    if (!"".equalsIgnoreCase(etUserName.getText().toString().trim())) {
                        ivCha1.setVisibility(View.VISIBLE);
                    } else {
                        ivCha1.setVisibility(View.GONE);
                    }
                } else {
                    etUserName.setCompoundDrawables(getDrawableById(R.drawable.icon_user_no), null, null, null);
                    ivCha1.setVisibility(View.GONE);
                }
            }
        });

        etPassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etPassWord.setCompoundDrawables(getDrawableById(R.drawable.icon_pass_word_focus), null, null, null);
                    if (!"".equalsIgnoreCase(etPassWord.getText().toString().trim())) {
                        ivCha2.setVisibility(View.VISIBLE);
                    } else {
                        ivCha2.setVisibility(View.GONE);
                    }
                } else {
                    etPassWord.setCompoundDrawables(getDrawableById(R.drawable.icon_pass_word_no), null, null, null);
                    ivCha2.setVisibility(View.GONE);
                }
            }
        });

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!"".equalsIgnoreCase(etUserName.getText().toString().trim())) {
                    ivCha1.setVisibility(View.VISIBLE);
                } else {
                    ivCha1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!"".equalsIgnoreCase(etPassWord.getText().toString().trim())) {
                    ivCha2.setVisibility(View.VISIBLE);
                } else {
                    ivCha2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initUser() {
        boolean isCheck = userSP.getBoolean(LOGIN_CHECKED);
        etUserName.setText(userSP.getString(LOGIN_USERNAME));
        isFastLogin = userSP.getLoginType(LOGIN_TYPE);
        if (isFastLogin == 0) {
            setFastLoginView();
        } else {
            setNormalLoginView();
        }
        if (isCheck) {
            switchButton.setChecked(isCheck);
            etPassWord.setText(userSP.getString(LOGIN_PASSWORD));
        } else {
            deleteUserSP();
        }
    }


    @OnClick({R.id.bt_login, R.id.ll_register, R.id.tv_forget, R.id.cb_eye, R.id.tv_code,
            R.id.ll_fast, R.id.ll_normal, R.id.iv_login_advertising,
            R.id.tx_connection_problem, R.id.iv_cha1, R.id.iv_cha2, R.id.ll_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tx_connection_problem:
                setURLPicker();
                break;
            case R.id.bt_login: {
                if (isFastLogin == 1) {
                    getEditTextData();
                    if (userName != null && !"".equals(userName)) {
                        if (userPassWord != null && !"".equals(userPassWord)) {
                            login();
                        } else {
                            ToastUtil.showShort(this, "请输入密码！");
                        }
                    } else {
                        ToastUtil.showShort(this, "请输入用户名！");
                    }
                } else {
                    String phoneNum = phoneNumber.getText().toString().trim();
                    String vertifyNum = this.vertifyNum.getText().toString().trim();
                    if (JudgeUserUtil.judgeQphone(phoneNum, 11)) {
                        if (JudgeUserUtil.judgeQphone(vertifyNum, 4)) {
                            fastLogin();
                        } else {
                            ToastUtil.showShort(this, "验证码格式错误,请重新输入.");
                        }
                    } else {
                        ToastUtil.showShort(this, "手机号格式错误,请重新输入.");
                    }
                }
            }
            break;
            case R.id.ll_fast: {
                setFastLoginView();

            }


            break;
            case R.id.ll_normal: {
                setNormalLoginView();

            }
            case R.id.iv_login_advertising: {
                //跳转到广告页面
                if (!"".equals(advertiseUrl)) {
                    WebUtil.toWebActivity(LoginActivity.this, advertiseUrl, "");
                }

            }
            break;
            case R.id.ll_register: {
                startActivity(new Intent(this, RegisterActivity.class));
            }
            break;
            case R.id.tv_forget: {
                startActivity(new Intent(this, ForgetPassWordActivity.class));
            }
            break;
            case R.id.cb_eye: {
                if (cbEye.isChecked()) {
                    etPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPassWord.setSelection(etPassWord.getText().toString().trim().length());
            }
            break;
            case R.id.tv_code: {
                sendQrCode();
            }
            break;
            case R.id.iv_cha1: {
                etUserName.setText("");
            }
            break;
            case R.id.iv_cha2: {
                etPassWord.setText("");
            }
            break;
            case R.id.ll_close: {
                finish();
            }
            break;
        }
    }


    private void getAdvertisingInfo() {
        String token = userSP.getToken(LOGIN_TOKEN);
        instance.apiRequestAdvertise(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                ToastUtil.showUI(LoginActivity.this, "网络异常...");
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
                        LoginAdvertiseBean loginAdvertiseBean = new Gson().fromJson(string, LoginAdvertiseBean.class);
                        LoginAdvertiseBean.AdvertiseData data = loginAdvertiseBean.getData();
                        if (data != null) {
                            if (!"".equals(data.getImg()) && data.getImg() != null) {
                                Glide.with(LoginActivity.this)
                                        .load(data.getImg())
                                        .crossFade()
                                        .into(loginAdvertising);
                            }
                            advertiseUrl = data.getUrl();
                        }

                    } else {
                        ToastUtil.showShort(LoginActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setFastLoginView() {
        llFastLogin.setVisibility(View.VISIBLE);
        llNormalLogin.setVisibility(View.GONE);
        tvFastLogin.setTextColor(getResources().getColor(R.color.text_color8));
        tvFastView.setVisibility(View.VISIBLE);
        tvNormalLogin.setTextColor(getResources().getColor(R.color.text_color1));
        tvNormalView.setVisibility(View.GONE);
        isFastLogin = 0;
    }

    private void setNormalLoginView() {
        llFastLogin.setVisibility(View.GONE);
        llNormalLogin.setVisibility(View.VISIBLE);
        tvFastLogin.setTextColor(getResources().getColor(R.color.text_color1));
        tvFastView.setVisibility(View.GONE);
        tvNormalLogin.setTextColor(getResources().getColor(R.color.text_color8));
        tvNormalView.setVisibility(View.VISIBLE);
        isFastLogin = 1;
    }

    private void fastLogin() {
        String token = userSP.getToken(LOGIN_TOKEN);
        String mobile = phoneNumber.getText().toString().trim();
        String vertify = vertifyNum.getText().toString().trim();
        if ("".equals(mobile)) {
            if ("".equals(vertify)) {
                ToastUtil.showShort(this, "验证码格式不正确");
                return;
            }
            ToastUtil.showShort(this, "手机号不能为空");
            return;
        }
        emptyLayout.setLoadingMessage("正在登录...");
        emptyLayout.showLoading();

        if (map.size() != 0) {
            map.clear();
        }
        map.put("mobile", mobile);
        map.put("code", vertify);

        instance.apiFastLogin(mobile, vertify, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("快速登录请求完成");
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("快速登录请求失败" + e.toString());
                ToastUtil.showUI(LoginActivity.this, "网络异常...");
            }

            @Override
            public void onNext(ResponseBody json) {
                emptyLayout.hide();
                try {
                    String string = json.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("快速登录请求token:" + string);
                    if (status == 200) {
                        EventBus.getDefault().post(new SuccessBean("success", "login"));
                        loginInSuccess(false);
                        ToastUtil.showShort(LoginActivity.this, "登录成功！");
                    } else {
                        ToastUtil.showShort(LoginActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //  ToastUtil.showUI(LoginActivity.this, "快速登录失败...");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  ToastUtil.showUI(LoginActivity.this, "快速登录失败...");
                }
            }
        });
    }

    private void sendQrCode() {
        String token = userSP.getToken(LOGIN_TOKEN);
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
        map.put("exist", "true");
        map.put("mobile", mobile);
        map.put("get", "true");

        instance.apiSms("true", mobile, "true", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("发送验证码请求完成");
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("发送验证码请求失败" + e.toString());
                ToastUtil.showUI(LoginActivity.this, "网络异常...");
            }

            @Override
            public void onNext(ResponseBody json) {
                emptyLayout.hide();
                try {
                    String string = json.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("发送验证码请求token:" + string);
                    if (status == 200) {
                        ToastUtil.showShort(LoginActivity.this, "发送成功");
                        setTimer();
                    } else {
                        ToastUtil.showShort(LoginActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //ToastUtil.showUI(LoginActivity.this, "发送失败...");
                } catch (JSONException e) {
                    e.printStackTrace();
                    // ToastUtil.showUI(LoginActivity.this, "发送失败...");
                }
            }
        });
    }


    private void login() {
        emptyLayout.setLoadingMessage("登录中...");
        emptyLayout.showLoading();
        String token = userSP.getToken(LOGIN_TOKEN);
        final boolean checked = switchButton.isChecked();
        String sps = MD5Utils.toMD5(userPassWord);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("username", userName);
        map.put("password", sps);

        instance.api(userName, sps, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("loginin请求完成");
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("loginin请求失败" + e.toString());
                ToastUtil.showUI(LoginActivity.this, "网络异常...");
                deleteUserSP();
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
                        String simple = jsonObject.optJSONObject("data").optString("simple");
                        if ("true".equals(simple)) {
                            ToastUtil.showLong(LoginActivity.this,
                                    "温馨提示：您所设置的登录密码过于简单，为了您的账号安全，请登录后前往个人资料修改密码，谢谢您。");
                        } else {
                            ToastUtil.showShort(LoginActivity.this, "登录成功！");
                        }
                        EventBus.getDefault().post(new SuccessBean("success", "login"));
                        loginInSuccess(checked);

                        MobclickAgent.onProfileSignIn(userName);
                    } else {
                        deleteUserSP();
                        String errorMsg = jsonObject.optString("errorMsg");
//                        MyToastUtil.getToastEmail().ToastShow(LoginActivity.this, null, errorMsg);
                        ToastUtil.showUI(LoginActivity.this, errorMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginInSuccess(boolean checked) {
        if (checked) { //判断当前checked是否选中
            userSP.setCheckedSP(LOGIN_CHECKED, true);
            userSP.setPassWord(LOGIN_PASSWORD, userPassWord);
        } else {
            deleteUserSP();
        }
        userSP.setLoginOut(Login_OUT, false);
        userSP.setLoginType(LOGIN_TYPE, isFastLogin);
        userSP.setUserName(LOGIN_USERNAME, userName);
        userSP.setSuccess(UserSP.LOGIN_SUCCESS, 1);
        Intent intent = new Intent();
        intent.putExtra("USERNAME", userName);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getEditTextData() {
        userName = etUserName.getText().toString().trim();
        userPassWord = etPassWord.getText().toString().trim();
    }

    public void deleteUserSP() {
        userSP.setCheckedSP(LOGIN_CHECKED, false);
        userSP.setPassWord(LOGIN_PASSWORD, "");
        etPassWord.setText("");
        switchButton.setChecked(false);
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tv_code.setText(recLen + "S");
                    tv_code.setClickable(false);
                    if (recLen < 1) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        tv_code.setClickable(true);
                        tv_code.setText("重新获取");
                        recLen = 60;
                        tv_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape));
                        tv_code.setTextColor(getResources().getColor(R.color.blue));
                    }
            }
        }
    };

    private void setTimer() {
        timer = new Timer();
        tv_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_verify_shape_gray));
        tv_code.setTextColor(getResources().getColor(R.color.white));

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

    int lineIndex = 1;
    final List<UrlPingInfo> urlPing = new ArrayList<UrlPingInfo>();

    private void pingAllUrl() {
        ScheduledExecutorService pools = Executors.newSingleThreadScheduledExecutor();
        final ArrayList<String> urlArr = UserSP.getSPInstance().getIpPools();
        if (urlArr != null) {
            if (urlArr.size() > 1) {
                for (int i = 0; i < urlArr.size(); i++) {
                    final String url = urlArr.get(i);

                    final Runnable rn = new Runnable() {
                        @Override
                        public void run() {
                            long pingSpeed = pingUrl(url);
                            UrlPingInfo ping = new UrlPingInfo();
                            ping.PingValue = pingSpeed;
                            ping.URLString = url;
                            ping.URLTitle = "线路" + lineIndex;

                            urlPing.add(ping);
                            lineIndex++;
                        }
                    };
                    pools.schedule(rn, 1, TimeUnit.SECONDS);
                }
            }
        }
    }


    private long pingUrl(String urlStr) {
        long startPing = System.currentTimeMillis();
        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setConnectTimeout(1000 * 3); // mTimeout is in seconds
            urlc.connect();
            if (urlc.getResponseCode() == 200) {
                long endPing = System.currentTimeMillis();
                return endPing - startPing;
            } else {
                if (urlc.getResponseCode() == 403) {
                    return 403;
                } else {
                    return urlc.getResponseCode();
                }
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return 9999;
        } catch (Exception e) {
            e.printStackTrace();
            return 9999;
        }
    }


    private void setURLPicker() {
        if (urlPing.size() > 1) {
            SinglePicker<UrlPingInfo> picker2 = new SinglePicker<>(this, urlPing);
            picker2.setCanLoop(false);//不禁用循环
            picker2.setLineVisible(true);
            picker2.setShadowVisible(true);
            picker2.setTextSize(18);
            picker2.setSelectedIndex(1);
            picker2.setTitleText("请选择时间范围");
            picker2.setWheelModeEnable(true);
            picker2.setWeightEnable(true);
            picker2.setWeightWidth(1);
            picker2.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
            picker2.setUnSelectedTextColor(0xFF999999);
            picker2.setOnItemPickListener(new OnItemPickListener<UrlPingInfo>() {
                @Override
                public void onItemPicked(int index, UrlPingInfo item) {
                    ToastUtil.showLong(LoginActivity.this, "已更换为线路" + item.URLTitle);
                    tx_connection_problem.setText("线路" + item.URLTitle);
                    UserSP.getSPInstance().setFavorIp(item.URLString);
                }
            });
            picker2.show();
        } else {
            ToastUtil.showShort(this, "目前仅为单一线路，请稍后尝试");
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
//        AntiHijackingUtil.checkActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    public Drawable getDrawableById(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }
}
