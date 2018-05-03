package com.wishland.www.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.R;
import com.wishland.www.api.BastApi;
import com.wishland.www.api.HttpApiInstance;
import com.wishland.www.controller.base.BastRetrofit;
import com.wishland.www.model.Model;
import com.wishland.www.model.bean.DomainBean;
import com.wishland.www.model.bean.DomainBeanNew;
import com.wishland.www.model.bean.DomainChangeBean;
import com.wishland.www.model.bean.KeyStoreBean;
import com.wishland.www.view.Myapplication;
import com.wishland.www.view.activity.MainActivity2;
import com.wishland.www.view.activity.MainActivity3;
import com.wishland.www.view.activity.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/11/15.
 */

public class RequestDomainUtilNew {
    private final static String DOMAIN_URL = "https://auberapp.yule-app.net/index.php/Api/Index/getDomainName";
    private static DomainBeanNew domainBean;
    private static String host;
    private static Activity context;

    private static String domainCode;

    public static void initDomain(Activity c, String dc) {
        context = c;
        domainCode = dc;
        getDomainList();
    }

    private static void getDomainList() {
        Map<String, Object> params = setParams();
        OkGo.post(DOMAIN_URL).params("did", domainCode).isMultipart(true)
                .execute(new AbsCallback<String>() {
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("cww", "请求域名列表成功！");
                        domainBean = new Gson().fromJson(s, DomainBeanNew.class);
                        if (domainBean.getCode() == 1) {
                            host = domainBean.getData().getDomain_name();
                            requestHostByUrl();
                        } else {
                            exitApp();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //报错处理
                        Log.e("cww", "请求域名列表异常！" + e.getMessage());
                        exitApp();
                    }
                });
    }

    private static Map<String, Object> setParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("did", domainCode);
        return params;
    }

    private static void requestHostByUrl() {
        if (host != null) {
            requestKeyStore(host + "/api/");
        } else {
            exitApp();
        }
    }

    private static void requestKeyStore(final String host) {
        OkGo.<String>get(host + "index.php?vcode/key").execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                KeyStoreBean keyStoreBean = new Gson().fromJson(s, KeyStoreBean.class);
                if (keyStoreBean.getStatus() == 200) {
                    BastApi.KEYSTORE = keyStoreBean.getData().getKey();
                    verifyHost(host);
                } else {
                    exitApp();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                exitApp();
            }
        });

    }

    private static void exitApp() {
        ToastUtil.showUI(context, "初始化应用失败,请关闭重新尝试...");
    }

    private static void verifyHost(final String host) {
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
                .url(host + "/index.php?vcode/validate")
                .post(build)
                .build();
        okhttp.newCall(requestPost).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("域名请求失败：" + e.getMessage());
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
                            WelcomeActivity.observable = BastRetrofit.getInstance().getObservable(new HttpApiInstance(host));
                            AppUtils.getInstance().setHost(host);
                            Intent intent = null;
                            if (Myapplication.Mcontext.getResources().getString(R.string.AppStyle).equals("type1")) {
                                intent = new Intent(Myapplication.Mcontext, MainActivity2.class);
                            } else if (Myapplication.Mcontext.getResources().getString(R.string.AppStyle).equals("type2")) {
                                intent = new Intent(Myapplication.Mcontext, MainActivity3.class);
                            }
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Myapplication.Mcontext.startActivity(intent);

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

}
