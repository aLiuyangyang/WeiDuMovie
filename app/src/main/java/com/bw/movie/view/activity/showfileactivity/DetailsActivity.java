package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.AttentionBean;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.fragment.showfilebtnpopupwindow.PopuWindowDetails;
import com.bw.movie.view.fragment.showfilebtnpopupwindow.PopuWindowFileReview;
import com.bw.movie.view.fragment.showfilebtnpopupwindow.PopuWindowForeshow;
import com.bw.movie.view.fragment.showfilebtnpopupwindow.PopuWindowHomeStill;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * date:2019/1/26
 * author:孙佳鑫(DELL)
 * function: 详情
 */
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
    @BindView(R.id.li4)
    LinearLayout li4;
    private int movieId;
    private Details_Info.ResultBean mResult;
    private String name;
    private int b;
    private int id;
    private Details_Info details_info;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        setGet(String.format(Constant.Details_Path, movieId), Details_Info.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //详情按钮
        filmDetailsHomeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopuWindowDetails popuWindowDetails = new PopuWindowDetails(DetailsActivity.this, mResult);
                popuWindowDetails.bottomwindow(linearlayout1);
            }
        });
        //预告片
        filmDetailsHomeForeshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopuWindowForeshow popuWindowForeshow = new PopuWindowForeshow(DetailsActivity.this, mResult);
                popuWindowForeshow.bottomwindow(linearlayout1);
            }
        });
        //剧照
        filmDetailsHomeStill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopuWindowHomeStill popuWindowHomeStill = new PopuWindowHomeStill(DetailsActivity.this,mResult);
                popuWindowHomeStill.bottomwindow(linearlayout1);
            }
        });
        //影评
        filmDetailsHomeFilmReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopuWindowFileReview popuWindowFileReview = new PopuWindowFileReview(DetailsActivity.this);
                popuWindowFileReview.bottomwindow(linearlayout1);
            }
        });
        movieDetailsHomeXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b==1){//取消
                    setGet(String.format(Constant.Unfollow_Path,id),AttentionBean.class);
                    details_info.getResult().setFollowMovie(2);
                }else if(b == 2){//关注
                    setGet(String.format(Constant.Attention_Path,id),AttentionBean.class);
                    details_info.getResult().setFollowMovie(1);
                }
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
        if (data instanceof Details_Info) {
            details_info = (Details_Info) data;
            id = details_info.getResult().getId();
            mResult = details_info.getResult();
            name = details_info.getResult().getName();
            detailsTitle.setText(name);
            detailsPic.setImageURI(details_info.getResult().getImageUrl());
            detailsBackground.setImageURI(details_info.getResult().getImageUrl());
            if (details_info.getResult().getFollowMovie() == 1) {
                movieDetailsHomeXin.setImageResource(R.mipmap.com_icon_collection_selected);
            } else {
                movieDetailsHomeXin.setImageResource(R.mipmap.com_icon_collection_default);
            }
        }else if (data instanceof AttentionBean){
            AttentionBean attentionBean= (AttentionBean) data;
            if (attentionBean.getStatus().equals("0000")){
                showToast(attentionBean.getMessage());
            }else {
                showToast(attentionBean.getMessage());
                setGet(String.format(Constant.Details_Path, movieId), Details_Info.class);
            }
        }
    }

    @Override
    public void fail(String error) {

    }
    @OnClick(R.id.film_details_buy_ticket)
    public void onViewClicked() {
        Intent intent=new Intent(this,ChooseCinemaActivity.class);
        intent.putExtra("movieId",movieId);
        intent.putExtra("name",name);
        startActivity(intent);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().isRegistered(this);
    }
}
