package com.wishland.www.wanhaohui2.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;

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
 * Created by admin on 2017/10/10.
 */

public class ComplaintAdviceActivity extends BaseStyleActivity {

    @BindView(R.id.et_complaint_content)
    EditText et_complaint_content;
    @BindView(R.id.login_edit_username)
    EditText login_edit_username;
    @BindView(R.id.login_edit_passwork)
    EditText login_edit_passwork;
    @BindView(R.id.et_complaint_wecat)
    EditText et_complaint_wecat;
    @BindView(R.id.bt_submit_complaint)
    Button bt_submit_complaint;
    @BindView(R.id.ll_submit_complaint)
    LinearLayout ll_submit_complaint;
    @BindView(R.id.ll_submit_success)
    LinearLayout ll_submit_success;

    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("投诉和建议");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_complaint_advice, R.layout.base_toolbar_back);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.bt_submit_complaint, R.id.bt_submit_close})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit_complaint:
                submitComplaint();
                break;
            case R.id.bt_submit_close:
                finish();
                break;
        }
    }

    private void submitComplaint() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String content = et_complaint_content.getText().toString();
        String phone = login_edit_username.getText().toString();
        String qq = login_edit_passwork.getText().toString();
        String wecat = et_complaint_wecat.getText().toString();
        if ("".equals(content)) {
            ToastUtil.showShort(ComplaintAdviceActivity.this, "请填写反馈意见~");
            return;
        }

        if (map.size() != 0) {
            map.clear();
        }
        map.put("content", content);
        if (!"".equals(phone)) {
            map.put("tel", phone);
        }
        if (!"".equals(qq)) {
            map.put("qq", qq);
        }
        if (!"".equals(wecat)) {
            map.put("wx", wecat);
        }

        instance.apiFeed(content, phone, qq, wecat, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

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
                    if (status == 200) {
                        ll_submit_complaint.setVisibility(View.GONE);
                        ll_submit_success.setVisibility(View.VISIBLE);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(ComplaintAdviceActivity.this, LoginActivity.class,"ComplaintAdviceActivity");
                        } else {
                            ToastUtil.showShort(ComplaintAdviceActivity.this, errorMsg);
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
