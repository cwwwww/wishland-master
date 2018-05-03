package com.wishland.www.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.api.BastApi;
import com.wishland.www.api.HttpApiInstance;
import com.wishland.www.controller.base.BastRetrofit;
import com.wishland.www.model.Model;
import com.wishland.www.model.bean.DomainBean;
import com.wishland.www.model.bean.DomainChangeBean;
import com.wishland.www.model.bean.KeyStoreBean;
import com.wishland.www.view.Myapplication;
import com.wishland.www.view.activity.MainActivity2;
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
 * Created by admin on 2017/9/9.
 */

public class RequestDomainUtil {
    private final static String DOMAIN_URL = "http://119.9.107.44:9999/getDomainMapper";

    private final static String UPDATE_DOMAIN_URL = "http://119.9.107.44:9999/updateDomainMapper";

    private static List<DomainBean.DataBean> domainList;

    private static DomainBean domainBean;

    private static int count = 0;

    private static List<DomainChangeBean> changes = new ArrayList<>();

    private static Activity context;

    private static String domainCode;

    public static void initDomain(Activity c, String dc) {
        context = c;
        domainCode = dc;
        getDomainList();
    }

    private static void getDomainList() {
        Map<String, Object> params = setParams();
        OkGo.post(DOMAIN_URL).upString(new Gson().toJson(params))
                .execute(new AbsCallback<String>() {
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("cww", "请求域名列表成功！");
                        domainBean = new Gson().fromJson(s, DomainBean.class);
                        domainList = domainBean.getData();
                        requestHostByUrl(count);
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
        Map<String, String> p = new HashMap<>();
        p.put("code", domainCode);
        p.put("domainState", "0");
        params.put("uri", "/getDomainMapper");
        params.put("token", "");
        params.put("paramData", p);
        return params;
    }

    private static void setUpdateParams() {
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("changes", changes);
        Map<String, Object> params = new HashMap<>();
        params.put("uri", "/updateDomainMapper");
        params.put("token", "");
        params.put("paramData", paramData);
        OkGo.post(UPDATE_DOMAIN_URL).upString(new Gson().toJson(params)).execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("cww", "提交域名请求信息" + s);
            }
        });
    }

    private static void requestHostByUrl(final int index) {
        if (domainList == null || index == domainList.size()) {
            setUpdateParams();
            exitApp();
        } else {
            Log.e("cww", "请求" + index);
            requestKeyStore(domainList.get(index).getDomain() + "/api/", index);
        }
    }

    private static void requestKeyStore(final String host, final int index) {
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
                    verifyHost(host, index);
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

    private static void test(String s, int index) {
        WelcomeActivity.observable = BastRetrofit.getInstance().getObservable(new HttpApiInstance(s));
        AppUtils.getInstance().setHost(s);
        setUpdateParams();
        Intent intent = new Intent(Myapplication.Mcontext, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Myapplication.Mcontext.startActivity(intent);
    }

    private static void exitApp() {
        ToastUtil.showUI(context, "初始化应用失败,请关闭重新尝试...");
    }

    private static void verifyHost(final String host, final int index) {
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
                addChangeBean(index);
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
                            setUpdateParams();
                            Intent intent;
//                            if (Myapplication.Mcontext.getResources().getString(R.string.AppName).equals("威尼斯")) {
                                intent = new Intent(Myapplication.Mcontext, MainActivity2.class);
//                            } else {
//                                intent = new Intent(Myapplication.Mcontext, MainActivity.class);
//                            }
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Myapplication.Mcontext.startActivity(intent);

                        } else {
                            addChangeBean(index);
                        }
                    } else {
                        addChangeBean(index);
                    }
                } catch (IOException e) {
                    addChangeBean(index);
                    e.printStackTrace();
                } catch (JSONException e) {
                    addChangeBean(index);
                    e.printStackTrace();
                }
            }
        });

    }

    private static void addChangeBean(int index) {
        DomainChangeBean changeBean = new DomainChangeBean();
        changeBean.setId(domainList.get(index).getId());
        changeBean.setDomainState("1");
        changeBean.setDomain(domainList.get(index).getDomain());
        changeBean.setSystemCode(domainList.get(index).getSystemCode());
        changes.add(changeBean);
        requestHostByUrl(++count);
    }
}
