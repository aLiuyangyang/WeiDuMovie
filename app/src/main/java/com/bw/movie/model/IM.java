package com.bw.movie.model;


import com.bw.movie.mycallback.MyCallBack;

import java.util.List;
import java.util.Map;

/**
 * date:2018/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public interface IM {
    void setResponse(String url, Class clazz, Map<String, String> map, MyCallBack myCallBack);
    void setGetResponse(String url, Class clazz, MyCallBack myCallBack);
    void setPutResponse(String url, Class clazz, Map<String, String> map, MyCallBack myCallBack);
    void setDelResponse(String url, Class clazz, MyCallBack myCallBack);
    void getimgtitle(String url, Map<String, Object> map, List<Object> list, Class clazz, MyCallBack myCallback);
}
