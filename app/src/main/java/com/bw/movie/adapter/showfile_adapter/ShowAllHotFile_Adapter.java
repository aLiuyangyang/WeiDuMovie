package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.bean.ShowFile_HotShopBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/1/26
 * author:刘洋洋(DELL)
 * function:
 */
public class ShowAllHotFile_Adapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
      private List<ShowFile_HotShopBean.ResultBean> mlist;
      private Context context;

    public ShowAllHotFile_Adapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, int i) {

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
