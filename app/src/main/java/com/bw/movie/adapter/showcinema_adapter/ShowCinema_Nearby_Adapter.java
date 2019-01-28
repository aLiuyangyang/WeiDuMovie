package com.bw.movie.adapter.showcinema_adapter;

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
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.view.activity.showcinemaactivity.DetailsOfCinemaActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/26
 * author:刘洋洋(DELL)
 * function:
 */
public class ShowCinema_Nearby_Adapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {

    private List<ShowCinemaBean.ResultBean> mlist;
    private Context context;

    public ShowCinema_Nearby_Adapter(Context context) {
        this.context = context;
        mlist = new ArrayList<>();
    }

    public void setList(List<ShowCinemaBean.ResultBean> list) {
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();

    }

    public void addList(List<ShowCinemaBean.ResultBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cinema_recommend, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder rHolder= (ViewHolder) viewHolder;
        rHolder.itemNameCinema.setText(mlist.get(i).getName());
        rHolder.itemAddrCinema.setText(mlist.get(i).getAddress());
        if (mlist.get(i).getFollowCinema()==1){
            rHolder.itemAttentionCinema.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            rHolder.itemAttentionCinema.setImageResource(R.mipmap.com_icon_collection_default);
        }
        rHolder.itemDistanceCinema.setText(mlist.get(i).getDistance()+"km");
        Uri uri = Uri.parse(mlist.get(i).getLogo());
        rHolder.itemImageCinema.setImageURI(uri);
        rHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailsOfCinemaActivity.class);
                intent.putExtra("id",mlist.get(i).getId());
                context.startActivity(intent);
            }
        });
        rHolder.itemAttentionCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attentLineners!=null) {
                    attentLineners.setattents(mlist.get(i).getFollowCinema(), i, mlist.get(i).getId());
                }
            }
        });
    }
    //关注
    public void add(int position){
        mlist.get(position).setFollowCinema(1);
        notifyDataSetChanged();
    }
    //取消
    public void cancel(int position){
        mlist.get(position).setFollowCinema(2);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image_cinema)
        SimpleDraweeView itemImageCinema;
        @BindView(R.id.item_name_cinema)
        TextView itemNameCinema;
        @BindView(R.id.item_addr_cinema)
        TextView itemAddrCinema;
        @BindView(R.id.item_distance_cinema)
        TextView itemDistanceCinema;
        @BindView(R.id.item_attention_cinema)
        SimpleDraweeView itemAttentionCinema;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private AttentLineners attentLineners;

    public void setAttentLineners(AttentLineners attentLineners) {
        this.attentLineners = attentLineners;
    }
    public interface AttentLineners{
        void setattents(int b,int position,int id);

    }
}
