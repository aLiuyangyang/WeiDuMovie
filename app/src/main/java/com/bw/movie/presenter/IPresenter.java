package com.bw.movie.presenter;


import com.bw.movie.model.IModel;
import com.bw.movie.mycallback.MyCallBack;
import com.bw.movie.view.IView;

import java.util.List;
import java.util.Map;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public class IPresenter implements IP{

    IView mIView;
    IModel mIModel;

    public IPresenter(IView IView) {
        mIView = IView;
        mIModel=new IModel();
    }

    @Override
    public void setRequest(String url, Class clazz, Map<String, String> map) {
        mIModel.setResponse(url, clazz, map, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.successed(data);
            }

            @Override
            public void setfail(String error) {
                mIView.failed(error);
            }
        });
    }

    @Override
    public void setGetRequest(String url, Class clazz) {
        mIModel.setGetResponse(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.successed(data);
            }

            @Override
            public void setfail(String error) {
                mIView.failed(error);
            }
        });
    }

    @Override
    public void setPutRequest(String url, Class clazz, Map<String, String> map) {
        mIModel.setPutResponse(url, clazz, map, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.successed(data);
            }

            @Override
            public void setfail(String error) {
                mIView.failed(error);
            }
        });
    }

    @Override
    public void setdelRequest(String url, Class clazz) {
        mIModel.setDelResponse(url, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.successed(data);
            }

            @Override
            public void setfail(String error) {
                mIView.failed(error);
            }
        });
    }

    @Override
    public void getRequestimgtitle(String url, Map<String, Object> map, List<Object> list, Class clazz) {
        mIModel.getimgtitle(url, map, list, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIView.successed(data);
            }

            @Override
            public void setfail(String error) {
                mIView.failed(error);
            }
        });

    }

    public void onDistouch(){
        if(mIModel!=null){
            mIModel=null;
        }
        if(mIView!=null){
            mIView=null;
        }
    }

}
