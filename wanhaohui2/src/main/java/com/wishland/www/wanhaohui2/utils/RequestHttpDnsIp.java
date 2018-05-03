package com.wishland.www.wanhaohui2.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.MyApplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2017/10/3.
 */

public class RequestHttpDnsIp {
    //    private static final String ALIYUN_URL = "www.pj78777.com";
//    private static String aliUrl = "www.whh7788.com";
    private static final String HTTP_SCHEMA = "http://";
    private static final String HTTPS_SCHEMA = "https://";
    private static final String TAG = "httpdns_android_demo";
    private static HttpDnsService httpdns;
    public static final String accountID = "117278";
    private static ExecutorService pool = Executors.newSingleThreadExecutor();
    private static String ip;
    private static boolean isSend = false;
    private static Context context;


    public static void initHttpDns(Handler handler, String baseUrl) {
        // 初始化httpdns
        httpdns = HttpDns.getService(MyApplication.baseContext, accountID);
        setPeeResoleHosts(baseUrl);
        // 允许过期IP以实现懒加载策略
        httpdns.setExpiredIPEnabled(true);
        normalRequest(handler, baseUrl);
    }

    /**
     * 设置预解析域名列表代码示例
     */
    private static void setPeeResoleHosts(String aliUrl) {
        // 设置预解析域名列表
        // 可以替换成您在后台配置的域名
        httpdns.setPreResolveHosts(new ArrayList<>(Arrays.asList(aliUrl)));
    }

    /**
     * 通过IP直连方式发起普通请求示例
     * 1. 通过IP直连时,为绕开服务域名检查,需要在Http报头中设置Host字段
     *
     * @param handler
     */
    private static void normalRequest(final Handler handler, String baseUrl) {
        getIp(handler, baseUrl);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.obj = ip;
        handler.sendMessageDelayed(msg, 500);
    }

    private static void getIp(final Handler handler, final String baseUrl) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String originalUrl = HTTPS_SCHEMA + baseUrl;
                    URL url = new URL(originalUrl);
                    // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 异步接口获取IP
                    ip = httpdns.getIpByHostAsync(url.getHost());
                    if (TextUtils.isEmpty(ip)) {
                        getIp(handler, baseUrl);
                    } else {
                        isSend = true;
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = ip;
                        handler.sendMessage(msg);
                    }
                } catch (Throwable throwable) {
                    Log.e(TAG, "normal request failed.", throwable);
                }
            }
        });
    }


}
