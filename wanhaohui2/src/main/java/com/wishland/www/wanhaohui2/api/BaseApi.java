package com.wishland.www.wanhaohui2.api;

import android.os.Environment;

import retrofit2.Retrofit;

/**
 * Created by admin on 2017/10/13.
 */

public abstract class BaseApi {
    public static final String VERSION = "app:3";
//    public static String KEYSTORE = "4414c5d94ca24942bad650c18ecf49a5";
    public static final String HTML5DATA = "HTML5";
    public static final String DEVICE = "android";
    public static String CustomHtml5 = "";
    public static String NEWHTML = "com.whh.www.html";
    public static String IMAGE_URL = Environment.getExternalStorageDirectory() + "/whh/";
    public static boolean GAME_MODEL_OPEN = false;

    private String baseUrl;

    public BaseApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public abstract ApiUrl getObservable(Retrofit retrofit);
}
