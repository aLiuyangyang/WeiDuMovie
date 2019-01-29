package com.bw.movie.adapter.showfile_adapter;

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
import com.bw.movie.bean.ChooseCinemaBean;
import com.bw.movie.view.activity.showfileactivity.CooseClassActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/1/28
 * author:刘洋洋(DELL)
 * function:
 */
public class ShowFile_ChooseCinema_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChooseCinemaBean.ResultBean> list;
    private Context context;

    public ShowFile_ChooseCinema_Adapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<ChooseCinemaBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_cinmea, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
      ViewHolder holder= (ViewHolder) viewHolder;
      holder.itemAddrChoose.setText(list.get(i).getAddress());
      holder.itemNameChoose.setText(list.get(i).getName());
      holder.itemDistanceChoose.setText(list.get(i).getDistance()+"km");
      Uri parse = Uri.parse(list.get(i).getLogo());
      holder.itemImageChoose.setImageURI(parse);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              if(mOnclick!=null){
                  mOnclick.success(list.get(i).getId(),list.get(i).getAddress(),list.get(i).getName());
              }
//              Intent intent=new Intent(context,CooseClassActivity.class);
//              intent.putExtra("address",list.get(i).getAddress());
//              intent.putExtra("name",list.get(i).getName());
//              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image_choose)
        SimpleDraweeView itemImageChoose;
        @BindView(R.id.item_name_choose)
        TextView itemNameChoose;
        @BindView(R.id.item_addr_choose)
        TextView itemAddrChoose;
        @BindView(R.id.item_distance_choose)
        TextView itemDistanceChoose;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface Onclick{
        void success(int id,String adress,String name);
    }

    private Onclick mOnclick;

    public void setmOnclick(Onclick mOnclick) {
        this.mOnclick = mOnclick;
    }
}
