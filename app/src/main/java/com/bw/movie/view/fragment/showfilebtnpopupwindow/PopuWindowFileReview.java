package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.FilmCommentAdapter;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MovieCommentDetailsBean;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * date:2019/1/27
 * author:孙佳鑫(DELL)
 * function:评价和点赞页面
 */
public class PopuWindowFileReview implements IView {
    @BindView(R.id.ping)
    ImageView ping;
    private PopupWindow popupWindow;
    private Context context;
    private MovieCommentDetailsBean resultBean;
    private IPresenter mIPresenter;
    private FilmCommentAdapter mCommentAdapter;


    public PopuWindowFileReview(Context context, MovieCommentDetailsBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        ConstraintLayout inflate = (ConstraintLayout) View.inflate(context, R.layout.fourth_popupwindow, null);
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
        RecyclerView film_comment_recycle = inflate.findViewById(R.id.fourth_recyclerview);
        //收起
        ImageView film_details_button_down = inflate.findViewById(R.id.finish_image);
        //收起按钮点击监听
        film_details_button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        mCommentAdapter = new FilmCommentAdapter(context, resultBean.getResult());
        film_comment_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        film_comment_recycle.setAdapter(mCommentAdapter);


        mIPresenter = new IPresenter(this);

        mCommentAdapter.setOnclickId(new FilmCommentAdapter.OnclickId() {
            @Override
            public void successed(int commentId) {
                Map<String, String> map = new HashMap<>();
                map.put("commentId", commentId + "");
                mIPresenter.setRequest(Constant.Prise_Path, LoginBean.class, map);
            }

            @Override
            public void failed() {

            }
        });

        /*ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public void successed(Object data) {
        LoginBean loginBean = (LoginBean) data;
        String message = loginBean.getMessage();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.i("sjx", message);
    }

    @Override
    public void failed(String error) {

    }
}
