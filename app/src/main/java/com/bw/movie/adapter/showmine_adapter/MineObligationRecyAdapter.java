package com.bw.movie.adapter.showmine_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.ObligationBean;
import com.bw.movie.view.activity.showfileactivity.ChoseseatActivity;

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

    private PopupWindow mPopupWindow;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.obligation.setText(list.get(i).getMovieName());
        viewHolder.obligationCode.setText("订单号: "+list.get(i).getOrderId());
        viewHolder.obligationMoney.setText("金额: "+list.get(i).getPrice()+"");
        viewHolder.obligationMoviehall.setText("影厅: "+list.get(i).getScreeningHall());
        viewHolder.obligationNum.setText("数量: "+list.get(i).getAmount()+"");
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list.get(i).getCreateTime()));
        viewHolder.obligationTime.setText("时间: "+date+"");
        viewHolder.obligation_cinema.setText("影院: "+list.get(i).getCinemaName());

        viewHolder.obligationBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickOrderId!=null){
                    mOnClickOrderId.successed(list.get(i).getOrderId(),list.get(i).getPrice());
                }
            }
        });

        /*viewHolder.obligationBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(context).inflate(R.layout.pay_popupwindow, null);
                mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //显示PopupWindow
                View rootview = LayoutInflater.from(context).inflate(R.layout.pay_popupwindow, null);
                mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
            }
        });*/
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


    public interface OnClickOrderId{
        void successed(String orderId,double price);
    }

    private OnClickOrderId mOnClickOrderId;

    public void setOnClickOrderId(OnClickOrderId onClickOrderId){
        mOnClickOrderId=onClickOrderId;
    }
}
