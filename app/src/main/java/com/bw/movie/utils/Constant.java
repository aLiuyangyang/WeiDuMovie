package com.bw.movie.utils;

import java.util.Locale;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:网络接口
 */
public class Constant {


    public static String Base_Url="http://mobile.bwstudent.com/";

    //public static final String Base_Url="http://172.17.8.100/";
    //登录post
     public static String Login_Path="movieApi/user/v1/login";
    //注册post
    public static String Register_Path="movieApi/user/v1/registerUser";
    //查询热门电影列表get
    public static String Hotfile_Path="movieApi/movie/v1/findHotMovieList?page=%d&count=%d";
    //轮播图
    public static String Banner_Path="movieApi/movie/v1/findReleaseMovieList?page=1&count=10";
    //查询正在上映电影列表
    public static String  NWESHOWING_Path="movieApi/movie/v1/findComingSoonMovieList?page=%d&count=%d";
    public static String Coming_Path = "movieApi/movie/v1/findReleaseMovieList?page=%d&count=10";
}
