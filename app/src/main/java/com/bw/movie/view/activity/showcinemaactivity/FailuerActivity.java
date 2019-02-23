package com.bw.movie.view.activity.showcinemaactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.view.activity.showmineactivity.OpinSuccessActivity;
import com.bw.movie.view.activity.showmineactivity.TicketActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FailuerActivity extends AppCompatActivity {

    @BindView(R.id.fail_go_page)
    Button failGoPage;
    @BindView(R.id.fail_go_obli)
    Button failGoObli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feilure);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fail_go_page, R.id.fail_go_obli})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fail_go_page:
                finish();
                break;
            case R.id.fail_go_obli:
                Intent intent=new Intent(this,TicketActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
