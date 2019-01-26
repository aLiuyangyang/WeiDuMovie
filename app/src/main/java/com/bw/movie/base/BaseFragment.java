package com.bw.movie.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Loading_view;
import com.bw.movie.view.IView;

import java.util.Map;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:抽取fragment基类
 * GitHub  Token值:
 * d1e28b84e2517ab35c6101a5f0eba1f852b57010
 */
public abstract class BaseFragment extends Fragment implements IView {

    private IPresenter mIPresenter;
    private Loading_view loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContent(), container, false);
        mIPresenter = new IPresenter(this);
        initView(view);
        initData(view);
        return view;
    }

    //吐司基类
    public void showToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

    //log日志
    public void showLog(String msg){
        Log.e("sjx",msg);
    }

    public abstract void initView(View view);
    public abstract void initData(View view);
    public abstract int getContent();

    @Override
    public void successed(Object data) {
        success(data);
    }

    @Override
    public void failed(String error) {
        fail(error);
    }

    public void setGet(String url,Class clazz){
       /* loading = new Loading_view(getContext(), R.style.CustomDialog);
        loading.show();
        new Handler().postDelayed(new Runnable() {//定义延时任务模仿网络请求
            @Override
            public void run() {
                loading.dismiss();//3秒后调用关闭加载的方法
            }
        }, 3000);*/
        mIPresenter.setGetRequest(url,clazz);
    }

    public void setPost(String url, Class clazz, Map<String,String> map){
        mIPresenter.setRequest(url,clazz,map);
    }

    //提供给activity的成功方法
    public abstract void success(Object data);

    //提供给activity的失败方法
    public abstract void fail(String error);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIPresenter!=null){
            mIPresenter.onDistouch();
        }
    }
}
