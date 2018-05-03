package com.wishland.www.wanhaohui2.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.wishland.www.wanhaohui2.base.MyApplication;

/**
 * Created by admin on 2017/11/14.
 */

public class StatusBarHightUtil {
    public static int getStatusBarHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = MyApplication.baseContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = MyApplication.baseContext.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }
}
