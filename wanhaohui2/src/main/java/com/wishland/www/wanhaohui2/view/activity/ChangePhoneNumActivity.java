package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class ChangePhoneNumActivity extends BaseStyleActivity {
    Unbinder unbinder;

    @BindView(R.id.login_edit_username)
    TextView phoneNumber;
    @BindView(R.id.login_edit_passwork)
    EditText vertifyCode;
    @BindView(R.id.login_new_phone)
    EditText newPhoneNum;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.tv_tishi)
    TextView point;
    @BindView(R.id.bt_vertify_send)
    Button sendVertify;
    @BindView(R.id.tv_contact_kf)
    TextView tv_contact_kf;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;


    private int recLen = 60;
    private Timer timer;
    private TimerTask timerTask;
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private String phoneNum = "";


    @Override
    protected void initVariable() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
    }

    @Override
    protected void initDate() {
        if ("".equals(userSP.getMolibe())) {
            phoneNumber.setText("没有绑定手机");
        } else {
            phoneNumber.setText(userSP.getMolibe());
        }

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_phone_num, R.layout.base_toolbar_back);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_code, R.id.bt_vertify_send, R.id.tv_contact_kf})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                sendQrCode();
                break;
            case R.id.bt_vertify_send:
                phoneNum = newPhoneNum.getText().toString().trim();
                String vertifyNum = vertifyCode.getText().toString().trim();
                if (JudgeUserUtil.judgeQphone(phoneNum, 11)) {
                    if (JudgeUserUtil.judgeQphone(vertifyNum, 4)) {
                        changePhoneNum();
                    } else {
                        ToastUtil.showShort(this, "验证码格式错误,请重新输入.");
                    }
                } else {
                    ToastUtil.showShort(this, "新手机号格式错误,请重新输入.");
                }
                break;
            case R.id.tv_contact_kf:
                emptyLayout.setLoadingMessage("正在连接客服...");
                emptyLayout.showLoading();
                Intent intent = new Intent(this, DetailsHtmlPageActivity.class);
                intent.setAction(BaseApi.NEWHTML);
                intent.putExtra(BaseApi.HTML5DATA, BaseApi.CustomHtml5);
                startActivity(intent);
                break;
        }
    }

    private void changePhoneNum() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String mobile = newPhoneNum.getText().toString().trim();
        String vertify = vertifyCode.getText().toString().trim();
        if ("".equals(mobile)) {
            if ("".equals(vertify)) {
                ToastUtil.showShort(this, "验证码格式不正确");
                return;
            }
            ToastUtil.showShort(this, "手机号不能为空");
            return;
        }
        emptyLayout.setLoadingMessage("加载中...");
        emptyLayout.showLoading();

        if (map.size() != 0) {
            map.clear();
        }
        map.put("mobile", mobile);
        map.put("code", vertify);

        instance.apiChangePhone(mobile, vertify, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("快速登录请求完成");
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("快速登录请求失败" + e.toString());
                ToastUtil.showUI(ChangePhoneNumActivity.this, "网络异常...");
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
                        cleanView();
                        ToastUtil.showShort(ChangePhoneNumActivity.this, "修改成功");
                    } else {
                        ToastUtil.showShort(ChangePhoneNumActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                 //   ToastUtil.showUI(ChangePhoneNumActivity.this, "修改手机号失败...");
                } catch (JSONException e) {
                    e.printStackTrace();
                  //  ToastUtil.showUI(ChangePhoneNumActivity.this, "修改手机号失败...");
                }
            }
        });
    }

    private void cleanView() {
        phoneNumber.setText(phoneNum);
        vertifyCode.setText("");
        newPhoneNum.setText("");
    }

    private void sendQrCode() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String mobile = newPhoneNum.getText().toString().trim();
        if ("".equals(mobile)) {
            ToastUtil.showShort(this, "新手机号不能为空");
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
                ToastUtil.showUI(ChangePhoneNumActivity.this, "网络异常...");
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
                        ToastUtil.showShort(ChangePhoneNumActivity.this, "发送成功");
                        setTimer();
                    } else {
                        ToastUtil.showShort(ChangePhoneNumActivity.this, jsonObject.optString("errorMsg"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                   // ToastUtil.showUI(ChangePhoneNumActivity.this, "发送失败...");
                } catch (JSONException e) {
                    e.printStackTrace();
                 //   ToastUtil.showUI(ChangePhoneNumActivity.this, "发送失败...");
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
                        tv_code.setBackground(getResources().getDrawable(R.drawable.bg_verify_shape));
                        tv_code.setTextColor(getResources().getColor(R.color.blue));
                    }
            }
        }
    };

    private void setTimer() {
        timer = new Timer();
        tv_code.setBackground(getResources().getDrawable(R.drawable.bg_verify_shape_gray));
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

    @Override
    public void onResume() {
        super.onResume();
        emptyLayout.hide();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
