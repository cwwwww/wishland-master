package com.wishland.www.wanhaohui2.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.wishland.www.wanhaohui2.base.MyApplication.baseContext;

/**
 * Created by Administrator on 2017/4/21.
 */

public class UserSP {
    public static final String LOGIN_CHECKED = "checked";
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    //15分钟失效
    public static final String LOGIN_TOKEN = "token";
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_SITE = "site";
    public static final String MOLIBE_NUMBER = "molibeNumber";
    public static final String LOGIN_TYPE = "type";
    public static final String USERSP = "usersp";
    public static final String VIP_IMAGE_URI = "vipImageUrl";
    public static final String Login_OUT = "loginOut";
    public static final String START_UP_IMG_TIME = "startUpImgTime";
    public static final String IP_POOLS = "IP_POOLS";
    public static final String FAVOR_IP = "favor_ip";
    public static final String KEYSTORE = "key_store";
    private static UserSP usersp;
    private SharedPreferences sp = baseContext.getSharedPreferences(USERSP, Context.MODE_PRIVATE);

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
     * @param value 保存最快的IP
     */
    public void setFavorIp(String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(FAVOR_IP, value);
        edit.commit();
    }

    public String getFavorIp() {
        String favorIp = sp.getString(FAVOR_IP, "");
        return favorIp;
    }

    /**
     * @param value 保存是否记住密码状态
     */
    public void setIpPools(ArrayList<String> value) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < value.size(); i++) {
            jsonArray.put(value.get(i));
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(IP_POOLS, jsonArray.toString());
        edit.commit();
    }


    /**
     */
    public ArrayList<String> getIpPools() {
        String ips = sp.getString(IP_POOLS, "");
        if (!ips.isEmpty()) {
            try {
                ArrayList<String> saveArr = new ArrayList<String>();
                JSONArray jsonArray2 = new JSONArray(ips);
                for (int i = 0; i < jsonArray2.length(); i++) {
                    saveArr.add(jsonArray2.getString(i));
                    Log.d("your JSON Array", jsonArray2.getString(i) + "");
                }
                return saveArr;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
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
     * 保存手机号
     */
    public void setMolibe(String key, String molibe) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, molibe);
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
     * 保存用户登录的方式
     */
    public void setLoginType(String key, int type) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, type);
        edit.commit();
    }

    /**
     * 保存用户的vip图片
     */
    public void setVipImageUrl(String key, String vipImageUrl) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, vipImageUrl);
        editor.commit();
    }

    /**
     * 保存loginout
     */
    public void setLoginOut(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存启动页修改时间
     */
    public void setStartUpImgTime(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存KEYSTORE
     */
    public void setKeyStore(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
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

    public String getMolibe() {
        String string = sp.getString(MOLIBE_NUMBER, "");
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

    public int getLoginType(String key) {
        int type = sp.getInt(key, 1);
        return type;
    }

    public String getVipImageUri(String key) {
        String vipImageUrl = sp.getString(key, "");
        return vipImageUrl;
    }

    public boolean getLoginOut(String key) {
        boolean value = sp.getBoolean(key, false);
        return value;
    }

    public String getStartUpImgTime(String key) {
        String value = sp.getString(key, "");
        return value;
    }

    public String getKeystore(String key) {
        String value = sp.getString(key, "4414c5d94ca24942bad650c18ecf49a5");
        return value;
    }

}
