package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
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
    List<Details_Info.ResultBean> mResultBeans;

    public Show_File_ImageShow_Adapter(Context context) {
        mContext = context;
        mResultBeans=new ArrayList<>();
    }

    public void setResultBeans(List<Details_Info.ResultBean> resultBeans) {
        mResultBeans = resultBeans;
        notifyDataSetChanged();
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
        List<String> image = mResultBeans.get(i).getPosterList();


            String s = image.get(0);
            Pattern compile = Pattern.compile("\\,");
            String[] split = compile.split(s);
        for (int j = 0; j < split.length; j++) {
            Glide.with(mContext).load(split[j]).into(viewHolder.mShow_image);

        }
        //Glide.with(mContext).load(j).into(viewHolder.mShow_image);

    }

    @Override
    public int getItemCount() {
        return mResultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView mShow_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mShow_image = itemView.findViewById(R.id.show_image);
        }
    }
}
