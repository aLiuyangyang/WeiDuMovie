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
    //关注影院
    public static String Attention_Path="cinema/v1/verify/followCinema?cinemaId=%d";
    //取消关注影院
    public static String Unfollow_Path="cinema/v1/verify/cancelFollowCinema?cinemaId=%d";
    //关注影片
    public static String Attentionmovie_Path="movie/v1/verify/followMovie?movieId=%d";
    //取消关注影片
    public static String Unfollowmovie_Path="movie/v1/verify/cancelFollowMovie?movieId=%d";
    //根据电影id查看电影排期
    public static String ChooseCinema_Path="movie/v1/findCinemasListByMovieId?movieId=%d";
    //根据电影ID和影院ID查询电影排期列表
    public static String ChooseClass_Path="movie/v1/findMovieScheduleList?cinemasId=%d&movieId=%d";
    //根据电影ID查询电影信息
    public static String MoviesById_Path="movie/v1/findMoviesById?movieId=%d";
    public static String Weixin_Path="user/v1/weChatBindingLogin?code=%d";
    public static String BDWeixin_Path="user/v1/verify/bindWeChat?userId=%d";
    public static String SFWeixin_Path="user/v1/verify/whetherToBindWeChat?=%d";

    public static String Camera_Banner="movie/v1/findMovieListByCinemaId?cinemaId=%d";

     public static  String URL_QUERY_COMMENT="movie/v1/findAllMovieComment?movieId=%d&page=1&count=10";
    //根据用户ID查询用户信息
     public static String Update_User="user/v1/verify/getUserInfoByUserId";
    //修改头像
    public static String UploadHeadPic_Path="user/v1/verify/uploadHeadPic";
    //修改密码
    public static String UpdataPass_Path="user/v1/verify/modifyUserPwd";
    //查询用户关注的影院信息
    public static String CinemaPageList_Path="cinema/v1/verify/findCinemaPageList?page=%d&count=%d";
    //查询用户关注的影片列表
    public static String MoviePageList_Path="movie/v1/verify/findMoviePageList?page=%d&count=%d";
    //用户签到
    public static String UserSignIn_Path="user/v1/verify/userSignIn";
    //意见反馈
    public static String RecordFeedBack_Path="tool/v1/verify/recordFeedBack";
    //用户购票记录查询列表
    public static String BuyTicketRecord_Path="user/v1/verify/findUserBuyTicketRecordList?page=%d&count=%d&status=%d";
   //修改用户信息
    public static String UserInfo_Path="user/v1/verify/modifyUserInfo";
    //查询新版本
    public static String NewVersion_Path="tool/v1/findNewVersion";
}
