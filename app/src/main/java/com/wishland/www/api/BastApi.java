package com.wishland.www.api;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/20.
 */
public abstract class BastApi {
    public static final String VERSION = "1";
    public static String KEYSTORE;
    //    public static final String CustomHtml5 = "https://chat6.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=17779&configID=48708&jid=&s=1";
    public static final String HTML5DATA = "HTML5";
    public static final String SIXURI = "SIXURI";
    public static final String SIXTEXT = "SIXTEXT";
    public static String CustomHtml5 = "";
    /*基础url*/
    public String url = "https://tpfw.083075.com:8080";
    public String baseUrl;
    //    public static String NEWHTML = "com.wishland.www.html";
    public static String NEWHTML;
    public static String BASEURL;

    public BastApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract ApiAddress getObservable(Retrofit retrofit);

    public String getBaseUrl() {
        return baseUrl;
    }

}
