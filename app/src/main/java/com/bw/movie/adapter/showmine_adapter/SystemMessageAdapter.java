package com.bw.movie.adapter.showmine_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.SelectSystemBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.ViewHolder> {

    private Context context;
    private List<SelectSystemBean.ResultBean> list;

    public SystemMessageAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setData(List<SelectSystemBean.ResultBean> lists) {
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void addData(List<SelectSystemBean.ResultBean> lists) {
        list.addAll(lists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.system_message_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        ButterKnife.bind(viewHolder, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        long pushTime = list.get(i).getPushTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",Locale.CHINA);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String time = formatter.format(new Date(pushTime));
        viewHolder.systemMessageTextTitle.setText(list.get(i).getTitle());
        viewHolder.systemMessageTextTime.setText(time);
        viewHolder.systemMessageTextContent.setText(list.get(i).getContent());
        final int status = list.get(i).getStatus();
        /** 0未读 1已读 */
        if (status == 1){
            viewHolder.systemMessageTextRead.setVisibility(View.GONE);
        }else {
            viewHolder.systemMessageTextRead.setVisibility(View.VISIBLE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==0){
                    viewHolder.systemMessageTextRead.setVisibility(View.GONE);
                    if (isReadClick != null) {
                        isReadClick.isReadClick(list.get(i).getId());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.system_message_textTitle)
        TextView systemMessageTextTitle;
        @BindView(R.id.system_message_textTime)
        TextView systemMessageTextTime;
        @BindView(R.id.system_message_textContent)
        TextView systemMessageTextContent;
        @BindView(R.id.system_message_text_read)
        TextView systemMessageTextRead;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public interface IsReadClick{
        void isReadClick(int id);
    }

    IsReadClick isReadClick;

    public void setIsReadClick(IsReadClick isReadClick) {
        this.isReadClick = isReadClick;
    }
}
