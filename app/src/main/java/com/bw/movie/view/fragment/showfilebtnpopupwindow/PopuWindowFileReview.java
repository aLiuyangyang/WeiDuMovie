package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.FilmCommentAdapter;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MovieCommentDetailsBean;
import com.bw.movie.presenter.IPresenter;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.IView;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * date:2019/1/27
 * author:孙佳鑫(DELL)
 * function:评价和点赞页面
 */
public class PopuWindowFileReview implements IView {
    @BindView(R.id.ping)
    ImageView ping;
    @BindView(R.id.finish_image)
    ImageView finishImage;
    @BindView(R.id.fourth_recyclerview)
    RecyclerView fourthRecyclerview;
    @BindView(R.id.pinglun_edittext)
    EditText pinglunEdittext;
    @BindView(R.id.pingjia_btn)
    Button pingjiaBtn;
    int page=1;

    private PopupWindow popupWindow;
    private Context context;
    private IPresenter mIPresenter;
    private FilmCommentAdapter mCommentAdapter;
    private int movied;
    private RecyclerView mFilm_comment_recycle;
    private MovieCommentDetailsBean resultBean;
    private MovieCommentDetailsBean mDetailsBean;


    public PopuWindowFileReview(Context context, MovieCommentDetailsBean resultBean) {
        this.context = context;
        this.resultBean = resultBean;
        EventBus.getDefault().register(this);
    }

    public void bottomwindow(View view) {

        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        ConstraintLayout inflate = (ConstraintLayout) View.inflate(context, R.layout.fourth_popupwindow, null);
         ButterKnife.bind(this,inflate);
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void message(EventBusMessage eventBusMessage) {
        movied = eventBusMessage.getId();
    }



    private void setButtonListeners(ConstraintLayout inflate) {

        mIPresenter = new IPresenter(this);

        mFilm_comment_recycle = inflate.findViewById(R.id.fourth_recyclerview);
        //收起
        ImageView film_details_button_down = inflate.findViewById(R.id.finish_image);
        //收起按钮点击监听
        film_details_button_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        mCommentAdapter = new FilmCommentAdapter(context,resultBean.getResult());
        mFilm_comment_recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mFilm_comment_recycle.setAdapter(mCommentAdapter);

        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinglunEdittext.setVisibility(View.VISIBLE);
                pingjiaBtn.setVisibility(View.VISIBLE);
            }
        });


        pingjiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plev = pinglunEdittext.getText().toString().trim();
                Map<String, String> map = new HashMap<>();
                map.put("movieId",movied+"");
                map.put("commentContent",plev);
                mIPresenter.setRequest(Constant.pinglun_Path,LoginBean.class,map);
            }
        });

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




    }

    @Override
    public void successed(Object data) {
        if(data instanceof MovieCommentDetailsBean){
            mDetailsBean  = (MovieCommentDetailsBean) data;

        }else if(data instanceof LoginBean) {
            LoginBean loginBean = (LoginBean) data;
            String message = loginBean.getMessage();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            if (message.equals("评论成功")) {
                pinglunEdittext.setText("");
            }
        }
    }

    @Override
    public void failed(String error) {

    }
}
