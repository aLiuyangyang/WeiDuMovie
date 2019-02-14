package com.bw.movie.utils;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * date:2018/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public interface BaseApis<T> {

    @GET
    Observable<ResponseBody> get(@Url String url);

    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @POST
    Observable<ResponseBody> Image(@Url String url, @HeaderMap Map<String, Object> headermap, @Body MultipartBody body);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> map);
    @POST
    Observable<ResponseBody> postHead(@Url String path,@Body MultipartBody multipartBody);
}

