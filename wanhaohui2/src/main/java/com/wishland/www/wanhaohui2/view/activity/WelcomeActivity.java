package com.wishland.www.wanhaohui2.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.api.BaseRetrofit;
import com.wishland.www.wanhaohui2.api.HttpApiInstance;
import com.wishland.www.wanhaohui2.base.BaseActivity;
import com.wishland.www.wanhaohui2.bean.AppUpdateBean;
import com.wishland.www.wanhaohui2.bean.KeyStoreBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FileUtil;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.RequestHttpDnsIp;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.UpdateUtils;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.utils.UpdateUtils.FORCE_UPDATE;
import static com.wishland.www.wanhaohui2.utils.UpdateUtils.NON_UPDATE;
import static com.wishland.www.wanhaohui2.utils.UpdateUtils.NORMAL_UPDATE;

/**
 * Created by admin on 2017/10/13.
 */

@RuntimePermissions
public class WelcomeActivity extends BaseActivity {
    private String baseUrl = "";
    private boolean isLoadHttp = false;
    private Model model;
    private UserSP userSP;
    private String startUpImgTime;

    private int retryTimes = 0;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isLoadHttp) {
                return;
            }
            switch (msg.what) {
                case 1: {
                    String ip = (String) msg.obj;
                    if (ip != null) {
                        isLoadHttp = true;
                        initHttp(ip);
                    } else {
                        isLoadHttp = true;
                        initHttp(baseUrl);
                    }
                }
                break;
            }
        }
    };

    private void initHttp(String ip) {
        final String baseUrl = "https://" + ip + "/api/";
//        final String baseUrl = "http://47.74.6.30/api2/";

        OkGo.get(baseUrl + "index.php?vcode/key")
                .execute(new AbsCallback<String>() {
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        KeyStoreBean keyStoreBean = new Gson().fromJson(s, KeyStoreBean.class);
                        if (keyStoreBean != null) {
                            if (keyStoreBean.getStatus() == 200) {
                                userSP.setKeyStore(UserSP.KEYSTORE, keyStoreBean.getData().getKey());
//                              BaseApi.KEYSTORE = keyStoreBean.getData().getKey();
                                Model.mObservable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(baseUrl + "/"));
                                getStartUpImg();
//                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            } else {
                                ToastUtil.showShort(WelcomeActivity.this, "应用初始化失败，请稍后重试！");
                                checkUpdate();
                            }
                        } else {
                            Model.mObservable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(baseUrl + "/"));
                            getStartUpImg();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtil.showShort(WelcomeActivity.this, "初始化应用失败，请稍后重试！");
                        Log.e("cww", e.getMessage());
                        checkUpdate();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否有网络
        if (!JudgeNewWorkUtil.isNetworkAvalible(this)) {
            setContentView(R.layout.view_nonetwork);
            ToastUtil.showUI(this, "网络异常,请检查设置！");
            return;
        }
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        WelcomeActivityPermissionsDispatcher.doCallWithCheck(this);
    }


    private void init() {
        baseUrl = getString(R.string.AliUrl);
        RequestHttpDnsIp.initHttpDns(mHandler, baseUrl);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doCall() {
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void showDialog(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void refuse() {
        init();
    }

    @Override
    protected void initVariable() {
        model = Model.getInstance();
        userSP = model.getUserSP();
        startUpImgTime = userSP.getStartUpImgTime(UserSP.START_UP_IMG_TIME);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {

    }

    private void getStartUpImg() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        File file = new File(BaseApi.IMAGE_URL + "ad.jpg");
        if (file.exists()) {
            Intent intent = new Intent(WelcomeActivity.this, StartUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade, R.anim.hold);
        } else {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade, R.anim.hold);
        }
        model.apiStartUp(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("cww", "获取启动页图片报错：" + e.getMessage());
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String s = responseBody.string();
                    final String url = model.getJsonObject(s).optJSONObject("data").optString("img");
                    final String time = model.getJsonObject(s).optJSONObject("data").optString("time");
                    if (!startUpImgTime.equals(time)) {
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        FileUtil.deleteFile(BaseApi.IMAGE_URL + "ad.jpg");
                                        userSP.setStartUpImgTime(UserSP.START_UP_IMG_TIME, time);
                                        Bitmap bitmap = BitmapFactory.decodeStream(FileUtil.getImageStream(url));
                                        FileUtil.saveFile(bitmap, "ad.jpg");
//                                        finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
//                        finish();
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
                        int appVersion = UpdateUtils.getAPPRealVersion(WelcomeActivity.this);
                        int currentVersion = UpdateUtils.dealVersion(appUpdate.getEntity().getVersion());

                        MaterialDialog.Builder builder = null;
                        switch (UpdateUtils.checkUpdateType(currentVersion, appVersion, appUpdate.getEntity().getVersionType())) {
                            case FORCE_UPDATE:

                                builder = new MaterialDialog.Builder(WelcomeActivity.this);
                                builder.title("发现新版本")
                                        .cancelable(false)
                                        .autoDismiss(false)
                                        .content("重大版本更新，请下载安装新版本后继续使用\n" + UPDATE_Info)
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
                                builder = new MaterialDialog.Builder(WelcomeActivity.this);
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