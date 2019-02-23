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
import com.bw.movie.bean.MinecinemaBean;
import com.bw.movie.view.activity.showcinemaactivity.DetailsOfCinemaActivity;
import com.bw.movie.view.activity.showfileactivity.DetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/2/14
 * author:刘洋洋(DELL)
 * function:
 */
public class MineCareRecyAdapter extends RecyclerView.Adapter<MineCareRecyAdapter.ViewHolder> {

    private List<MinecinemaBean.ResultBean> list;
    private Context context;

    public MineCareRecyAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<MinecinemaBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_care_cinema, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri parse = Uri.parse(list.get(i).getLogo());
        viewHolder.careCinemaSim.setImageURI(parse);
        viewHolder.careCinemaName.setText(list.get(i).getName());
        viewHolder.careCinemaBrief.setText(list.get(i).getAddress());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailsOfCinemaActivity.class);
                intent.putExtra("id",list.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.care_cinema_sim)
        SimpleDraweeView careCinemaSim;
        @BindView(R.id.care_cinema_name)
        TextView careCinemaName;
        @BindView(R.id.care_cinema_brief)
        TextView careCinemaBrief;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
