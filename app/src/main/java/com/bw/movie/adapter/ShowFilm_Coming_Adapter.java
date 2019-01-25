package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.bean.ShowFile_ComingBean;
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/25
 * author:刘洋洋(DELL)
 * function:热门电影
 */
public class ShowFilm_Coming_Adapter extends RecyclerView.Adapter<ShowFilm_Coming_Adapter.ViewHolder> {
    private List<ShowFile_ComingBean.ResultBean> mlist;
    private Context context;

    public ShowFilm_Coming_Adapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
    }

    public void setClist(List<ShowFile_ComingBean.ResultBean> list) {
        this.mlist = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowFilm_Coming_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_layout_hotshop,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowFilm_Coming_Adapter.ViewHolder viewHolder, int i) {
        Uri parse = Uri.parse(mlist.get(i).getImageUrl());
        viewHolder.hotshop_image.setImageURI(parse);
        viewHolder.hotshop_name.setText(mlist.get(i).getName());
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hotshop_image)
        SimpleDraweeView hotshop_image;
        @BindView(R.id.hotshop_name)
        TextView hotshop_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
