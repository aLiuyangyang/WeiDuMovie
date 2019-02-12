package com.bw.movie.view.activity.showcinemaactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Banner_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Schedule_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_Coming_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.CinemaDetailsBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.MovieScheduleBean;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.ImageViewAnimationHelper;
import com.bw.movie.view.activity.AreaActivity;
import com.bw.movie.view.activity.showfileactivity.CooseClassActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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
    @BindView(R.id.ditus)
    ImageView ditus;
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
    @BindView(R.id.detailsOf_recy)
    RecyclerView detailsOfRecy;
    private int id, page = 1, count = 10;
    private ShowFilm_Coming_Adapter showFilm_coming_adapter;
    private ShowFile_Schedule_Adapter showFile_schedule_adapter;
    private int cinemaid;
    private int id1;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void message(EventBusMessage eventBusMessage) {
        id1 = eventBusMessage.getId();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        detailsOfRecy.setLayoutManager(linearLayoutManager);
        showFile_schedule_adapter = new ShowFile_Schedule_Adapter(this);
        detailsOfRecy.setAdapter(showFile_schedule_adapter);
        setGet(String.format(Constant.ChooseClass_Path, cinemaid, id1), MovieScheduleBean.class);
        ditus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsOfCinemaActivity.this,AreaActivity.class));
            }
        });
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
                cinemaid = cinemaDetailsBean.getResult().getId();
                Uri parse = Uri.parse(cinemaDetailsBean.getResult().getLogo());
                detailsImage.setImageURI(parse);
            }
        } else if (data instanceof ShowFile_Banner_Info) {
            //即将上映
            ShowFile_Banner_Info showFile_banner_info = (ShowFile_Banner_Info) data;
            List<ShowFile_Banner_Info.ResultBean> result = showFile_banner_info.getResult();
            ShowFile_Banner_Adapter showFile_banner_adapter = new ShowFile_Banner_Adapter(this,result );
            cinemaRecycFlow.setAdapter(showFile_banner_adapter);
            cinemaRecycFlow.smoothScrollToPosition(1);
            final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(this, checkedLayout, 2, 30);
            cinemaRecycFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                @Override
                public void onItemSelected(int position) {
                   /* int flag  = position%result.size();
                    id1 = result.get(flag).getId();
                    initTimeListLayout();*/
                    imageViewAnimationHelper.startAnimation(position);
                    setGet(String.format(Constant.ChooseClass_Path, cinemaid, id1), MovieScheduleBean.class);
                }
            });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().isRegistered(this);
    }
}
