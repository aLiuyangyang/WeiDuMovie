package com.bw.movie.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.Loading_view;
import com.bw.movie.utils.NetworkUtil;
import com.bw.movie.view.IView;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/1/23
 * author:孙佳鑫(孙佳鑫)
 * function:抽取Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IView{

    private IPresenter mIPresenter;
    private Loading_view loading;
    private int mNetworkType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContent());
        mIPresenter = new IPresenter(this);
        initView();
        initData();

    }

    //吐司基类
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //log日志
    public void showLog(String msg){
        Log.e("sjx",msg);
    }

    public abstract void initView();
    public abstract void initData();
    public abstract int getContent();

    //抽取IView成功方法
    @Override
    public void successed(Object data) {

        success(data);
        loading.dismiss();
    }


    //抽取IView失败方法
    @Override
    public void failed(String error) {

        fail(error);
        loading.dismiss();
        Toast.makeText(this, "没网了"+mNetworkType, Toast.LENGTH_SHORT).show();
    }

    //沉浸式状态栏
    public void windowManger() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    public void setGet(String url,Class clazz){

        mIPresenter.setGetRequest(url,clazz);
    }
    public  void NetWork(Context context){
        mNetworkType = NetworkUtil.getNetworkType(context);

    }

    public void setPost(String url, Class clazz, Map<String,String> map){

        loading = new Loading_view(this, R.style.CustomDialog);
        loading.show();

        if (map==null){
            map=new HashMap<>();
        }
        mIPresenter.setRequest(url,clazz,map);
    }


    //提供给activity的成功方法
    public abstract void success(Object data);

    //提供给activity的失败方法
    public abstract void fail(String error);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIPresenter!=null){
            mIPresenter.onDistouch();
        }
    }
}
