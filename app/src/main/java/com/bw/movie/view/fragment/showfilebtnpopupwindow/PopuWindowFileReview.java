package com.bw.movie.view.fragment.showfilebtnpopupwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.bw.movie.bean.AttentionBean;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
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
    private int index;
    private int status;
    private PopupWindow popupWindow;
    private Context context;
    private IPresenter mIPresenter;
    private FilmCommentAdapter mCommentAdapter;
    private int movied;
    private RecyclerView mFilm_comment_recycle;
    private MovieCommentDetailsBean resultBean;
    private SharedPreferences sharedPreferences;//存储
    private SharedPreferences.Editor edit;
    private boolean isUser;

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
        sharedPreferences=context.getSharedPreferences("UserMessage",MODE_PRIVATE);
        edit = sharedPreferences.edit();
        isUser = sharedPreferences.getBoolean("isUser", false);
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
                if (isUser) {
                    pinglunEdittext.setVisibility(View.VISIBLE);
                    pingjiaBtn.setVisibility(View.VISIBLE);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("您还没有登陆，确认要去登陆吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        pingjiaBtn.setOnClickListener(new View.OnClickListener() {

            private String plev;

            @Override
            public void onClick(View v) {

                plev = pinglunEdittext.getText().toString().trim();
                if(!plev.equals("")){
                    Map<String, String> map = new HashMap<>();
                    map.put("movieId",movied+"");
                    map.put("commentContent", plev);
                    mIPresenter.setRequest(Constant.pinglun_Path,LoginBean.class,map);
                    pinglunEdittext.setText("");
                }else {
                    Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mCommentAdapter.setOnclickId(new FilmCommentAdapter.OnclickId() {
            @Override
            public void successed(int b, int position, int id) {
                index=position;
                status=b;
                if(isUser){
                    if (b ==1){//取消
                        Map<String, String> map = new HashMap<>();
                        map.put("commentId", id + "");
                        mIPresenter.setRequest(Constant.Prise_Path, LoginBean.class, map);
                    }else if (b==0) {
                        Map<String, String> map = new HashMap<>();
                        map.put("commentId", id + "");
                        mIPresenter.setRequest(Constant.Prise_Path, LoginBean.class, map);
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("您还没有登陆，确认要去登陆吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }
    @Override
    public void successed(Object data) {
        if(data instanceof MovieCommentDetailsBean){
            MovieCommentDetailsBean movieCommentDetailsBean= (MovieCommentDetailsBean) data;
            Toast.makeText(context, movieCommentDetailsBean.getMessage(), Toast.LENGTH_SHORT).show();
        }else if(data instanceof LoginBean){
            LoginBean loginBean = (LoginBean) data;
            if (loginBean.getStatus().equals("0000")){
                if(status==0){
                    mCommentAdapter.cancel(index);
                }else if(status==0){
                    mCommentAdapter.add(index);
                }
                Toast.makeText(context, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }else if (loginBean.getMessage().equals("请先登陆")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("您还没有登录，确认要去登录吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Toast.makeText(context, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }else if (loginBean.getMessage().equals("评论成功")){
                pinglunEdittext.setText("");
                Toast.makeText(context, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void failed(String error) {

    }
}
