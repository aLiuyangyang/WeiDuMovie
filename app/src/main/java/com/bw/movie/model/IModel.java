package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.mycallback.MyCallBack;
import com.bw.movie.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public class IModel implements IM{

    @Override
    public void setResponse(String url, final Class clazz, Map<String, String> map, final MyCallBack myCallBack) {


        RetrofitManager.getInstance().post(url,map,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String error) {
                if(myCallBack!=null){
                    myCallBack.setfail(error);
                }
            }
        });
    }

    @Override
    public void setGetResponse(String url, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String data) {

            }
        });
    }



    @Override
    public void setPutResponse(String url, final Class clazz, Map<String, String> map, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().put(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data,clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String data) {

            }
        });
    }

    @Override
    public void setDelResponse(String url, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().delete(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallBack.setData(o);
            }

            @Override
            public void onFail(String data) {

            }
        });
    }

    @Override
    public void getimgtitle(String url, Map<String, Object> map, List<Object> list, final Class clazz, final MyCallBack myCallback) {
        RetrofitManager.getInstance().postFormBodyObject(url, map, list, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object obj=new Gson().fromJson(data,clazz);
                    if(myCallback!=null){
                        myCallback.setData(obj);
                    }
                }catch (Exception e){
                    Log.d("sjx","111"+e);
                }
            }

            @Override
            public void onFail(String data) {
                Log.d("sjx",""+data);
            }
        });

    }
}
