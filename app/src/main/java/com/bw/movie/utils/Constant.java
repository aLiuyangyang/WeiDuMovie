package com.bw.movie.utils;

import java.util.Locale;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:网络接口
 */
public class Constant {


    public static String Base_Url="http://mobile.bwstudent.com/movieApi/";

    //public static final String Base_Url="http://172.17.8.100/";
    //登录post
     public static String Login_Path="user/v1/login";
    //注册post
    public static String Register_Path="user/v1/registerUser";
    //查询热门电影列表get
    public static String Hotfile_Path="movie/v1/findHotMovieList?page=%d&count=%d";
    //轮播图
    public static String Banner_Path="movie/v1/findReleaseMovieList?page=1&count=7";
    //查询正在上映电影列表
    public static String  NWESHOWING_Path="movie/v1/findComingSoonMovieList?page=%d&count=%d";
    public static String Coming_Path = "movie/v1/findReleaseMovieList?page=%d&count=%d";

    /*
    * 详情接口
    * */
    public static String Details_Path="movie/v1/findMoviesDetail?movieId=%d";

}
