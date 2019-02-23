package com.bw.movie.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
 * function:抽取Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IView{

    private IPresenter mIPresenter;
    private Loading_view loading;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getContent());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mIPresenter = new IPresenter(this);
        initView();
        initData();

    }

    //吐司基类
   /* public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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
        if(loading!=null){
            loading.dismiss();
        }
    }


    //抽取IView失败方法
    @Override
    public void failed(String error) {

        fail(error);
        if(loading!=null){
            loading.dismiss();
        }
        if(alertDialog==null){
            NetWorkUtil.setNetworkMethod(this);
        }else{
            alertDialog.dismiss();
            alertDialog=null;
            NetWorkUtil.setNetworkMethod(this);
            }
          }

    //沉浸式状态栏
    public void windowManger(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

    public void setGet(String url,Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        if(loading==null){
            loading = new Loading_view(this, R.style.CustomDialog);
            loading.show();
        }
        mIPresenter.setGetRequest(url,clazz);

    }

    public void setPost(String url, Class clazz, Map<String,String> map){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        if(loading==null){
            loading = new Loading_view(this, R.style.CustomDialog);
            loading.show();
        }

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
        if(loading!=null){
            loading.dismiss();
        }
        }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (BaseActivity.this.getCurrentFocus() != null) {
                if (BaseActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
