package com.wishland.www.wanhaohui2.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeUserUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2018/1/10.
 */

public class ChangeUserNameActivity extends BaseStyleActivity {
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private Model model;
    private UserSP userSP;

    @Override
    protected void initVariable() {
        model = Model.getInstance();
        userSP = model.getUserSP();
    }

    @Override
    protected void initDate() {
        setTitle("编辑用户名");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_change_user_name, R.layout.base_toolbar_back);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.bt_register)
    public void onViewClicked() {
        String userName = etUserName.getText().toString().trim();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        if (JudgeUserUtil.judgeUsername(userName)) {
            emptyLayout.showLoading();
            model.apiSetLoginName(userName, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    emptyLayout.hide();
                    Log.e("cww", e.getMessage());
                    ToastUtil.showShort(ChangeUserNameActivity.this, "编辑用户名失败！");
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        emptyLayout.hide();
                        String s = responseBody.string();
                        int status = model.getJsonObject(s).optInt("status");
                        if (status == 200) {
                            ToastUtil.showShort(ChangeUserNameActivity.this, "编辑用户名成功！");
                            finish();
                        } else {
                            String errorMsg = model.getJsonObject(s).optString("errorMsg");
                            ToastUtil.showShort(ChangeUserNameActivity.this, errorMsg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            ToastUtil.showShort(ChangeUserNameActivity.this, "请输入正确格式的用户名！");
        }

    }
}
