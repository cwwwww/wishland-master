package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.VerftyPhoneBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
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
import okhttp3.ResponseBody;
import rx.Subscriber;

public class VerifyPhoneNumberActivity extends BaseStyleActivity {

    @BindView(R.id.login_new_phone)
    EditText login_new_phone;
    @BindView(R.id.bt_vertify_send)
    Button sendVertify;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();

        sendVertify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVertify();
            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_verify_phone_number, R.layout.base_toolbar_back);
        setTitle("手机验证");
        ButterKnife.bind(this);
    }

    private void sendVertify() {
        String phoneNum = login_new_phone.getText().toString();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if ("".equals(phoneNum)) {
            ToastUtil.showShort(VerifyPhoneNumberActivity.this, "手机号不能为空");
            return;
        }
        emptyLayout.setLoadingMessage("加载中...");
        emptyLayout.showLoading();

        if (map.size() != 0) {
            map.clear();
        }
        map.put("mobile", phoneNum);

        instance.apiVerftyPhone(phoneNum, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                ToastUtil.showUI(VerifyPhoneNumberActivity.this, "网络异常...");
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
                        VerftyPhoneBean verftyPhoneBean = new Gson().fromJson(string, VerftyPhoneBean.class);
                        boolean valid = verftyPhoneBean.getData().isValid();
                        if (valid) {
                            ToastUtil.showShort(VerifyPhoneNumberActivity.this, "验证成功");
                            startActivity(new Intent(VerifyPhoneNumberActivity.this, ChangePhoneNumActivity.class));
                        } else {
                            ToastUtil.showShort(VerifyPhoneNumberActivity.this, "验证失败");
                        }


                    } else {
                        ToastUtil.showShort(VerifyPhoneNumberActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
               //     ToastUtil.showUI(VerifyPhoneNumberActivity.this, "验证手机号失败...");
                } catch (JSONException e) {
                    e.printStackTrace();
                  //  ToastUtil.showUI(VerifyPhoneNumberActivity.this, "验证手机号失败...");
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
}
