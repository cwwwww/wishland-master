package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.model.AccountDataSP;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeUserUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_CHECKED;
import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_PASSWORD;

/**
 * Created by admin on 2018/1/9.
 */

public class SetLoginPWActivity extends BaseStyleActivity {
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_hit)
    TextView tvHit;
    @BindView(R.id.cb_eye)
    CheckBox cb;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private Model model;
    private UserSP userSP;
    private String mobile;
    private String passWord;

    @Override
    protected void initVariable() {
        model = Model.getInstance();
        userSP = model.getUserSP();
        mobile = getIntent().getStringExtra("mobile");
    }

    @Override
    protected void initDate() {
        setTitle("设置登录密码");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_login_pw, R.layout.base_toolbar);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cb_eye, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_eye: {
                if (cb.isChecked()) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPassword.setSelection(etPassword.getText().toString().trim().length());
            }
            break;
            case R.id.bt_register: {
                passWord = etPassword.getText().toString().trim();
                Map<String, String> map = new HashMap<>();
                map.put("mobile", mobile);
                map.put("rpassword", passWord);
                map.put("device", "android");
                if (JudgeUserUtil.judgePassword(passWord)) {
                    emptyLayout.showLoading();
                    String token = userSP.getToken(UserSP.LOGIN_TOKEN);
                    model.apiFastReg(mobile, passWord, "android", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            emptyLayout.hide();
                            ToastUtil.showShort(SetLoginPWActivity.this, "注册失败");
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            emptyLayout.hide();
                            try {
                                String s = responseBody.string();
                                int status = model.getJsonObject(s).optInt("status");
                                model.setToken_SP(model.getJsonObject(s).optString("token"));
                                if (status == 200) {
                                    tvHit.setVisibility(View.GONE);
                                    ToastUtil.showShort(SetLoginPWActivity.this, "注册成功");
                                    deleteUserSP();
                                    EventBus.getDefault().post(new SuccessBean("success", "register"));
                                    startActivity(new Intent(SetLoginPWActivity.this, MainActivity.class));
                                } else {
                                    String errorMsg = model.getJsonObject(s).optString("errorMsg");
                                    ToastUtil.showShort(SetLoginPWActivity.this, errorMsg);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } else {
                    tvHit.setVisibility(View.VISIBLE);
                    ToastUtil.showShort(this, "您输入的密码格式不符合要求");
                }
            }
            break;
        }
    }

    public void deleteUserSP() {
        userSP.setCheckedSP(LOGIN_CHECKED, false);
        userSP.setPassWord(LOGIN_PASSWORD, passWord);
        userSP.setSuccess(UserSP.LOGIN_SUCCESS, 1);
        userSP.setUserName(UserSP.LOGIN_USERNAME, mobile);
        model.getAccountDataSP().setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, mobile);
    }
}
