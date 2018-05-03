package com.wishland.www.wanhaohui2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/19.
 */

public class JudgeUserUtil {


    /**
     * @param username 判断用户名
     * @return
     */
    public static boolean judgeUsername(String username) {
        if (!username.isEmpty() && firstCharacter(username) && username.length() > 5) {
            return true;
        } else {
            return false;
        }
        //用户名格式
//        String regExAccount = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$";
//        Pattern patternAccount = Pattern.compile(regExAccount);
//        Matcher matcherAccount = patternAccount.matcher(username);
//        if (!matcherAccount.matches()) {
//            return false;
//        } else {
//            return true;
//        }
    }

    /**
     * @param passwork 判断密码
     * @return
     */
    public static boolean judgePassword(String passwork) {
//        if (!passwork.isEmpty() && passwork.length() > number) {
//            return true;
//        } else {
//            return false;
//        }
        //密码格式
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(passwork);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param passwork
     * @return
     */
    public static boolean judgeQphone(String passwork, int number) {
        if (!passwork.isEmpty() && passwork.length() == number) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @return 判断username第一个字符是否为字母，并返回结果；
     */
    public static boolean firstCharacter(String username) {
        char x = username.charAt(0);
        if ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }
}
