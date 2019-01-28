package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.Show_File_VideoShow_Adapter;
import com.bw.movie.bean.Details_Info;

import java.util.List;

public class PopuWindowForeshow {
    private PopupWindow popupWindow;

    private Context context;
    private Details_Info.ResultBean resultBean;
    private ImageView mFinish_image;
    private RecyclerView mSecond_recyclerview;

    public PopuWindowForeshow(Context context, Details_Info.ResultBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        ConstraintLayout inflate= (ConstraintLayout) View.inflate(context, R.layout.second_popupwindow, null);
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
        mSecond_recyclerview = inflate.findViewById(R.id.second_recyclerview);

        Show_File_VideoShow_Adapter show_file_videoShow_adapter = new Show_File_VideoShow_Adapter(context);
        List<Details_Info.ResultBean.ShortFilmListBean> shortFilmList = resultBean.getShortFilmList();
        show_file_videoShow_adapter.setFilmListBeans(shortFilmList);
        mSecond_recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        mSecond_recyclerview.setAdapter(show_file_videoShow_adapter);

        mFinish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
