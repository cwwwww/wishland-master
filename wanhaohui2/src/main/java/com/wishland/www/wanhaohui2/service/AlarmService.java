package com.wishland.www.wanhaohui2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.wanhaohui2.api.BaseRetrofit;
import com.wishland.www.wanhaohui2.api.HttpApiInstance;
import com.wishland.www.wanhaohui2.bean.KeyStoreBean;
import com.wishland.www.wanhaohui2.bean.UrlPingInfo;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.WelcomeNewActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/12/21.
 */

public class AlarmService extends Service {

    /**
     * 每5分钟更新一次数据
     */
    private static final int ONE_Minute = 1 * 60 * 1000;
//        private static final int ONE_Minute = 5 * 60 * 1000;
    private static final int PENDING_REQUEST = 0;

    public AlarmService() {
    }

    /**
     * 调用Service都会执行到该方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("Service", "背景程序开始作业：");

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
//                            onCreate();

//                            changeFastestIP();
                            updateUserOnline();
                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, ONE_Minute); //execute in every 1000 ms


        Log.e("Service", "背景程序结束作业：");
        return super.onStartCommand(intent, flags, startId);
    }


    /****
     * 利用取得的IP 开始APP执行
     * @param ip
     */
    private void initKeyStore(final String ip) {
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
                            UserSP.getSPInstance().setKeyStore(UserSP.KEYSTORE, keyStoreBean.getData().getKey());
//                            BaseApi.KEYSTORE = keyStoreBean.getData().getKey();
                            Model.mObservable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(baseUrl + "/"));
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /***
     * 用户在线监测
     */
    private void updateUserOnline() {
        //这里模拟后台操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = UserSP.getSPInstance().getToken(UserSP.LOGIN_TOKEN);
                if (Model.mObservable != null) {
                    Model.getInstance().apiHeartRequest(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("cww", "用户在线监测失败：" + e.getMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String s = responseBody.string();
                                Log.e("cww", "用户在线监测成功：" + s);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }).start();
    }


    ScheduledExecutorService pools = null;
    int times = 0;
    boolean isSavedNewList = false;
    ArrayList<UrlPingInfo> urlPingList = new ArrayList<>();

    /***
     * 动态IP
     * 每次判断是否为最快的ip位置
     * 并更换为最快IP
     */
    private void changeFastestIP() {
        times = 0;
        ArrayList<String> ipPools = UserSP.getSPInstance().getIpPools();
        pools = Executors.newSingleThreadScheduledExecutor();
        urlPingList = new ArrayList<>();
        if (ipPools != null) {
            for (int ipDex = 0; ipDex < ipPools.size(); ipDex++) {
                final String CurrentUrl = ipPools.get(ipDex);
                final Runnable rn = new Runnable() {
                    @Override
                    public void run() {
                        long pingResult = pingUrl(CurrentUrl);


                        if (pingResult >= 9999) {
                            if (times > 3) {
                                pools.shutdown();
                                Log.e("changeFastestIP", "失败重试已达，进程关闭！");
                            } else {
                                pools.schedule(this, 1, TimeUnit.SECONDS);
                                Log.e("changeFastestIP", "失败重试次数[" + times + "]！");
                                times++;
                            }
                        } else {
                            UrlPingInfo pingInfo = new UrlPingInfo();
                            pingInfo.URLTitle = "线路";
                            pingInfo.URLString = CurrentUrl;
                            pingInfo.PingValue = pingResult;
                            urlPingList.add(pingInfo);
                            Log.e("changeFastestIP", "测试线路=" + CurrentUrl + "--> PING值=" + pingResult);
                        }
                    }
                };
                pools.schedule(rn, 0, TimeUnit.SECONDS);
            }
            pools.schedule(new Runnable() {
                @Override
                public void run() {
                    if (urlPingList.size() >= 1) {
                        long fastestPing = 0;
                        String fastestURL = "";
                        for (int i = 0; i < urlPingList.size(); i++) {
                            if (i == 0) {
                                fastestPing = urlPingList.get(i).PingValue;
                                fastestURL = urlPingList.get(i).URLString;
                            } else {
                                if (fastestPing > urlPingList.get(i).PingValue) {
                                    fastestURL = urlPingList.get(i).URLString;
                                }
                            }
                        }
                        if (!UserSP.getSPInstance().getFavorIp().equals(fastestURL)) {
                            UserSP.getSPInstance().setFavorIp(fastestURL);
                            Model.mObservable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(fastestURL + "api/"));
                            Log.e("changeFastestIP", "更换最新 IP 为" + fastestURL);
                        } else {
                            Log.e("changeFastestIP", "沿用旧 IP 为" + fastestURL);
                        }
                    } else {
                        Log.e("changeFastestIP", "IP 池为空");
                    }
                }
            }, 10, TimeUnit.SECONDS);
        } else {
            Log.e("changeFastestIP", "域名池为空");
        }

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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

