package com.bw.movie.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.utils.Constant;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.movie_details_home_xin)
    ImageView movieDetailsHomeXin;
    @BindView(R.id.linearlayout1)
    LinearLayout linearlayout1;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.details_pic)
    SimpleDraweeView detailsPic;
    @BindView(R.id.details_background)
    SimpleDraweeView detailsBackground;
    @BindView(R.id.film_details_home_details)
    Button filmDetailsHomeDetails;
    @BindView(R.id.film_details_home_foreshow)
    Button filmDetailsHomeForeshow;
    @BindView(R.id.film_details_home_still)
    Button filmDetailsHomeStill;
    @BindView(R.id.film_details_home_film_review)
    Button filmDetailsHomeFilmReview;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.film_details_buy_ticket)
    Button filmDetailsBuyTicket;
    private int movieId;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }
    @Override
    public void initData() {
        setGet(String.format(Constant.Details_Path,movieId),Details_Info.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void message(EventBusMessage eventBusMessage) {
        movieId = eventBusMessage.getId();
    }

    @Override
    public int getContent() {
        return R.layout.activity_details;
    }

    @Override
    public void success(Object data) {
        Details_Info details_info= (Details_Info) data;
        detailsTitle.setText(details_info.getResult().getName());
        detailsPic.setImageURI(details_info.getResult().getImageUrl());
        detailsBackground.setImageURI(details_info.getResult().getImageUrl());


    }

    @Override
    public void fail(String error) {

    }

}
