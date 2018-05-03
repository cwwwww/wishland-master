package com.wishland.www.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.wishland.www.view.Myapplication;

/**
 * Created by admin on 2017/11/14.
 */

public class StatusBarHightUtil {
    public static int getStatusBarHeight() {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = Myapplication.Mcontext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = Myapplication.Mcontext.getResources().getDimensionPixelSize(resourceId);
        }
//        Log.e("cww", statusBarHeight1 + "aaaaaaa");
        return statusBarHeight1;
    }

    /**
     * 判断底部navigator是否已经显示
     *
     * @return
     * @paramwindowManager
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {

        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;

        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();

        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;

        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;

    }

}
