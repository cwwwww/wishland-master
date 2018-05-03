package com.wishland.www.wanhaohui2.view.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeUserUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
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

/**
 * Created by admin on 2017/11/10.
 */

public class FindPassWordActivity extends BaseStyleActivity {
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.cb_new_eye)
    CheckBox cbNewEye;
    @BindView(R.id.et_sure_password)
    EditText etSurePassword;
    @BindView(R.id.cb_new_psw)
    CheckBox cbNewPsw;
    @BindView(R.id.bt_login)
    Button btLogin;

    private Model model;
    private UserSP userSP;
    private String code;


    @Override
    protected void initVariable() {
        model = Model.getInstance();
        userSP = model.getUserSP();
        code = getIntent().getStringExtra("code");

    }

    @Override
    protected void initDate() {
        setTitle("设置新密码");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_pass_word, R.layout.base_toolbar_back);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.cb_new_eye, R.id.cb_new_psw, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_new_eye: {
                if (cbNewEye.isChecked()) {
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
            break;
            case R.id.cb_new_psw: {
                if (cbNewPsw.isChecked()) {
                    etSurePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etSurePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
            break;
            case R.id.bt_login: {
                String newPSW = etNewPassword.getText().toString().trim();
                final String surePSW = etSurePassword.getText().toString().trim();
                if (JudgeUserUtil.judgePassword(newPSW) && JudgeUserUtil.judgePassword(surePSW)) {
                    if (newPSW.equals(surePSW)) {
                        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
                        Map<String, String> map = new HashMap<>();
                        map.put("code", code);
                        map.put("newPW", newPSW);
                        model.apiAmendLogin("", newPSW, code, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtil.e("cww", e.getMessage());
                                ToastUtil.showShort(FindPassWordActivity.this, "设置新密码失败！");
                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    JSONObject jsonObject = model.getJsonObject(responseBody.string());
                                    model.setToken_SP(jsonObject.optString("token"));
                                    int status = jsonObject.optInt("status");
                                    if (status == 200) {
                                        userSP.setPassWord(UserSP.LOGIN_PASSWORD, surePSW);
                                        ToastUtil.showShort(FindPassWordActivity.this, "设置新密码成功！");
                                        setResult(1);
                                        finish();
                                    } else {
                                        String errorMsg = jsonObject.optString("errorMsg");
                                        LogUtil.e("cww", errorMsg);
                                        ToastUtil.showShort(FindPassWordActivity.this, errorMsg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        ToastUtil.showShort(FindPassWordActivity.this, "两次密码不一致，请重新设置！");
                    }
                } else {
                    ToastUtil.showShort(FindPassWordActivity.this, "密码格式不正确，请设置6-20位数字和字母组合的密码！");
                }
            }
            break;
        }
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
