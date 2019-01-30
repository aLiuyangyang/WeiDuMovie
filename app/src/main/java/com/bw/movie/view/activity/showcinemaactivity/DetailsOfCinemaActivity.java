package com.bw.movie.view.activity.showcinemaactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Banner_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_Coming_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.CinemaDetailsBean;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.ImageViewAnimationHelper;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * date:2019/1/27
 * author:刘洋洋(DELL)
 * function: 影院详情
 */
public class DetailsOfCinemaActivity extends BaseActivity {
    @BindView(R.id.details_image)
    SimpleDraweeView detailsImage;
    @BindView(R.id.details_name)
    TextView detailsName;
    @BindView(R.id.details_addr)
    TextView detailsAddr;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.cinema_recyc_flow)
    RecyclerCoverFlow cinemaRecycFlow;
    @BindView(R.id.li4)
    LinearLayout li4;
    @BindView(R.id.checked_layout)
    LinearLayout checkedLayout;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    private int id,page=1,count=10;
    private ShowFilm_Coming_Adapter showFilm_coming_adapter;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        //轮播
        setGet(String.format(Constant.Banner_Path, page, count), ShowFile_Banner_Info.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        setGet(String.format(Constant.DetailsOfCinema_Path, id), CinemaDetailsBean.class);

    }

    @Override
    public int getContent() {
        return R.layout.activity_details_of_cinema;
    }

    @Override
    public void success(Object data) {
        if (data instanceof CinemaDetailsBean) {
            CinemaDetailsBean cinemaDetailsBean = (CinemaDetailsBean) data;
            if (cinemaDetailsBean.getStatus().equals("0000")) {
                detailsName.setText(cinemaDetailsBean.getResult().getName());
                detailsAddr.setText(cinemaDetailsBean.getResult().getAddress());
                Uri parse = Uri.parse(cinemaDetailsBean.getResult().getLogo());
                detailsImage.setImageURI(parse);
            }
        } else if (data instanceof ShowFile_Banner_Info) {
            //即将上映
            ShowFile_Banner_Info showFile_banner_info = (ShowFile_Banner_Info) data;
            ShowFile_Banner_Adapter showFile_banner_adapter = new ShowFile_Banner_Adapter(this, showFile_banner_info.getResult());
            cinemaRecycFlow.setAdapter(showFile_banner_adapter);
            cinemaRecycFlow.smoothScrollToPosition(4);
            final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(this, checkedLayout, 2, 30);
            cinemaRecycFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                @Override
                public void onItemSelected(int position) {
                    imageViewAnimationHelper.startAnimation(position);
                }
            });
        }
    }

    @Override
    public void fail(String error) {

    }
}
