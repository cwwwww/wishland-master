package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_CHECKED;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_PASSWORD;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_TYPE;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_USERNAME;
import static com.wishland.www.wanhaohui2.model.UserSP.Login_OUT;

public class ChangePswActivity extends BaseStyleActivity {

    @BindView(R.id.et_old_password)
    EditText et_old_password;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.et_sure_password)
    EditText et_sure_password;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.rl_login_psw)
    RelativeLayout rl_login_psw;
    @BindView(R.id.rl_pay_psw)
    RelativeLayout rl_pay_psw;
    @BindView(R.id.tv_login_psw)
    TextView tv_login_psw;
    @BindView(R.id.tv_pay_psw)
    TextView tv_pay_psw;
    @BindView(R.id.empty_layout)
    EmptyLayout empty_layout;
    @BindView(R.id.cb_old_eye)
    CheckBox cb_old_eye;
    @BindView(R.id.cb_new_eye)
    CheckBox cb_new_eye;
    @BindView(R.id.cb_new_psw)
    CheckBox cb_new_psw;

    private Model instance;
    private UserSP userSP;
    private int type = 0;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("更改密码");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_psw, R.layout.base_toolbar_back);
        ButterKnife.bind(this);

        initPassWordView();

    }

    private void initPassWordView() {
        if (type == 0) {
            et_new_password.setHint("请输入6-20位字母+数字组合");
            et_sure_password.setHint("请输入6-20位字母+数字组合");
        } else {
            et_new_password.setHint("请输入6位纯数字");
            et_sure_password.setHint("请输入6位纯数字");
        }
    }

    @OnClick({R.id.bt_login, R.id.rl_login_psw, R.id.rl_pay_psw, R.id.cb_old_eye, R.id.cb_new_eye, R.id.cb_new_psw})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                empty_layout.showLoading();
                if (type == 0) {
                    changeLoginPsw();
                } else {
                    changePayPsw();
                }
                break;
            case R.id.rl_login_psw:
                type = 0;
                initPassWordView();
                changeView();
                break;
            case R.id.rl_pay_psw:
                type = 1;
                initPassWordView();
                changeView();
                break;
            case R.id.cb_old_eye:
                if (cb_old_eye.isChecked()) {
                    et_old_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.cb_new_eye:
                if (cb_new_eye.isChecked()) {
                    et_new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.cb_new_psw:
                if (cb_new_psw.isChecked()) {
                    et_sure_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_sure_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }

    private void changeView() {
        if (type == 0) {
            tv_login_psw.setTextColor(getResources().getColor(R.color.blue));
            tv_pay_psw.setTextColor(getResources().getColor(R.color.text_color1));
            rl_login_psw.setBackgroundColor(getResources().getColor(R.color.white));
            rl_pay_psw.setBackgroundColor(getResources().getColor(R.color.background_color));
        } else {
            tv_pay_psw.setTextColor(getResources().getColor(R.color.blue));
            tv_login_psw.setTextColor(getResources().getColor(R.color.text_color1));
            rl_pay_psw.setBackgroundColor(getResources().getColor(R.color.white));
            rl_login_psw.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        et_old_password.setText("");
        et_new_password.setText("");
        et_sure_password.setText("");
        et_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cb_old_eye.setChecked(false);
        cb_new_eye.setChecked(false);
        cb_new_psw.setChecked(false);
    }


    private void changePayPsw() {
        Map<String, String> map = new HashMap<>();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String oldLoginPsw = et_old_password.getText().toString();
        final String newLoginPsw = et_new_password.getText().toString();
        String surePsw = et_sure_password.getText().toString();
        if ("".equals(oldLoginPsw)) {
            ToastUtil.showShort(this, "原密码不能为空");
            empty_layout.hide();
            return;
        }
        if (oldLoginPsw.equals(newLoginPsw)) {
            ToastUtil.showShort(this, "新旧密码不能一致");
            empty_layout.hide();
            return;
        }
        if (!surePsw.equals(newLoginPsw)) {
            ToastUtil.showShort(this, "确认密码与新密码不一致");
            empty_layout.hide();
            return;
        }

        //密码格式限制(6位数的数字)
        String regEx = "^\\d{6}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(newLoginPsw);
        if (!matcher.matches()) {
            ToastUtil.showShort(this, "取款密码修改失败，密码必须由6位数字组成");
            empty_layout.hide();
            return;
        }

        map.put("oriPW", oldLoginPsw);
        map.put("newPW", newLoginPsw);

        instance.apiAmend(oldLoginPsw, newLoginPsw, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
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
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        userSP.setPassWord(UserSP.LOGIN_PASSWORD, newLoginPsw);
                        et_old_password.setText("");
                        et_new_password.setText("");
                        et_sure_password.setText("");

                        ToastUtil.showShort(ChangePswActivity.this, "修改取款密码成功");
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(ChangePswActivity.this, LoginActivity.class, "ChangePswActivity");
                        } else {
                            ToastUtil.showShort(ChangePswActivity.this, errorMsg);
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

    private void changeLoginPsw() {
        Map<String, String> map = new HashMap<>();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String oldLoginPsw = et_old_password.getText().toString();
        String newLoginPsw = et_new_password.getText().toString();
        String surePsw = et_sure_password.getText().toString();
        if ("".equals(oldLoginPsw)) {
            ToastUtil.showShort(this, "原密码不能为空");
            empty_layout.hide();
            return;
        }
        if (oldLoginPsw.equals(newLoginPsw)) {
            ToastUtil.showShort(this, "新旧密码不能一致");
            empty_layout.hide();
            return;
        }
        if (!surePsw.equals(newLoginPsw)) {
            ToastUtil.showShort(this, "确认密码与新密码不一致");
            empty_layout.hide();
            return;
        }
        //密码格式限制(6-20位字母或者数字的组合)
//        String regEx = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/";
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(newLoginPsw);
        if (!matcher.matches()) {
            ToastUtil.showShort(this, "登录密码修改失败，密码必须由字母+数字混合组成");
            empty_layout.hide();
            return;
        }

        map.put("oriPW", oldLoginPsw);
        map.put("newPW", newLoginPsw);

        instance.apiAmendLogin(oldLoginPsw, newLoginPsw, "", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
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
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        et_old_password.setText("");
                        et_new_password.setText("");
                        et_sure_password.setText("");


                        loginOut();
                        ToastUtil.showShort(ChangePswActivity.this, "修改登录密码成功");
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(ChangePswActivity.this, LoginActivity.class, "ChangePswActivity");
                        } else {
                            ToastUtil.showShort(ChangePswActivity.this, errorMsg);
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

    private void loginOut() {
        instance.setToken_SP("");
        userSP.setCheckedSP(LOGIN_CHECKED, false);
        userSP.setPassWord(LOGIN_PASSWORD, "");
        userSP.setLoginOut(Login_OUT, true);
        userSP.setSuccess(UserSP.LOGIN_SUCCESS, -1);
        finish();
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
}
