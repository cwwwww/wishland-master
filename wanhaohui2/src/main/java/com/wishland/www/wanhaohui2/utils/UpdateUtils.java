package com.wishland.www.wanhaohui2.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.view.activity.MainActivity;

/**
 * Created by JayCruz on 2018/1/3.
 */

public class UpdateUtils {
    public static final int FORCE_UPDATE = 4;
    public static final int NORMAL_UPDATE = 3;
    public static final int NON_UPDATE = 0;
    public static int checkUpdateType(int newVerson, int userVerson, int versionType) {
        if (userVerson < newVerson) {
            switch (versionType) {
                case FORCE_UPDATE:
                    return FORCE_UPDATE;
                case NORMAL_UPDATE:
                    return NORMAL_UPDATE;
                default:
                    return NON_UPDATE;
            }
        } else {
            return NON_UPDATE;
        }
    }


    public static int getAPPRealVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int dealVersion(String s) {
        if (s.contains(".")) {
            s = s.replace(".", "");
        }
        return Integer.valueOf(s);
    }
}
