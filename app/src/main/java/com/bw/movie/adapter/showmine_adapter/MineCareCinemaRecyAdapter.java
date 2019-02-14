package com.bw.movie.adapter.showmine_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.FilmCommentAdapter;
import com.bw.movie.bean.MovieCommentDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/14
 * author:刘洋洋(DELL)
 * function:
 */
public class MineCareCinemaRecyAdapter extends RecyclerView.Adapter<MineCareCinemaRecyAdapter.ViewHolder> {
    private List<MovieCommentDetailsBean.ResultBean> list;
    private Context context;

    public MineCareCinemaRecyAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }
    @NonNull
    @Override
    public MineCareCinemaRecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MineCareCinemaRecyAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
