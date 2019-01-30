package com.bw.movie.view.activity.showfileactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.ShowFile_ChooseCinema_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.ChooseCinemaBean;
import com.bw.movie.utils.Constant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * date:2019/1/27
 * author:刘洋洋(DELL)
 * function: 选择影院
 */
public class ChooseCinemaActivity extends BaseActivity {
    @BindView(R.id.mCinema_name)
    TextView mCinemaName;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.mCinema_recy)
    RecyclerView mCinemarecy;
    private ShowFile_ChooseCinema_Adapter showFile_chooseCinema_adapter;
    private int movieId;
    private String name;

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
        final Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId",0);
        name = intent.getStringExtra("name");
        mCinemaName.setText(name);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mCinemarecy.setLayoutManager(linearLayoutManager);
        showFile_chooseCinema_adapter=new ShowFile_ChooseCinema_Adapter(this);
        mCinemarecy.setAdapter(showFile_chooseCinema_adapter);
        setGet(String.format(Constant.ChooseCinema_Path,movieId),ChooseCinemaBean.class);

        showFile_chooseCinema_adapter.setmOnclick(new ShowFile_ChooseCinema_Adapter.Onclick() {
            @Override
            public void success(int id,String adress,String name) {
                Intent intent1=new Intent(ChooseCinemaActivity.this,CooseClassActivity.class);
                intent1.putExtra("cinemasId",id);
                intent1.putExtra("movieId",movieId);
                intent1.putExtra("address",adress);
                intent1.putExtra("name",name);
                startActivity(intent1);
            }
        });


    }

    @Override
    public int getContent() {
        return R.layout.activity_choose_cinema;
    }

    @Override
    public void success(Object data) {
      if (data instanceof ChooseCinemaBean){
          ChooseCinemaBean chooseCinemaBean= (ChooseCinemaBean) data;
          if (chooseCinemaBean.getStatus().equals("0000")){
              showFile_chooseCinema_adapter.setList(chooseCinemaBean.getResult());
          }
      }
    }

    @Override
    public void fail(String error) {

    }
}
