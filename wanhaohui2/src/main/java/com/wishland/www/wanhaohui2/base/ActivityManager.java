package com.wishland.www.wanhaohui2.base;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.wishland.www.wanhaohui2.BuildConfig;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.view.activity.DetailsHtmlPageActivity;

import java.util.Stack;

/**
 * Created by admin on 2017/10/15.
 */

public class ActivityManager {

    private static ActivityManager activityManager = null;

    public static ActivityManager getActivityManager() {
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    private Stack<Activity> stack = new Stack<>();

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            stack.add(activity);
        }
    }

    /**
     * 删除指定的Activity
     *
     * @param activity
     */
    public void deleteActivity(Activity activity) {
        for (int x = stack.size() - 1; x >= 0; x--) {
            //对比栈中的实例的类 和 activity的类 是否一样
            if (stack.get(x).getClass().equals(activity.getClass())) {
                activity.finish();
                stack.remove(x);
            }
        }
    }

    public Context getActivity(Activity activity) {
        for (int x = stack.size() - 1; x >= 0; x--) {
            //对比栈中的实例的类 和 activity的类 是否一样
            if (stack.get(x).getClass().equals(activity.getClass())) {
                activity.finish();
                return activity;
            }
        }
        return MyApplication.baseContext;
    }

    /**
     * 删除当前的Activity (EX: 当前代表在栈顶，就时必须显示在屏幕界面)
     */
    public void deleteThisActivity() {
        Activity activity = stack.lastElement();
        activity.finish();
        stack.remove(activity);
    }

    /**
     * 删除所有的Activi
     */
    public void deleteAllActivity() {
        for (int x = stack.size() - 1; x >= 0; x--) {
            Activity activity = stack.get(x);
            activity.finish();
            stack.remove(x);
        }
    }

    /**
     * @return 返回栈的大小
     */
    public int getStackCount() {
        return stack.size();
    }

    public Application.ActivityLifecycleCallbacks callback = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (activity != null) {
                stack.add(activity);
                MyApplication.baseContext = activity;
                //友盟统计启动数据
                PushAgent.getInstance(activity).onAppStart();

            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            try {
                MobclickAgent.onResume(activity);

                String name = activity.getClass().getSimpleName();
                MobclickAgent.onPageStart(name);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            try {
                MobclickAgent.onPause(activity);

                String name = activity.getClass().getSimpleName();
                MobclickAgent.onPageEnd(name);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity != null) {
                stack.remove(activity);
                OkGo.getInstance().cancelTag(activity);
            }

        }
    };
}
