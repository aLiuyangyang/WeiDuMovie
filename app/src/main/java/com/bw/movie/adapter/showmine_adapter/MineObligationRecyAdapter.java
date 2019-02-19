package com.bw.movie.adapter.showmine_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class MineObligationRecyAdapter extends RecyclerView.Adapter<MineObligationRecyAdapter.ViewHolder> {

    private List<ObligationBean.ResultBean> list;
    private Context context;

    public MineObligationRecyAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_obligation, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ObligationBean.ResultBean resultBean = list.get(i);
        viewHolder.obligation.setText(resultBean.getMovieName());
        viewHolder.obligationCode.setText("订单号: "+resultBean.getOrderId());
        viewHolder.obligationMoney.setText("金额: "+resultBean.getPrice()+"");
        viewHolder.obligationMoviehall.setText("影厅: "+resultBean.getScreeningHall());
        viewHolder.obligationNum.setText("数量: "+resultBean.getAmount()+"");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(resultBean.getCreateTime()));
        viewHolder.obligationTime.setText("时间: "+date+"");
        viewHolder.obligation_cinema.setText("影院: "+resultBean.getCinemaName());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.obligation)
        TextView obligation;
        @BindView(R.id.obligation_but)
        Button obligationBut;
        @BindView(R.id.obligation_cinema)
        TextView obligation_cinema;
        @BindView(R.id.obligation_code)
        TextView obligationCode;
        @BindView(R.id.obligation_moviehall)
        TextView obligationMoviehall;
        @BindView(R.id.obligation_time)
        TextView obligationTime;
        @BindView(R.id.obligation_num)
        TextView obligationNum;
        @BindView(R.id.obligation_money)
        TextView obligationMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
