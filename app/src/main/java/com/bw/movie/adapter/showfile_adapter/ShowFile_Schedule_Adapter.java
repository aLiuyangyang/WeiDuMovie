package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieScheduleBean;
import com.bw.movie.view.activity.showfileactivity.ChoseseatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/28
 * author:刘洋洋(DELL)
 * function:
 */
public class ShowFile_Schedule_Adapter extends RecyclerView.Adapter<ShowFile_Schedule_Adapter.ViewHolder> {


    private List<MovieScheduleBean.ResultBean> list;
    private Context context;

    public ShowFile_Schedule_Adapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<MovieScheduleBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_schedule, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.schedulePlayHall.setText(list.get(i).getScreeningHall());
        viewHolder.scheduleTimeEnd.setText(list.get(i).getEndTime());
        viewHolder.scheduleTimeStart.setText(list.get(i).getBeginTime());
        viewHolder.scheduleTimePrice.setText(list.get(i).getPrice() + "");
        viewHolder.choseseat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ChoseseatActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.schedule_Play_Hall)
        TextView schedulePlayHall;
        @BindView(R.id.schedule_Time_Start)
        TextView scheduleTimeStart;
        @BindView(R.id.schedule_Time_End)
        TextView scheduleTimeEnd;
        @BindView(R.id.schedule_Time_Price)
        TextView scheduleTimePrice;
        @BindView(R.id.choseseat)
        ImageView choseseat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
