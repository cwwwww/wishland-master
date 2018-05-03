package com.wishland.www.wanhaohui2.model;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wishland.www.wanhaohui2.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JayCruz on 2017/12/21.
 * 为了防止 被ACTIVITY劫持
 */

public class AntiHijackingUtil {

    public static final String TAG = "AntiHijackingUtil";
    // 白名单列表
    private static List<String> safePackages;

    static {
        safePackages = new ArrayList<String>();
    }

    public static void configSafePackages(List<String> packages) {
        return;
    }

    private static PackageManager pm;
    private List<ApplicationInfo> mlistAppInfo;

    /**
     * 检测当前Activity是否安全
     */
    public static boolean checkActivity(Context context) {
        boolean safe = false;
        pm = context.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序
        List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>(); // 保存过滤查到的AppInfo
        //appInfos.clear();

        for (ApplicationInfo app : listAppcations) {//这个排序必须有.
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //appInfos.add(getAppInfo(app));
                safePackages.add(app.packageName);
            }
        }
        safePackages.add(context.getPackageName() + ":html");
        safePackages.add(context.getPackageName() + ":channel");

        //得到所有的系统程序包名放进白名单里面.
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivityPackageName;
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        if (sdkVersion >= 21) {//获取系统api版本号,如果是5x系统就用这个方法获取当前运行的包名
            runningActivityPackageName = getCurrentPkgName(context);
        } else {
            runningActivityPackageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        //如果是4x及以下,用这个方法.
        if (runningActivityPackageName != null) {//有些情况下在5x的手机中可能获取不到当前运行的包名，所以要非空判断。
            Log.e("checkActivity", "runningActivityPackageName==>" + runningActivityPackageName);
            Log.e("checkActivity", "context.getPackageName()==>" + context.getPackageName());
            if (runningActivityPackageName.equals(context.getPackageName())) {
                safe = true;
            } else {
                // 白名单比对
                for (String safePack : safePackages) {
                    if (safePack.equals(runningActivityPackageName)) {
                        safe = true;
                    }
                }
            }
        } else {
            safe = true;
        }
        Log.d(TAG, "checkActivity: Safe" + (safe ? "true" : "false"));
        return showHackMessage(context, safe);
    }

    public static boolean showHackMessage(Context context, boolean safe) {
        if (!safe) {
            String currentActivityName = context.getResources().getString(R.string.AppName);
            Toast.makeText(context, "警告，目前疑似未知软件弹出，若有疑虑，请点击离开！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public static String getCurrentPkgName(Context context) {//5x系统以后利用反射获取当前栈顶activity的包名.
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String pkgName = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");//通过反射获取进程状态字段.
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List appList = am.getRunningAppProcesses();
        ActivityManager.RunningAppProcessInfo app;
        for (int i = 0; i < appList.size(); i++) {
            //ActivityManager.RunningAppProcessInfo app : appList
            app = (ActivityManager.RunningAppProcessInfo) appList.get(i);
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {//表示前台运行进程.
                Integer state = null;
                try {
                    state = field.getInt(app);//反射调用字段值的方法,获取该进程的状态.
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (state != null && state == START_TASK_TO_FRONT) {//根据这个判断条件从前台中获取当前切换的进程对象.
                    currentInfo = app;
                    break;
                }
            }
        }
        if (currentInfo != null) {
            pkgName = currentInfo.processName;
        }
        return pkgName;
    }
}
