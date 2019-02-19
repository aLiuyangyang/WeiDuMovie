package com.bw.movie.view.fragment.allfile_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Nearby_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowAllHotFile_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.AttentionBean;
import com.bw.movie.bean.EventBusMessage;
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * date:2019/1/27
 * author:刘洋洋(DELL)
 * function: 正在热映
 */
public class IsHotFragment extends BaseFragment {

    @BindView(R.id.all_ishot_xrecy)
    XRecyclerView allIshotXrecy;
    Unbinder unbinder;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    Unbinder unbinder1;
    private int page;//当前页数
    private int count = 10;//数量
    private ShowAllHotFile_Adapter showAllHotFile_adapter;
    private int index;//下标
    private int status;//状态
    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData(View view) {
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        page = 1;
        allIshotXrecy.setPullRefreshEnabled(true);
        allIshotXrecy.setLoadingMoreEnabled(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        allIshotXrecy.setLayoutManager(linearLayoutManager);
        showAllHotFile_adapter = new ShowAllHotFile_Adapter(getActivity());
        allIshotXrecy.setAdapter(showAllHotFile_adapter);
        allIshotXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                allIshotXrecy.refreshComplete();
                load();
            }

            @Override
            public void onLoadMore() {
                allIshotXrecy.loadMoreComplete();
                load();
            }
        });
        load();
        showAllHotFile_adapter.setAttentLineners(new ShowCinema_Nearby_Adapter.AttentLineners() {
            @Override
            public void setattents(int b, int position, int id) {
                index=position;
                status=b;
                if(b==1){//取消
                    setGet(String.format(Constant.Unfollowmovie_Path,id),AttentionBean.class);
                }else if(b == 2){//关注
                    setGet(String.format(Constant.Attentionmovie_Path,id),AttentionBean.class);
                }
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(EventBusMessage eventBusMessage) {
        if (eventBusMessage.getId() == 1){
            page = 1;
            setGet(String.format(Constant.Banner_Path, page, count), ShowFile_HotShopBean.class);
        }
    }
    private void load() {
        setGet(String.format(Constant.Banner_Path, page, count), ShowFile_HotShopBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.fragment_is_hot;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShowFile_HotShopBean) {
            ShowFile_HotShopBean showFile_hotShopBean = (ShowFile_HotShopBean) data;
            if (showFile_hotShopBean.getStatus().equals("0000")) {
                if (page == 1) {
                    showAllHotFile_adapter.setList(showFile_hotShopBean.getResult());
                } else {
                    showAllHotFile_adapter.addList(showFile_hotShopBean.getResult());
                }
                page++;
            }
        }else if (data instanceof AttentionBean){
            AttentionBean attentionBean= (AttentionBean) data;
            if (attentionBean.getStatus().equals("0000")){
                if(status==1){
                    showAllHotFile_adapter.cancel(index);
                }else if(status==2){
                    showAllHotFile_adapter.add(index);
                }
                EventBus.getDefault().post(new EventBusMessage(1));
                showToast(attentionBean.getMessage());
            }else {
                if (attentionBean.getMessage().equals("请先登陆")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("您还没有登陆，确认要去登陆吗?");
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}
