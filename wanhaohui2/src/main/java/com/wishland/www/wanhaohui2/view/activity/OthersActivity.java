package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.AboutUsBean2;
import com.wishland.www.wanhaohui2.bean.AppUpdateBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.UpdateUtils;
import com.wishland.www.wanhaohui2.utils.WebUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.utils.UpdateUtils.FORCE_UPDATE;
import static com.wishland.www.wanhaohui2.utils.UpdateUtils.NON_UPDATE;
import static com.wishland.www.wanhaohui2.utils.UpdateUtils.NORMAL_UPDATE;

/**
 * Created by JayCruz on 2018/1/5.
 */

public class OthersActivity extends BaseStyleActivity {

    String aboutUsUrl = null;
    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_other, R.layout.base_toolbar_back_btn);
        ButterKnife.bind(this);
        setTitle("关于我们");
        getAboutUsList();

    }

    @OnClick({R.id.ll_contact_us, R.id.ll_complain_advice, R.id.ll_clean_cache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_contact_us:
                //关于我们
                if (aboutUsUrl != null && !"".equals(aboutUsUrl)) {
                    WebUtil.toWebActivity(OthersActivity.this, aboutUsUrl, "品牌信誉");
                }else{
                    ToastUtil.showLong(OthersActivity.this,"数据异常");
                }
//                startActivity(new Intent(OthersActivity.this, AboutUsActivity.class));
                break;
            case R.id.ll_complain_advice:
                break;
            case R.id.ll_clean_cache:
                break;
        }


    }

    //关于我们
    private void getAboutUsList() {
        final Model model = Model.getInstance();
        String token = UserSP.getSPInstance().getToken(UserSP.LOGIN_TOKEN);
        model.apiAboutUs(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(OthersActivity.this, "获取消息列表请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    LogUtil.i("linearResponse", data);
                    JSONObject jsonObject = model.getJsonObject(data);
                    int status = jsonObject.optInt("status");
                    model.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        AboutUsBean2 aboutUsBean = new Gson().fromJson(data, AboutUsBean2.class);
                        aboutUsUrl = aboutUsBean.getData().getUrl();
                    } else if (status == 301 || status == 332) {
                        Intent intent = new Intent(OthersActivity.this, LoginActivity.class);
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

    /**
     * 服务器挂掉后还是去检查 版本更新机制，
     * 就算被打挂之后，还是可以救回
     * 防止 URL挂掉后，用户无法检查版本名导致无法 发包
     */
    private void checkUpdate() {
        final String UPDATE_Info = getResources().getString(R.string.UPDATE_Info);

        String UPDATE_URL = getResources().getString(R.string.UPDATE_URL);
        String code = getResources().getString(R.string.VersionCode);
        OkGo.post(UPDATE_URL)
                .params("code", code)
                .execute(new AbsCallback<String>() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        final AppUpdateBean appUpdate = new Gson().fromJson(s, AppUpdateBean.class);
                        int appVersion = UpdateUtils.getAPPRealVersion(OthersActivity.this);
                        int currentVersion = UpdateUtils.dealVersion(appUpdate.getEntity().getVersion());

                        MaterialDialog.Builder builder = null;
                        switch (UpdateUtils.checkUpdateType(currentVersion, appVersion, appUpdate.getEntity().getVersionType())) {
                            case FORCE_UPDATE:

                                builder = new MaterialDialog.Builder(OthersActivity.this);
                                builder.title("发现新版本")
                                        .cancelable(false)
                                        .autoDismiss(false)
                                        .content("重大版本更新，请下载安装新版本后继续使用\n"+UPDATE_Info)
                                        .positiveText("点击升级")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                try {
                                                    Uri uri = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next());
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                    startActivity(intent);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).show();
                                break;
                            case NORMAL_UPDATE:
                                builder = new MaterialDialog.Builder(OthersActivity.this);
                                builder.title("发现新版本")
                                        .negativeColor(getResources().getColor(R.color.text_hint))
                                        .content(UPDATE_Info)
                                        .negativeText("暂不升级")
                                        .positiveText("点击升级")
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                try {
                                                    Uri uri = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next());
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                    startActivity(intent);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).show();
                                break;
                            case NON_UPDATE:
                                ToastUtil.showLong(OthersActivity.this,"目前已为最新版本！");
                                break;
                        }
                    }

                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }
                });
    }
}
