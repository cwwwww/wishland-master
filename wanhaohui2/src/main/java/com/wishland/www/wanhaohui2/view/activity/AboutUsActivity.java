package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.AboutUsBean;
import com.wishland.www.wanhaohui2.bean.AppUpdateBean;
import com.wishland.www.wanhaohui2.bean.QuestionBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.UpdateUtils;
import com.wishland.www.wanhaohui2.view.adapter.AboutUsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.utils.UpdateUtils.FORCE_UPDATE;
import static com.wishland.www.wanhaohui2.utils.UpdateUtils.NON_UPDATE;
import static com.wishland.www.wanhaohui2.utils.UpdateUtils.NORMAL_UPDATE;

public class AboutUsActivity extends BaseStyleActivity {

    @BindView(R.id.rv_about_us)
    RecyclerView recyclerView;
    @BindView(R.id.iv_about_us)
    ImageView imgAboutUs;

    private Model instance;
    private UserSP userSP;
    private AboutUsAdapter aboutUsAdapter;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        aboutUsAdapter = new AboutUsAdapter(this);
        recyclerView.setAdapter(aboutUsAdapter);
        getAboutUsList();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_about_us, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        setTitle("关于我们");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
    }

    private void getAboutUsList() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAboutUs(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(AboutUsActivity.this, "获取消息列表请求异常！");
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
                        AboutUsBean aboutUsBean = new Gson().fromJson(lineMoneyData, AboutUsBean.class);
                        String img = aboutUsBean.getData().getImg();
                        List<AboutUsBean.AboutUsData.AboutContent> aboutList = aboutUsBean.getData().getAbout();
                        if (!"".equals(img)) {
                            Glide.with(AboutUsActivity.this).load(img).into(imgAboutUs);
                        }
                        if (aboutList.size() != 0) {
                            aboutUsAdapter.setData(aboutList);
                        }
                    } else if (status == 301 || status == 332) {
                        Intent intent = new Intent(AboutUsActivity.this, LoginActivity.class);
                        startActivity(intent);
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

}
