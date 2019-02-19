package com.bw.movie.adapter.showmine_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.ObligationBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/2/15
 * author:刘洋洋(DELL)
 * function:
 */
public class MineStocksRecyAdapter extends RecyclerView.Adapter<MineStocksRecyAdapter.ViewHolder> {

    private List<ObligationBean.ResultBean> list;
    private Context context;

    public MineStocksRecyAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<ObligationBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_stocks, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mineStarttiems.setText(list.get(i).getBeginTime()+"");
        viewHolder.mineStackEndTime.setText(list.get(i).getEndTime()+"");
        viewHolder.mineStackName.setText(list.get(i).getMovieName());
        viewHolder.mineStackCode.setText("订单号: "+list.get(i).getOrderId());
        viewHolder.mineStackMoney.setText("金额: "+list.get(i).getPrice()+"");
        viewHolder.mineStackMoviehall.setText("影厅: "+list.get(i).getScreeningHall());
        viewHolder.mineStackNum.setText("数量: "+list.get(i).getAmount()+"");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list.get(i).getCreateTime()));
        viewHolder.mineStackTime.setText("下单时间: "+date);
        viewHolder.mineStackCinema.setText("影院: "+list.get(i).getCinemaName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mine_stack_name)
        TextView mineStackName;
        @BindView(R.id.mine_stack_StartTiem)
        TextView mineStackStartTiem;
        @BindView(R.id.mine_stack_EndTime)
        TextView mineStackEndTime;
        @BindView(R.id.mine_starttiems)
        TextView mineStarttiems;
        @BindView(R.id.mine_stack_Code)
        TextView mineStackCode;
        @BindView(R.id.mine_stack_Cinema)
        TextView mineStackCinema;
        @BindView(R.id.mine_stack_moviehall)
        TextView mineStackMoviehall;
        @BindView(R.id.mine_stack_time)
        TextView mineStackTime;
        @BindView(R.id.mine_stack_num)
        TextView mineStackNum;
        @BindView(R.id.mine_stack_money)
        TextView mineStackMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
