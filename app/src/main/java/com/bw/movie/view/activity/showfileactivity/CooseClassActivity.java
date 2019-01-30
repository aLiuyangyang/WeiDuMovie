package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Schedule_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.bean.MovieScheduleBean;
import com.bw.movie.utils.Constant;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * date:2019/1/27
 * author:刘洋洋(DELL)
 * function: 根据电影ID和影院ID查询电影排期列表
 */
public class CooseClassActivity extends BaseActivity {
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.cinema_class_name)
    TextView cinemaClassName;
    @BindView(R.id.cinema_class_addr)
    TextView cinemaClassAddr;
    @BindView(R.id.film_class_image)
    SimpleDraweeView filmClassImage;
    @BindView(R.id.film_class_name)
    TextView filmClassName;
    @BindView(R.id.film_class_type)
    TextView filmClassType;
    @BindView(R.id.film_class_director)
    TextView filmClassDirector;
    @BindView(R.id.film_class_duration)
    TextView filmClassDuration;
    @BindView(R.id.film_class_addr)
    TextView filmClassAddr;
    @BindView(R.id.schedule_recy)
    RecyclerView scheduleRecy;
    private int movieId;//影片id
    private int cinemasId;//影院id
    private String name, address;
    private ShowFile_Schedule_Adapter showFile_schedule_adapter;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        movieId = intent.getIntExtra("movieId", 0);
        cinemasId = intent.getIntExtra("cinemasId", 0);
        cinemaClassName.setText(name);
        cinemaClassAddr.setText(address);
        setGet(String.format(Constant.Details_Path, movieId), Details_Info.class);
        //根据电影ID和影院ID查询电影排期列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        scheduleRecy.setLayoutManager(linearLayoutManager);
        showFile_schedule_adapter=new ShowFile_Schedule_Adapter(this);
        scheduleRecy.setAdapter(showFile_schedule_adapter);
        setGet(String.format(Constant.ChooseClass_Path,cinemasId,movieId),MovieScheduleBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.activity_coose_class;
    }

    @Override
    public void success(Object data) {
        if (data instanceof Details_Info) {
            Details_Info moviesByIdBean = (Details_Info) data;
            if (moviesByIdBean.getStatus().equals("0000")) {
                Details_Info.ResultBean result = moviesByIdBean.getResult();
                filmClassName.setText(result.getName());
                filmClassType.setText("类型:" + result.getMovieTypes());
                filmClassDirector.setText("导演： " + result.getDirector());
                filmClassDuration.setText("时长： " + result.getDuration());
                filmClassAddr.setText("产地： " + result.getPlaceOrigin());
                Uri parse = Uri.parse(result.getImageUrl());
                filmClassImage.setImageURI(parse);
            }
        }else if (data instanceof MovieScheduleBean){
            MovieScheduleBean movieScheduleBean= (MovieScheduleBean) data;
            if (movieScheduleBean.getStatus().equals("0000")){
                showFile_schedule_adapter.setList(movieScheduleBean.getResult());
            }
        }
    }

    @Override
    public void fail(String error) {

    }

}
