package com.bw.movie.view.activity.showcinemaactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Nearby_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.AttentionBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.SearchCinemaBean;
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_xrecy)
    XRecyclerView searchXrecy;
    @BindView(R.id.search_nones)
    RelativeLayout search_none;
    private int page = 1;//当前页数
    private ShowCinema_Nearby_Adapter showCinema_nearby_adapter;
    private int index;
    private int status;
    private String searchname;

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        searchname = intent.getStringExtra("searchname");
        page = 1;
        searchXrecy.setLoadingMoreEnabled(true);
        searchXrecy.setPullRefreshEnabled(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        searchXrecy.setLayoutManager(linearLayoutManager);
        showCinema_nearby_adapter = new ShowCinema_Nearby_Adapter(this);
        searchXrecy.setAdapter(showCinema_nearby_adapter);
        searchXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                searchXrecy.refreshComplete();
                load();
            }

            @Override
            public void onLoadMore() {
                page++;
                searchXrecy.loadMoreComplete();
                load();
            }
        });
        load();
        showCinema_nearby_adapter.setAttentLineners(new ShowCinema_Nearby_Adapter.AttentLineners() {
            @Override
            public void setattents(int b, int position, int id) {
                index=position;
                status=b;
                if (b == 0) {//取消
                    setGet(String.format(Constant.Unfollow_Path, id), AttentionBean.class);
                } else if (b ==1) {//关注
                    setGet(String.format(Constant.Attention_Path, id), AttentionBean.class);
                }
            }
        });
    }
    private void load() {
        setGet(String.format(Constant.AllCinemas,page,searchname), ShowCinemaBean.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(EventBusMessage eventBusMessage) {
        if (eventBusMessage.getId() == 1) {
            page = 1;
            setGet(String.format(Constant.AllCinemas,page,searchname), ShowCinemaBean.class);
        }
    }
    @Override
    public int getContent() {
        return R.layout.activity_search;
    }
    @Override
    public void success(Object data) {
        if (data instanceof ShowCinemaBean) {
            ShowCinemaBean showCinemaBean = (ShowCinemaBean) data;
            if (showCinemaBean.getStatus().equals("0000")) {
                List<ShowCinemaBean.ResultBean> result = showCinemaBean.getResult();
                if (result.size()==0){
                    search_none.setVisibility(View.VISIBLE);
                }else {
                    if (page == 1) {
                        showCinema_nearby_adapter.setList(result);
                    } else {
                        showCinema_nearby_adapter.addList(result);
                    }

                }

            }
        } else if (data instanceof AttentionBean) {
            AttentionBean attentionBean = (AttentionBean) data;
            if (attentionBean.getStatus().equals("0000")) {
                if(status==0){
                    showCinema_nearby_adapter.cancel(index);
                }else if(status==1){
                    showCinema_nearby_adapter.add(index);
                }
                EventBus.getDefault().post(new EventBusMessage(1));
                showToast(attentionBean.getMessage());
            } else {
                if (attentionBean.getMessage().equals("请先登陆")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                load();
            }
        }
    }

    @Override
    public void fail(String error) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
