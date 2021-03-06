package com.bw.movie.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Loading_view;
import com.bw.movie.utils.MyApp;
import com.bw.movie.utils.NetWorkUtil;
import com.bw.movie.view.IView;

import java.util.HashMap;
import java.util.Map;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:抽取fragment基类
 */
public abstract class BaseFragment extends Fragment implements IView {

    private IPresenter mIPresenter;
    private Loading_view loading;
    private AlertDialog alertDialog;
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
    /*public void showToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }*/
    private static Toast toast;//在类前面声明吐司，确保在这个页面只有一个吐司

    //需要谈吐司的地方调用showToast()

    public static void showToast(Context context, String msg) {



        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }

        toast.show();//重新显示吐司
    }




    //log日志
    public void showLog(String msg) {
        Log.e("sjx", msg);
    }

    public abstract void initView(View view);

    public abstract void initData(View view);

    public abstract int getContent();

    @Override
    public void successed(Object data) {
        if (loading != null) {
            loading.dismiss();
        }
        success(data);
    }

    @Override
    public void failed(String error) {
        if (loading != null) {
            loading.dismiss();
        }
        fail(error);
        if(alertDialog==null){
            NetWorkUtil.setNetworkMethod(getActivity());
        }else{
            alertDialog.dismiss();
            alertDialog=null;
            NetWorkUtil.setNetworkMethod(getActivity());
        }
    }

    public void setGet(String url, Class clazz) {
        if (!(NetWorkUtil.isConn(getActivity()))) {
            NetWorkUtil.setNetworkMethod(getActivity());
            return;
        }
        if (loading == null) {
            loading = new Loading_view(getContext(), R.style.CustomDialog);
            loading.show();
        }
        mIPresenter.setGetRequest(url, clazz);
    }

    public void setPost(String url, Class clazz, Map<String, String> map) {
        if (!(NetWorkUtil.isConn(getActivity()))) {
            NetWorkUtil.setNetworkMethod(getActivity());
            return;
        }
        if (loading == null) {
            loading = new Loading_view(getContext(), R.style.CustomDialog);
            loading.show();
        }
        mIPresenter.setRequest(url, clazz, map);
    }

    //提供给activity的成功方法
    public abstract void success(Object data);

    //提供给activity的失败方法
    public abstract void fail(String error);


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIPresenter != null) {
            mIPresenter.onDistouch();
        }
        if (loading != null) {
            loading.dismiss();
        }
        /*RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
    }


}
