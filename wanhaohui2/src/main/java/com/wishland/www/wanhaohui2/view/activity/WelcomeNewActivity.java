package com.wishland.www.wanhaohui2.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.api.BaseRetrofit;
import com.wishland.www.wanhaohui2.api.HttpApiInstance;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.base.BaseActivity;
import com.wishland.www.wanhaohui2.base.MyApplication;
import com.wishland.www.wanhaohui2.bean.AppUpdateBean;
import com.wishland.www.wanhaohui2.bean.KeyStoreBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FileUtil;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.RequestHttpDnsIp;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.UpdateUtils;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
public class WelcomeNewActivity extends BaseActivity {
    private Model model;
    private UserSP userSP;
    private String startUpImgTime;
    private boolean appStop = false;


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
        WelcomeNewActivityPermissionsDispatcher.doCallWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeNewActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /***
     * 权限请求判断
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.GET_TASKS})
    void doCall() {
        init();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.GET_TASKS})
    void refuse() {
        init();
    }

    private static final String HTTPS_SCHEMA = "https://";
    ArrayList<String> urlPools = new ArrayList<String>();
    ScheduledExecutorService pools = null;
    private int failedTimes = 0;


    /***
     * 从 域名中 取得 域明池后 用多执行续的方式 取得最快解析的IP
     * 并开始执行程序
     * @param urllist
     */
    private void startDNSProgress(String[] urllist) {
        String DNS_ACCOUNT_ID = getResources().getString(R.string.DNS_ACCOUNT_ID);
        pools = Executors.newSingleThreadScheduledExecutor();
        try {

            HttpDnsService httpdns = HttpDns.getService(MyApplication.baseContext, DNS_ACCOUNT_ID);
            // 设置预解析域名列表
            httpdns.setPreResolveHosts(new ArrayList<>(Arrays.asList(urllist)));
            // 允许过期IP以实现懒加载策略
            httpdns.setExpiredIPEnabled(true);
            // 网路变更时 重新请求变更IP
            httpdns.setPreResolveAfterNetworkChanged(true);

            urlPools.clear();
            for (int NTread = 0; NTread < urllist.length; NTread++) {
                final String CurrentUrl = urllist[NTread];
                getIpFromDnsByLoop(httpdns, CurrentUrl);
            }
            saveTreadPools();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getIpFromDnsByLoop(final HttpDnsService httpdns, final String CurrentUrl) {
        failedTimes = 0;
        final Runnable rn = new Runnable() {
            @Override
            public void run() {
                try {
                    String originalUrl = HTTPS_SCHEMA + CurrentUrl;
                    URL url = new URL(originalUrl);
                    String ip = httpdns.getIpByHostAsync(url.getHost());
                    String newUrl = "http://" + ip + "/";

                    if (ip != null) {
                        Log.e("HTTP_DNS成功取得", "域名：" + originalUrl + " " + newUrl);
                        startAppWithIP(originalUrl, newUrl);
                    } else {
                        if (failedTimes >= 5) {
                            Log.e("HTTP_DNS未取得", "尝试 5 次失败，线层池关闭");
                            pools.shutdown();
                        } else {
                            Log.e("HTTP_DNS重试第 [" + failedTimes + "] 次", originalUrl);
                            pools.schedule(this, 2, TimeUnit.SECONDS);
                        }
                        failedTimes++;
                    }
                } catch (Exception ex) {
                    Log.e("HTTP_DNS", "normal request failed.", ex);
                    pools.shutdown();
                }
            }
        };
        pools.schedule(rn, 0, TimeUnit.SECONDS);
    }


    /***
     * 存放暂存池
     */
    private void saveTreadPools() {
        pools.schedule(new Runnable() {
            @Override
            public void run() {
                /**
                 * 将取到的DNS 存入用户资料
                 */
                if (urlPools.size() >= 1) {
                    UserSP.getSPInstance().setFavorIp(urlPools.get(0));
                    UserSP.getSPInstance().setIpPools(urlPools);
//                    finish();
                    Log.e("HTTP_DNS线程池", "存放 线程池！");
                } else {
                    Log.e("HTTP_DNS线程池", "线程池 为空！");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showLong(WelcomeNewActivity.this, "域名取得失败，请稍后重试！");
                            checkUpdate();
                        }
                    });
                }
                Log.e("HTTP_DNS线程池", "关闭暂存池！");
                appStop = true;
                pools.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
    }


    /***
     * 检查是否有预设IP
     * 如果有 则 PING IP是否可用
     * 如果无 则 透过DNS 来取得最快IP
     */
    private void init() {
        String AliUrlsPool = getString(R.string.AliUrlsPool);
        startDNSProgress(AliUrlsPool.split(","));
    }

    private long pingUrl(String urlStr) {
        long startPing = System.currentTimeMillis();
        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setConnectTimeout(1000 * 3); // mTimeout is in seconds
            urlc.connect();
            if (urlc.getResponseCode() == 200) {
                long endPing = System.currentTimeMillis();
                return endPing - startPing;
            } else {
                if (urlc.getResponseCode() == 403) {
                    return 9999;
                } else {
                    return 9999;
                }
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return 9999;
        } catch (Exception e) {
            e.printStackTrace();
            return 9999;
        }
    }


    /****
     * 利用取得的IP 开始APP执行
     * @param ip
     */
    private void startAppWithIP(final String originalUrl, final String ip) {
        Log.e("进到startAppWithIP", "ip=>" + ip);
        final String baseUrl = ip + "api/";
        OkGo.get(baseUrl + "index.php?vcode/key")
                .execute(new AbsCallback<String>() {
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        KeyStoreBean keyStoreBean = new Gson().fromJson(s, KeyStoreBean.class);
                        if (keyStoreBean.getStatus() == 200) {

                            String key = keyStoreBean.getData().getKey();
                            userSP.setKeyStore(UserSP.KEYSTORE, key);
                            Log.e("startAppWithIP", "域名-->" + originalUrl + "_有效IP--> " + ip + "_KEYSTORE--> " + key);
                            urlPools.add(ip);
                            Log.e("cww", "多次用域名开启活动！");
                            if (urlPools.size() == 1 && !appStop) {
                                Log.e("startAppWithIP", "开始使用IP==>" + originalUrl + "开始APP=>" + ip);
                                Model.mObservable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(baseUrl + "/"));
                                getStartUpImg();
                            }
                        } else {
                            Log.e("startAppWithIP", "错误CODE==>" + keyStoreBean.getStatus() + "开始APP" + originalUrl);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("startAppWithIP", "URL错误==>" + originalUrl);
                    }
                });
    }

    private void getStartUpImg() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        File file = new File(BaseApi.IMAGE_URL + "ad.jpg");
        if (file.exists()) {
            Intent intent = new Intent(WelcomeNewActivity.this, StartUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade, R.anim.hold);
        } else {
            Intent intent = new Intent(WelcomeNewActivity.this, MainActivity.class);
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
//                startActivity(new Intent(WelcomeNewActivity.this, MainActivity.class));
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
                        int appVersion = UpdateUtils.getAPPRealVersion(WelcomeNewActivity.this);
                        int currentVersion = UpdateUtils.dealVersion(appUpdate.getEntity().getVersion());

                        MaterialDialog.Builder builder = null;
                        switch (UpdateUtils.checkUpdateType(currentVersion, appVersion, appUpdate.getEntity().getVersionType())) {
                            case FORCE_UPDATE:

                                builder = new MaterialDialog.Builder(WelcomeNewActivity.this);
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
                                                    onBackPressed();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).show();
                                break;
                            case NORMAL_UPDATE:
                                builder = new MaterialDialog.Builder(WelcomeNewActivity.this);
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
                                                    onBackPressed();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).show();
                                break;
                            case NON_UPDATE:
                                onBackPressed();
                                break;
                        }
                    }

                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getActivityManager().deleteAllActivity();
        System.exit(0);
    }
}