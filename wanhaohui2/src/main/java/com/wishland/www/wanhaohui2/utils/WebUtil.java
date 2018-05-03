package com.wishland.www.wanhaohui2.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.view.activity.DetailsHtmlPageActivity;
import com.wishland.www.wanhaohui2.view.activity.WebViewForDiscountActivity;

/**
 * Created by admin on 2017/10/18.
 */

public class WebUtil {
    public static void toWebActivity(Context context, String url, String... args) {
        /***
         * CROSS_WALK 取代原生WEBVIEW方案
         * 暂不使用
         */
//        Intent intent = new Intent(context, DetailsHtmlCrossWalkActivity.class);
        Intent intent = new Intent(context, DetailsHtmlPageActivity.class);
        intent.setAction(BaseApi.NEWHTML);
        intent.putExtra(BaseApi.HTML5DATA, url);
        Log.e("cww", "webutil" + url);
        intent.putExtra("title", args[0]);
        if (args.length == 2) {
            intent.putExtra("id", args[1]);
        }
        context.startActivity(intent);
    }

    public static void toSimpleWebActivity(Context context, String url) {
        /***
         * CROSS_WALK 取代原生WEBVIEW方案
         * 暂不使用
         */
//        Intent intent = new Intent(context, DetailsHtmlCrossWalkActivity.class);
        Intent intent = new Intent(context, DetailsHtmlPageActivity.class);
        intent.setAction(BaseApi.NEWHTML);
        intent.putExtra(BaseApi.HTML5DATA, url);
        Log.e("cww", "webutil" + url);
        context.startActivity(intent);
    }


    public static void toWebGameActivity(Context context, String url, String gameCategory, String gameTitle, String gameSubTitle) {
        String fastestIP = changeToFastestIP(url);
        /***
         * CROSS_WALK 取代原生WEBVIEW方案
         * 暂不使用
         */
//        Intent intent = new Intent(context, DetailsHtmlCrossWalkActivity.class);
        Intent intent = new Intent(context, DetailsHtmlPageActivity.class);
        intent.setAction(BaseApi.NEWHTML);
        intent.putExtra(BaseApi.HTML5DATA, fastestIP);
//        intent.putExtra(BaseApi.HTML5DATA, url);
        intent.putExtra("category", gameCategory);
        intent.putExtra("title", gameTitle);
        intent.putExtra("subTitle", gameSubTitle);
        context.startActivity(intent);
    }

    public static void toWebDiscountActivity(Context context, String url, String... args) {
        String fastestIP = changeToFastestIP(url);
        Intent intent = new Intent(context, WebViewForDiscountActivity.class);
        intent.setAction(BaseApi.NEWHTML);
        intent.putExtra(BaseApi.HTML5DATA, fastestIP);
//        intent.putExtra(BaseApi.HTML5DATA, url);
        Log.e("cww", "webutil" + changeToFastestIP(url));
        intent.putExtra("title", args[0]);
        if (args.length == 2) {
            intent.putExtra("id", args[1]);
        }
        context.startActivity(intent);
    }

    /***
     * 更换为最快速IP
     * @param url
     * @return
     */
    public static String changeToFastestIP(String url) {
        String favorIp = UserSP.getSPInstance().getFavorIp();
        if (favorIp.isEmpty()) {
            return "";
        } else {
            int endPoint = url.indexOf("/", 12);
            String urlWithoutFront = url.substring(endPoint + 1);
            String newUrlWithIP = favorIp + urlWithoutFront;
            return newUrlWithIP;
        }
    }
}
