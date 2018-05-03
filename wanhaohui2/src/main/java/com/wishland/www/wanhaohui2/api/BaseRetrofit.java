package com.wishland.www.wanhaohui2.api;

import com.wishland.www.wanhaohui2.utils.HttpsUtils;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
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
 * Created by admin on 2017/10/13.
 */

public class BaseRetrofit {

    private static volatile BaseRetrofit baseRetrofitInstance;

    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();

    private static OkHttpClient okHttpClient;

    public static String ip;

    private BaseRetrofit() {
    }

    public static BaseRetrofit getInstance() {
        if (baseRetrofitInstance == null) {
            synchronized (BaseRetrofit.class) {
                if (baseRetrofitInstance == null) {
                    baseRetrofitInstance = new BaseRetrofit();
                    okHttpClient = getOkHttp();
                }
            }
        }
        return baseRetrofitInstance;
    }

    public ApiUrl getObservable(BaseApi baseApi) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ApiUrl apiUrl = baseApi.getObservable(retrofit);
        return apiUrl;
    }

    public static OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public static OkHttpClient getOkHttp() {

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
//        try {
//            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            builder.sslSocketFactory(sslSocketFactory);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }


//        builder.sslSocketFactory(OkHttpClientManager.getInstance().getSocketFactory());

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
