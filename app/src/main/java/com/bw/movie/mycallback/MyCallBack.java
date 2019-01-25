package com.bw.movie.mycallback;

/**
 * date:2018/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public interface MyCallBack<T> {

    void setData(T data);
    void setfail(String error);
}
