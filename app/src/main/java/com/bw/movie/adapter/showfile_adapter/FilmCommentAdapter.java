package com.bw.movie.adapter.showfile_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.MovieCommentDetailsBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilmCommentAdapter extends XRecyclerView.Adapter<FilmCommentAdapter.ViewHolder> {


    private Context mContext;
    private List<MovieCommentDetailsBean.ResultBean> mList;

    public FilmCommentAdapter(Context context, List<MovieCommentDetailsBean.ResultBean> list) {
        mContext = context;
        mList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.film_comment_button_image.setImageURI(mList.get(i).getCommentHeadPic());
        viewHolder.film_comment_button_name.setText(mList.get(i).getCommentUserName());
        viewHolder.film_comment_button_message.setText(mList.get(i).getCommentContent());
        Date date = new Date();
        SimpleDateFormat time = new SimpleDateFormat("MM-dd HH:mm");
        String format = time.format(date);
        viewHolder.film_comment_button_time.setText(format);
        viewHolder.film_comment_button_comment_num.setText(mList.get(i).getReplyNum() + "");
        viewHolder.film_prise_num.setText(mList.get(i).getGreatNum() + "");
        final int isGreat = mList.get(i).getIsGreat();
       if (isGreat==1){
            viewHolder.film_comment_button_prise.setImageResource(R.mipmap.com_icon_praise_selected);
        }
        viewHolder.film_comment_button_prise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickId.successed(mList.get(i).getIsGreat(),i,mList.get(i).getCommentId());
            }
        });
    }

    //关注
    public void add(int position){
        mList.get(position).setIsGreat(0);
        notifyDataSetChanged();
    }
    //取消
    public void cancel(int position){
        mList.get(position).setIsGreat(1);
        int greatNum = mList.get(position).getGreatNum();
        mList.get(position).setGreatNum(++greatNum);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.film_comment_button_image)
        SimpleDraweeView film_comment_button_image;
        @BindView(R.id.film_comment_button_name)
        TextView film_comment_button_name;
        @BindView(R.id.film_comment_button_message)
        TextView film_comment_button_message;
        @BindView(R.id.film_comment_button_time)
        TextView film_comment_button_time;
        @BindView(R.id.film_comment_button_comment_num)
        TextView film_comment_button_comment_num;
        @BindView(R.id.film_comment_button_comment)
        ImageView film_comment_button_comment;
        @BindView(R.id.film_prise_num)
        TextView film_prise_num;
        @BindView(R.id.film_comment_button_prise)
        ImageView film_comment_button_prise;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnclickId{
        void successed(int b,int position,int id);

    }

    private OnclickId mOnclickId;

    public void setOnclickId(OnclickId onclickId){
        mOnclickId=onclickId;
    }

}
