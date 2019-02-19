package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilmCosplayAdapter extends RecyclerView.Adapter<FilmCosplayAdapter.ViewHolder> {
    private Context mContext;
    private String mList;

    public FilmCosplayAdapter(Context context, String list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.film_details_child_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Pattern compile = Pattern.compile("\\,");
        String[] split = compile.split(mList);
        viewHolder.film_xrecycle_performer.setText(split[i]);

    }

    @Override
    public int getItemCount() {
        Pattern compile = Pattern.compile("\\,");
        String[] split = compile.split(mList);
        return split.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.film_xrecycle_performer)
        TextView film_xrecycle_performer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
