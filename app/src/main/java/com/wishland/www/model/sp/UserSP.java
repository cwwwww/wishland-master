package com.wishland.www.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wishland.www.view.Myapplication.Mcontext;

/**
 * Created by Administrator on 2017/4/21.
 */

public class UserSP {
    public static final String LOGIN_CHECKED = "checked";
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_TOKEN = "token";
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_SITE = "site";

    public static final String USERSP = "usersp";
    private static UserSP usersp;
    private SharedPreferences sp = Mcontext.getSharedPreferences(USERSP, Context.MODE_PRIVATE);

    private UserSP() {
    }

    public static UserSP getSPInstance() {
        if (usersp == null) {
            synchronized (UserSP.class) {
                if (usersp == null) {
                    usersp = new UserSP();
                }
            }
        }
        return usersp;
    }

    /**
     * @param key
     * @param value 保存是否记住密码状态
     */
    public void setCheckedSP(String key, boolean value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * @param key
     * @param value 保存用戶名
     */
    public void setUserName(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }


    /**
     * 保存密码
     */
    public void setPassWord(String loginPassword, String keypassword) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(loginPassword, keypassword);
        edit.commit();
    }

    /**
     * 保存token
     */
    public void setToken(String key, String token) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, token);
        edit.commit();
    }

    /**
     * 保存token
     */
    public void setSuccess(String key, int success) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, success);
        edit.commit();
    }

    /**
     * 保存pc_url
     */
    public void setSite(String key, String site) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, site);
        edit.commit();
    }

    /**
     * @param key
     * @return 返回保存密码状态
     */
    public boolean getBoolean(String key) {
        boolean aBoolean = sp.getBoolean(key, false);
        return aBoolean;
    }

    public String getString(String key) {
        String string = sp.getString(key, "");
        return string;
    }

    public String getToken(String key) {
        String string = sp.getString(key, "");
        return string;
    }

    public int getInt(String key) {
        int uid = sp.getInt(key, -1);
        return uid;
    }


}
