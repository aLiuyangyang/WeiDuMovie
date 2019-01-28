package com.bw.movie.view.activity.showcinemaactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.CinemaDetailsBean;
import com.bw.movie.utils.Constant;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/27
 * author:刘洋洋(DELL)
 * function:
 */
public class DetailsOfCinemaActivity extends BaseActivity {
    @BindView(R.id.details_image)
    SimpleDraweeView detailsImage;
    @BindView(R.id.details_name)
    TextView detailsName;
    @BindView(R.id.details_addr)
    TextView detailsAddr;
    private int id;

    @Override
    public void initView() {
    ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        setGet(String.format(Constant.DetailsOfCinema_Path,id),CinemaDetailsBean.class);

    }

    @Override
    public int getContent() {
        return R.layout.activity_details_of_cinema;
    }

    @Override
    public void success(Object data) {
       if (data instanceof CinemaDetailsBean){
           CinemaDetailsBean cinemaDetailsBean= (CinemaDetailsBean) data;
           if (cinemaDetailsBean.getStatus().equals("0000")){
               detailsName.setText(cinemaDetailsBean.getResult().getName());
               detailsAddr.setText(cinemaDetailsBean.getResult().getAddress());
               Uri parse = Uri.parse(cinemaDetailsBean.getResult().getLogo());
               detailsImage.setImageURI(parse);
           }
       }
    }

    @Override
    public void fail(String error) {

    }

}
