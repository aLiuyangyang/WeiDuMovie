package com.bw.movie.utils;

import java.util.regex.Pattern;

public class RegularUtil {

    //验证用户名
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";
    //验证密码
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";
    //验证手机号
    public static final String REGEX_MOBILE = "^1\\d{10}$";
    //验证邮箱
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    //用户名
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
    //验证密码
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    //验证手机号
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    //验证邮箱
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }



}
