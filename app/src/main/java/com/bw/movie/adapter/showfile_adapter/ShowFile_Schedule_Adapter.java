package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieScheduleBean;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.schedulePlayHall.setText(list.get(i).getScreeningHall());
        viewHolder.scheduleTimeEnd.setText(list.get(i).getEndTime());
        viewHolder.scheduleTimeStart.setText(list.get(i).getBeginTime());
        viewHolder.scheduleTimePrice.setText(list.get(i).getPrice() + "");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnclickId!=null){
                    mOnclickId.successed(list.get(i).getId(),list.get(i).getBeginTime(),
                            list.get(i).getEndTime(),list.get(i).getScreeningHall(),
                            list.get(i).getPrice()
                    );
                }
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

    public interface OnclickId{
        void successed(int id, String scheduleTimeStart, String scheduleTimeEnd, String schedulePlayHall, double price);
    }

    private OnclickId mOnclickId;

    public void setOnclickId(OnclickId onclickId) {
        mOnclickId = onclickId;
    }
}
