package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.Details_Info;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * date:2019/1/27
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public class Show_File_ImageShow_Adapter extends RecyclerView.Adapter<Show_File_ImageShow_Adapter.ViewHolder> {
    Context mContext;

    List<Bitmap>  imgs ;
    public Show_File_ImageShow_Adapter(Context context) {
        mContext = context;
        imgs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(mContext, R.layout.item_fileshow_image, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mShow_image.setImageBitmap(imgs.get(i));
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public void setResultBeans(List<Bitmap> mlist) {
        imgs =mlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView mShow_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mShow_image = itemView.findViewById(R.id.show_image);
        }
    }
}
