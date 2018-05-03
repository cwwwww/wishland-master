package com.wishland.www.api;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by erha on 2017/8/24.
 */

public class AppOnlineUpdate {
    static Retrofit mRetrofit;
    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("https://tpfw.083075.com:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
    public interface updataAddress {
        //https://tpfw.083075.com:8080/tizi/system/getAppLastChange
        @POST("tizi/system/getAppLastChange")
        Observable<ResponseBody> onlineUpdate(
                @Query("code") String codeStr);
    }
}

