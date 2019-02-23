package com.bw.movie.view.fragment.show_cinema_Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Nearby_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.AttentionBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.MessageBus;
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

import static android.content.Context.MODE_PRIVATE;

public class Nearby_Fragment extends BaseFragment {

    @BindView(R.id.nearby_cinema_xrecy)
    XRecyclerView nearbyCinemaXrecy;
    private int page = 1;//当前页数
    private ShowCinema_Nearby_Adapter showCinema_adapter;
    private int index;
    private int status;
    private double mylatitude;
    private double mylongitude;

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        load();
    }

    @Override
    public void initData(View view) {
        page = 1;
        nearbyCinemaXrecy.setLoadingMoreEnabled(true);
        nearbyCinemaXrecy.setPullRefreshEnabled(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        nearbyCinemaXrecy.setLayoutManager(linearLayoutManager);
        showCinema_adapter = new ShowCinema_Nearby_Adapter(getActivity());
        nearbyCinemaXrecy.setAdapter(showCinema_adapter);
        nearbyCinemaXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                nearbyCinemaXrecy.refreshComplete();
                load();
            }

            @Override
            public void onLoadMore() {
                page++;
                nearbyCinemaXrecy.loadMoreComplete();
                load();
            }
        });
        load();
        showCinema_adapter.setAttentLineners(new ShowCinema_Nearby_Adapter.AttentLineners() {
            @Override
            public void setattents(int b, int position, int id) {
                index=position;
                status=b;
                if (b == 1) {//取消
                    setGet(String.format(Constant.Unfollow_Path, id), AttentionBean.class);
                } else if (b == 2) {//关注
                    setGet(String.format(Constant.Attention_Path, id), AttentionBean.class);
                }
            }
        });
    }
    private void load() {
        setGet(String.format(Constant.Nearby_Path,mylongitude,mylatitude,page), ShowCinemaBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.fragment_nearbys;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(EventBusMessage eventBusMessage) {
        if (eventBusMessage.getId() == 1) {
            page = 1;
            setGet(String.format(Constant.Nearby_Path, mylongitude,mylatitude,page), ShowCinemaBean.class);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky =true)
    public void message(MessageBus messageBus) {
        mylatitude = messageBus.getMylatitude();
        mylongitude = messageBus.getMylongitude();
    }
    @Override
    public void success(Object data) {
        if (data instanceof ShowCinemaBean) {
            ShowCinemaBean showCinemaBean = (ShowCinemaBean) data;
            if (showCinemaBean.getStatus().equals("0000")) {
                List<ShowCinemaBean.ResultBean> result = showCinemaBean.getResult();
                if (page == 1) {
                    showCinema_adapter.setList(result);
                } else {
                    showCinema_adapter.addList(result);
                }
                page++;
            }
        } else if (data instanceof AttentionBean) {
            AttentionBean attentionBean = (AttentionBean) data;
            if (attentionBean.getStatus().equals("0000")) {
                if(status==1){
                    showCinema_adapter.cancel(index);
                }else if(status==2){
                    showCinema_adapter.add(index);
                }
                EventBus.getDefault().post(new EventBusMessage(1));
                showToast(getContext(),attentionBean.getMessage());
            } else {
                if (attentionBean.getMessage().equals("请先登陆")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("您还没有登录，确认要去登录吗?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), LoginActivity.class);
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
        showLog(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
