package com.bw.movie.view.activity.showmineactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/2/14
 * author:孙佳鑫(孙佳鑫)
 * function:意见反馈
 */
public class OpinSuccessActivity extends AppCompatActivity {
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opin_success);
        ButterKnife.bind(this);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
