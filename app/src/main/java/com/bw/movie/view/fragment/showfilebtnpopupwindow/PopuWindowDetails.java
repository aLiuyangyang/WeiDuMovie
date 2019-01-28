package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.CollapsibleActionView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.utils.CollapsibleTextView;
import com.facebook.drawee.view.SimpleDraweeView;

public class PopuWindowDetails{
    private PopupWindow popupWindow;

    private Context context;
    private Details_Info.ResultBean resultBean;
    private TextView mTv_file_type;
    private TextView mTv_film_director;
    private TextView mTv_film_time;
    private TextView mTv_film_area;
    private ImageView mFinish_image;
    private CollapsibleTextView mJianjie;
    private SimpleDraweeView mFile_img;
    private TextView mFile_stars;

    public PopuWindowDetails(Context context, Details_Info.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        ConstraintLayout inflate= (ConstraintLayout) View.inflate(context, R.layout.first_popupwindow, null);
        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(inflate);


    }

    private void setButtonListeners(ConstraintLayout inflate) {
        mTv_file_type = inflate.findViewById(R.id.tv_film_type);
        mTv_film_director = inflate.findViewById(R.id.tv_film_director);
        mTv_film_time = inflate.findViewById(R.id.tv_film_time);
        mTv_film_area = inflate.findViewById(R.id.tv_film_area);
        mFinish_image = inflate.findViewById(R.id.finish_image);
        mJianjie = inflate.findViewById(R.id.jianjie);
        mFile_img = inflate.findViewById(R.id.file_img);
        mFile_stars = inflate.findViewById(R.id.file_stars);

        mFile_img.setImageURI(resultBean.getImageUrl());
        mTv_file_type.setText(resultBean.getMovieTypes());
        mTv_film_director.setText(resultBean.getDirector());
        mTv_film_area.setText(resultBean.getPlaceOrigin());
        mTv_film_time.setText(resultBean.getDuration());
        mJianjie.setDesc(resultBean.getSummary());
        mFile_stars.setText(resultBean.getStarring());

        mFinish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
