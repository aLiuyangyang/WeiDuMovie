package com.bw.movie.presenter;

import java.util.List;
import java.util.Map;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public interface IP {

    void setRequest(String url, Class clazz, Map<String, String> map);

    void setGetRequest(String url, Class clazz);

    void setPutRequest(String url, Class clazz, Map<String, String> map);

    void setdelRequest(String url, Class clazz);

    void getRequestimgtitle(String url, Map<String, Object> map, List<Object> list, Class clazz);
    void setRequestHead(String path, Map<String,String> map,Class clazz);
}
