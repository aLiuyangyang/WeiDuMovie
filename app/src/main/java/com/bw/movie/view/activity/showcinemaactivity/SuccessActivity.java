package com.bw.movie.view.activity.showcinemaactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.view.activity.showmineactivity.TicketActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends AppCompatActivity {

    @BindView(R.id.success_go_page)
    Button successGoPage;
    @BindView(R.id.success_go_obli)
    Button successGoObli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.success_go_page, R.id.success_go_obli})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.success_go_page:
                finish();
                break;
            case R.id.success_go_obli:
                Intent intent=new Intent(this,TicketActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
