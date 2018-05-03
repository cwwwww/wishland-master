package com.wishland.www.wanhaohui2.api;

import retrofit2.Retrofit;

/**
 * Created by admin on 2017/10/13.
 */

public class HttpApiInstance extends BaseApi {

    public HttpApiInstance(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public ApiUrl getObservable(Retrofit retrofit) {
        return retrofit.create(ApiUrl.class);
    }
}
