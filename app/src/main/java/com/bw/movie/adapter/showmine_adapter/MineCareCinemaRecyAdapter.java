package com.bw.movie.adapter.showmine_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.MovieCommentDetailsBean;
import com.bw.movie.bean.MoviewPageListBean;
import com.bw.movie.view.activity.showfileactivity.DetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/2/14
 * author:刘洋洋(DELL)
 * function:
 */
public class MineCareCinemaRecyAdapter extends RecyclerView.Adapter<MineCareCinemaRecyAdapter.ViewHolder> {

    private List<MoviewPageListBean.ResultBean> list;
    private Context context;

    public MineCareCinemaRecyAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<MoviewPageListBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_filmcare, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String date = new SimpleDateFormat("yyyy-MM-dd")
                .format(new java.util.Date(list.get(i).getReleaseTime()));
        viewHolder.careFilmTime.setText(date+"");
        Uri parse = Uri.parse(list.get(i).getImageUrl());
        viewHolder.careFilmSim.setImageURI(parse);
        viewHolder.careFilmName.setText(list.get(i).getName());
        viewHolder.careFilmBrief.setText(list.get(i).getSummary());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
                EventBus.getDefault().postSticky(new EventBusMessage(list.get(i).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.care_film_sim)
        SimpleDraweeView careFilmSim;
        @BindView(R.id.care_film_name)
        TextView careFilmName;
        @BindView(R.id.care_film_brief)
        TextView careFilmBrief;
        @BindView(R.id.care_film_time)
        TextView careFilmTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
