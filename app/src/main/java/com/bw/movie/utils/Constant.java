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
    //登录
     public static String Login_Path="user/v1/login";
    //注册
    public static String Register_Path="user/v1/registerUser";
    //查询热门电影列表
    public static String Hotfile_Path="movie/v1/findHotMovieList?page=%d&count=%d";
    //轮播图
    public static String Banner_Path="movie/v1/findReleaseMovieList?page=%d&count=%d";
    //查询正在上映电影列表
    public static String  NWESHOWING_Path="movie/v1/findComingSoonMovieList?page=%d&count=%d";
    //热门影院
    public static String Recommend_Path="cinema/v1/findRecommendCinemas?page=%d&count=10";
    //附近影院
    public static String Nearby_Path="cinema/v1/findNearbyCinemas?page=%d&count=10";
   //详情接口
    public static String Details_Path="movie/v1/findMoviesDetail?movieId=%d";
    //查询电影信息明细
    public static String DetailsOfCinema_Path="cinema/v1/findCinemaInfo?cinemaId=%d";
    //关注
    public static String Attention_Path="cinema/v1/verify/followCinema?cinemaId=%d";
    //取消关注
    public static String Unfollow_Path="cinema/v1/verify/cancelFollowCinema?cinemaId=%d";
    public static String Weixin_Path="user/v1/weChatBindingLogin?code=%d";
    public static String BDWeixin_Path="user/v1/verify/bindWeChat?userId=%d";
    public static String SFWeixin_Path="user/v1/verify/whetherToBindWeChat?=%d";

}
