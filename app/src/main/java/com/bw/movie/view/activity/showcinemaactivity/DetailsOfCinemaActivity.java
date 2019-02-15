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
import com.bw.movie.adapter.showfile_adapter.Camera_Banner_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Banner_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Schedule_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_Coming_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.Camera_BannerBean;
import com.bw.movie.bean.CinemaDetailsBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.MovieScheduleBean;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.ImageViewAnimationHelper;
import com.bw.movie.view.activity.AreaActivity;
import com.bw.movie.view.activity.showfileactivity.ChoseseatActivity;
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
    @BindView(R.id.detailsOf_recy)
    RecyclerView detailsOfRecy;
    private int id, page = 1, count = 10;
    private ShowFilm_Coming_Adapter showFilm_coming_adapter;
    private ShowFile_Schedule_Adapter showFile_schedule_adapter;
    private int cinemaid;
    private int id1;
    private Camera_Banner_Adapter mCamera_banner_adapter;
    private String mName;
    private String mAddress;
    private String mName1;
    private Camera_BannerBean mCamera_bannerBean;

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
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        setGet(String.format(Constant.Camera_Banner,id), Camera_BannerBean.class);

        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        detailsOfRecy.setLayoutManager(linearLayoutManager);
        showFile_schedule_adapter = new ShowFile_Schedule_Adapter(this);

        showFile_schedule_adapter.setOnclickId(new ShowFile_Schedule_Adapter.OnclickId() {
            @Override
            public void successed(int id, String scheduleTimeStart, String scheduleTimeEnd, String schedulePlayHall, double price) {
                Intent intent1=new Intent(DetailsOfCinemaActivity.this,ChoseseatActivity.class);
                intent1.putExtra("movieId",id);
                intent1.putExtra("cinemasId",cinemaid);
                intent1.putExtra("Id",id);
                intent1.putExtra("name",mName);
                intent1.putExtra("scheduleTimeStart",scheduleTimeStart);
                intent1.putExtra("scheduleTimeEnd",scheduleTimeEnd);
                intent1.putExtra("schedulePlayHall",schedulePlayHall);
                intent1.putExtra("address",mAddress);
                intent1.putExtra("resultName",mName1);
                intent1.putExtra("price",price);
                startActivity(intent1);
            }

        });
        detailsOfRecy.setAdapter(showFile_schedule_adapter);

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
                mName = cinemaDetailsBean.getResult().getName();
                mAddress = cinemaDetailsBean.getResult().getAddress();
                detailsName.setText(cinemaDetailsBean.getResult().getName());
                detailsAddr.setText(cinemaDetailsBean.getResult().getAddress());
                cinemaid = cinemaDetailsBean.getResult().getId();
                Uri parse = Uri.parse(cinemaDetailsBean.getResult().getLogo());
                detailsImage.setImageURI(parse);
            }
            int getmovied = mCamera_banner_adapter.getmovied(0);
            setGet(String.format(Constant.ChooseClass_Path, cinemaid,getmovied), MovieScheduleBean.class);
        } else if (data instanceof Camera_BannerBean) {
            mCamera_bannerBean = (Camera_BannerBean) data;
            List<Camera_BannerBean.ResultBean> result = mCamera_bannerBean.getResult();
            showLog(result.get(0).getName());
            mCamera_banner_adapter = new Camera_Banner_Adapter(this);
            mCamera_banner_adapter.setList(result);

            cinemaRecycFlow.setAdapter(mCamera_banner_adapter);
            cinemaRecycFlow.smoothScrollToPosition(0);
            cinemaRecycFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                @Override
                public void onItemSelected(int position) {

                    int getmovied = mCamera_banner_adapter.getmovied(position);

                    setGet(String.format(Constant.ChooseClass_Path, cinemaid,getmovied), MovieScheduleBean.class);
                    mName1 = mCamera_bannerBean.getResult().get(position).getName();
                }
            });
            setGet(String.format(Constant.DetailsOfCinema_Path,id), CinemaDetailsBean.class);
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
