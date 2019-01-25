package com.bw.movie.view;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:View层
 */
public interface IView<T> {
    void successed(T data);
    void failed(String error);
}
