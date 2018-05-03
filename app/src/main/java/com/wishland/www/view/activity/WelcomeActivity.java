package com.wishland.www.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.R;
import com.wishland.www.api.ApiAddress;
import com.wishland.www.api.BastApi;
import com.wishland.www.api.HttpApiInstance;
import com.wishland.www.controller.base.BastRetrofit;
import com.wishland.www.model.Model;
import com.wishland.www.model.bean.KeyStoreBean;
import com.wishland.www.utils.ActivityManager;
import com.wishland.www.utils.AppUtils;
import com.wishland.www.utils.LogUtil;
import com.wishland.www.utils.MD5Utils;
import com.wishland.www.utils.NetUtils;
import com.wishland.www.utils.RequestDomainUtil;
import com.wishland.www.utils.RequestDomainUtilNew;
import com.wishland.www.utils.RequestHttpDnsIp;
import com.wishland.www.utils.ToastUtil;
import com.wishland.www.utils.UtilTools;
import com.wishland.www.view.Myapplication;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Administrator on 2017/7/30.
 */

@RuntimePermissions
public class WelcomeActivity extends AutoLayoutActivity {

    public static ApiAddress observable;
    private boolean isLoadHttp = false;
    private String domainCode;

    @SuppressLint("HandlerLeak")
    private Handler httpDnsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isLoadHttp) {
                return;
            }
            String ip = (String) msg.obj;
            if (ip != null) {
                isLoadHttp = true;
                initIp(ip);
            } else {
                isLoadHttp = true;
//                RequestDomainUtil.initDomain(WelcomeActivity.this, domainCode);
                String url = getResources().getString(R.string.AliUrl);
                initIp(url);
//                test();
            }
            // Log.e("cww", ip);
        }
    };

    private void initIp(final String ip) {
        final String baseUrl = "https://" + ip + "/api/";
//        final String baseUrl = "https://www.vns95777.com/api/";
//        final String baseUrl = "https://103.91.56.121/api/";
        OkGo.<String>get(baseUrl + "index.php?vcode/key").execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                KeyStoreBean keyStoreBean = new Gson().fromJson(s, KeyStoreBean.class);
                if (keyStoreBean.getStatus() == 200) {
                    BastApi.KEYSTORE = keyStoreBean.getData().getKey();
                    BastRetrofit.ip = ip;
                    observable = BastRetrofit.getInstance().getObservable(new HttpApiInstance(baseUrl + "/"));
                    AppUtils.getInstance().setHost(baseUrl);
                    Intent intent;
                    intent = new Intent(WelcomeActivity.this, MainActivity2.class);
                    startActivity(intent);
                } else {
//                    exitApp();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("cww", e.getMessage());
//                exitApp();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否有网络
        if (!UtilTools.isNetworkAvalible(this)) {
            setContentView(R.layout.view_nonetwork);
            ToastUtil.showUI(this, "网络异常,请检查设置！");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        WelcomeActivityPermissionsDispatcher.doCallWithCheck(this);
        ActivityManager.getActivityManager().addAcitivity(this);
    }

    private void init() {
        //通用
//        domainCode = getResources().getString(R.string.DomainCode);
//        String aliUrl = getResources().getString(R.string.AliUrl);
//        RequestHttpDnsIp.initHttpDns(httpDnsHandler, aliUrl);

        //新威尼斯
//        domainCode = getResources().getString(R.string.DomainCode);
//        RequestDomainUtilNew.initDomain(WelcomeActivity.this, domainCode);

//        //新银河
        test();
        String aliUrl = getResources().getString(R.string.AliUrl);
        RequestHttpDnsIp.initHttpDns(httpDnsHandler, aliUrl);
    }

    private void test() {
//        String aliUrl = getResources().getString(R.string.AliUrl);

        final String aliUrl = "https://yh33350.com/api/";

        OkGo.<String>get(aliUrl + "index.php?vcode/key").execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                KeyStoreBean keyStoreBean = new Gson().fromJson(s, KeyStoreBean.class);
                if (keyStoreBean.getStatus() == 200) {
                    BastApi.KEYSTORE = keyStoreBean.getData().getKey();
                    test1(aliUrl);
                } else {
//                    exitApp();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showLong(WelcomeActivity.this, "初始化失败！");
//                exitApp();
            }
        });


    }

    private void test1(final String aliUrl) {
        OkHttpClient okhttp = BastRetrofit.getOkhttp();
        HashMap<String, String> 小杨SB = new HashMap();
        小杨SB.put("code", "666777");
        Map<String, String> paramsPro = NetUtils.getParamsPro(小杨SB);
        FormBody.Builder builder = new FormBody.Builder();
        for (String s :
                paramsPro.keySet()) {
            builder
                    .add(s, paramsPro.get(s));
        }
        FormBody build = builder.build();
        Request requestPost = new Request.Builder()
                .url(aliUrl + "/index.php?vcode/validate")
                .post(build)
                .build();
        okhttp.newCall(requestPost).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("域名请求失败：" + e.getMessage());
//                addChangeBean(index);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = Model.getInstance().getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        String s1 = jsonObject.optJSONObject("data").optString("validate");
                        StringBuffer real = new StringBuffer();
                        for (int i = 0; i < s1.length() / 2; i++) {
                            real.append(s1.charAt(i * 2));
                        }
                        String s2 = real.toString();
                        String s = MD5Utils.toMD5("666777");

                        if (TextUtils.equals(s, s2)) {
                            WelcomeActivity.observable = BastRetrofit.getInstance().getObservable(new HttpApiInstance(aliUrl));
                            AppUtils.getInstance().setHost(aliUrl);
                            if (Myapplication.Mcontext.getResources().getString(R.string.AppStyle).equals("type1")) {
                                Intent intent = new Intent(Myapplication.Mcontext, MainActivity2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Myapplication.Mcontext.startActivity(intent);
                            } else if (Myapplication.Mcontext.getResources().getString(R.string.AppStyle).equals("type2")) {
                                Intent intent = new Intent(Myapplication.Mcontext, MainActivity3.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Myapplication.Mcontext.startActivity(intent);
                            }
                        } else {
//                            addChangeBean(index);
                        }
                    } else {
//                        addChangeBean(index);
                    }
                } catch (IOException e) {
//                    addChangeBean(index);
                    e.printStackTrace();
                } catch (JSONException e) {
//                    addChangeBean(index);
                    e.printStackTrace();
                }
            }
        });
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void doCall() {
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDialog(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void refuse() {
        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
