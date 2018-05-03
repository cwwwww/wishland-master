package com.wishland.www.controller.base;


import com.wishland.www.api.ApiAddress;
import com.wishland.www.api.BastApi;
import com.wishland.www.api.OkHttpClientManager;
import com.wishland.www.utils.HttpsUtils;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.BuildConfig;

/**
 * Created by Administrator on 2017/4/20.
 */

public class BastRetrofit {

    private static volatile BastRetrofit bastretrofitinstance;
    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();
    public static OkHttpClient client;
    public static String ip;

    public static BastRetrofit getInstance() {
        if (bastretrofitinstance == null) {
            synchronized (BastRetrofit.class) {
                if (bastretrofitinstance == null) {
                    bastretrofitinstance = new BastRetrofit();
                    client = getOkhttp();
                }
            }
        }
        return bastretrofitinstance;
    }

    public ApiAddress getObservable(final BastApi bastApi) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(bastApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ApiAddress observable = bastApi.getObservable(retrofit);
        return observable;
    }

    public static OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public static OkHttpClient getOkhttp() {

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        /**
         * Log信息拦截器
         */
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }

        /**
         *设置cookie
         */
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(45, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        /**
         * 错误重连
         */
        builder.retryOnConnectionFailure(true);


        /**
         * 设置SSL文件
         */
        //builder.sslSocketFactory(OkHttpClientManager.getInstance().getSocketFactory());

        if (OkHttpClientManager.getInstance().getSocketFactory() != null) {

        } else {
            HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
            builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        }

        /**
         *
         *  信任所有的安全证书
         *
         * */
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        OkHttpClient build = builder.build();

        return build;
    }

}
