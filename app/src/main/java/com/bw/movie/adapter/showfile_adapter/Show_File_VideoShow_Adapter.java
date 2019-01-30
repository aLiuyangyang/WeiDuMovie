package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.Details_Info;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * date:2019/1/27
 * author:孙佳鑫(孙佳鑫)
 * function:
 */
public class Show_File_VideoShow_Adapter extends RecyclerView.Adapter<Show_File_VideoShow_Adapter.ViewHolder> {
    Context mContext;
    List<Details_Info.ResultBean.ShortFilmListBean> mFilmListBeans;

    public Show_File_VideoShow_Adapter(Context context) {
        mContext = context;
        mFilmListBeans=new ArrayList<>();
    }

    public void setFilmListBeans(List<Details_Info.ResultBean.ShortFilmListBean> filmListBeans) {
        mFilmListBeans = filmListBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(mContext, R.layout.item_fileshow_video, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        boolean setUp = viewHolder.mPlayer_list_video.setUp(mFilmListBeans.get(i).getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if (setUp) {
            Glide.with(mContext).load(mFilmListBeans.get(i).getImageUrl()).into(viewHolder.mPlayer_list_video.thumbImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mFilmListBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final JCVideoPlayerStandard mPlayer_list_video;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlayer_list_video = itemView.findViewById(R.id.player_list_video);
        }
    }
}
