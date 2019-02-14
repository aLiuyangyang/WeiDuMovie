package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.bw.movie.utils.frescoutils.FrescoBitmapCallback;
import com.bw.movie.utils.frescoutils.FrescoLoadUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * date:2019/1/27
 * author:孙佳鑫(DELL)
 * function:
 */
public class PopuWindowHomeStill {
    private PopupWindow popupWindow;

    private Context context;
    private ImageView mFinish_image;
    private RecyclerView mThird_recyclerview;
    private Details_Info.ResultBean mResultBean;
    private Show_File_ImageShow_Adapter mShow_file_imageShow_adapter;

    public PopuWindowHomeStill(Context context, Details_Info.ResultBean resultBean) {
        this.context = context;
        mResultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if(popupWindow != null && popupWindow.isShowing()) {
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
        mShow_file_imageShow_adapter = new Show_File_ImageShow_Adapter(context);
        mShow_file_imageShow_adapter.setFilmListBeans(mResultBean.getShortFilmList());
        mThird_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mThird_recyclerview.setAdapter(mShow_file_imageShow_adapter);

        /*List<String> posterList = mResultBean.getPosterList();
        final List<Bitmap> mlist = new ArrayList<>();
        for (int i = 0; i < posterList.size(); i++) {
            FrescoLoadUtil.getInstance().loadImageBitmap(posterList.get(i), new FrescoBitmapCallback<Bitmap>() {
                @Override
                public void onSuccess(Uri uri, Bitmap result) {
                    mlist.add(result);
                }

                @Override
                public void onFailure(Uri uri, Throwable throwable) {

                }

                @Override
                public void onCancel(Uri uri) {

                }
            });

        }
        show_file_imageShow_adapter.setResultBeans(mlist);*/

        mFinish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

}
