package com.bw.movie.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void initView() {
        windowManger();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContent() {
        return R.layout.activity_main;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }

}
