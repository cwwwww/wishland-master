package com.wishland.www.wanhaohui2.utils;

import com.wishland.www.wanhaohui2.base.MyApplication;

/**
 * Created by admin on 2017/10/9.
 */

public class PxUtil {
    public static final float DENSITY = MyApplication.baseContext.getResources().getDisplayMetrics().density;
    public static final float SCALE_DENSITY = MyApplication.baseContext.getResources().getDisplayMetrics().scaledDensity;

    public static int px2dp(float px) {
        return (int) (px / DENSITY + 0.5f);
    }

    public static int dp2px(float dp) {
        return (int) (dp * DENSITY + 0.5f);
    }

    public static int px2sp(float px) {
        return (int) (px / SCALE_DENSITY + 0.5f);
    }

    public static int sp2px(float sp) {
        return (int) (sp * SCALE_DENSITY + 0.5f);
    }
}
