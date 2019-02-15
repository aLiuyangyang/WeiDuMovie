package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Camera_BannerBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.view.activity.showfileactivity.DetailsActivity;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/25
 * author:孙佳鑫(孙佳鑫)
 * function:轮播图
 */
public class Camera_Banner_Adapter extends RecyclerView.Adapter<Camera_Banner_Adapter.ViewHolder> {

    private Context mContext;
    private List<Camera_BannerBean.ResultBean> list;

    public Camera_Banner_Adapter(Context context) {
        mContext = context;
        list=new ArrayList<>();
    }

    public void setList(List<Camera_BannerBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getmovied(int position){
        int id = list.get(position).getId();
        return id;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_layout_coverflow, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
        GenericDraweeHierarchy build = builder.setRoundingParams(roundingParams).build();
        Uri parse = Uri.parse(list.get(i).getImageUrl());
        holder.image.setHierarchy(build);
        holder.image.setImageURI(parse);
        holder.text.setText(list.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SimpleDraweeView image;
        @BindView(R.id.text)
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
