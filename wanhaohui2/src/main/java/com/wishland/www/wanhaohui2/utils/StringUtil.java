package com.wishland.www.wanhaohui2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/10/29.
 */

public class StringUtil {

    /**
     * 字符串，每4个字符空格一位
     */
    public static String spaceAt4(String str) {

        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i += 4) {
            if (length - i <= 8) {      //防止ArrayIndexOutOfBoundsException
                sb.append(str.substring(i, i + 4)).append(" ");
                sb.append(str.substring(i + 4));
                break;
            }
            sb.append(str.substring(i, i + 4)).append(" ");
        }

        return sb.toString();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
