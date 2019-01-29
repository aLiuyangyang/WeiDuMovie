package com.bw.movie.adapter.showfile_adapter;

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
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.bw.movie.view.activity.showfileactivity.DetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/26
 * author:刘洋洋(DELL)
 * function:展示全部热门
 */
public class ShowAllHotFile_Adapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {

    private List<ShowFile_HotShopBean.ResultBean> mlist;
    private Context context;

    public ShowAllHotFile_Adapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setList(List<ShowFile_HotShopBean.ResultBean> list) {
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(List<ShowFile_HotShopBean.ResultBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_hot, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
       ViewHolder holder= (ViewHolder) viewHolder;
       holder.allHotName.setText(mlist.get(i).getName());
       holder.allHotBrief.setText("简介:  "+mlist.get(i).getSummary());
       Uri parse = Uri.parse(mlist.get(i).getImageUrl());
       holder.allHotImage.setImageURI(parse);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailsActivity.class));
                EventBus.getDefault().postSticky(new EventBusMessage(mlist.get(i).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.all_hot_image)
        SimpleDraweeView allHotImage;
        @BindView(R.id.all_hot_name)
        TextView allHotName;
        @BindView(R.id.all_hot_attention)
        SimpleDraweeView allHotAttention;
        @BindView(R.id.all_hot_brief)
        TextView allHotBrief;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
