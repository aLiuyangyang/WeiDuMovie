package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.Show_File_ImageShow_Adapter;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.view.activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class PopuWindowHomeStill {
    private PopupWindow popupWindow;

    private Context context;
    private ImageView mFinish_image;
    private RecyclerView mThird_recyclerview;

    public PopuWindowHomeStill(Context context) {
        this.context = context;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        ConstraintLayout inflate= (ConstraintLayout) View.inflate(context, R.layout.third_popupwindow, null);
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
        mFinish_image = inflate.findViewById(R.id.finish_image);
        mThird_recyclerview = inflate.findViewById(R.id.third_recyclerview);


        mFinish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
