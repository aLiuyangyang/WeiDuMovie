package com.bw.movie.view.activity.logandregactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:
 */
public class StartActivity extends AppCompatActivity {
    int i=2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int t=msg.what;
            if (t==0){
                Intent intent=new Intent(StartActivity.this,GuideActivity.class);
                startActivity(intent);
                finish();
            }
            }
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (i > 0) {
                    i--;
                    try {
                        sleep(1000);
                        handler.sendEmptyMessage(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
