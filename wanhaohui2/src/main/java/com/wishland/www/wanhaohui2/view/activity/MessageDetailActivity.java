package com.wishland.www.wanhaohui2.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.MessageBean;
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

public class MessageDetailActivity extends BaseStyleActivity {

    @BindView(R.id.tv_message_title)
    TextView tv_message_title;
    @BindView(R.id.tv_message_time)
    TextView tv_message_time;
    @BindView(R.id.tv_message_info)
    TextView tv_message_info;

    private Model instance;
    private UserSP userSP;
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void initVariable() {}

    @Override
    protected void initDate() {
        setTitle("我的消息");
        Bundle message = getIntent().getExtras();
        MessageBean.DataBean.DataListBean messageBean = (MessageBean.DataBean.DataListBean) message.getSerializable("message");
        tv_message_title.setText(messageBean.getTitle());
        tv_message_time.setText(messageBean.getTime());
        tv_message_info.setText(messageBean.getDetailedInfo());

        if (!"0".equals(messageBean.getIsNew() + "")) {
            //消息为未读时，请求已读接口
            readMessage(messageBean.getMsgId() + "");
        }
    }

    private void readMessage(String msgId) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("Msgid", msgId);

        instance.apiReadMessage(msgId, token, NetUtil.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("cww", e.getMessage());
                        ToastUtil.showShort(MessageDetailActivity.this, "读取消息请求异常！");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String lineMoneyData = responseBody.string();
                            LogUtil.i("linearResponse", lineMoneyData);
                            JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            if (status == 200) {

                            } else {
                                ToastUtil.showShort(MessageDetailActivity.this, "读取消息请求异常！");
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
    protected void initView() {
        setContentView(R.layout.activity_message_detail, R.layout.base_toolbar_back_btn);
        ButterKnife.bind(this);

        instance = Model.getInstance();
        userSP = instance.getUserSP();
    }

    @OnClick({R.id.bt_message_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.bt_message_back:
                finish();
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
