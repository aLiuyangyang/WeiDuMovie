package com.bw.movie.view.fragment.allfile_fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.showfile_adapter.ShowAllHotFile_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.bw.movie.utils.Constant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IsHotFragment extends BaseFragment {

    @BindView(R.id.all_ishot_xrecy)
    XRecyclerView allIshotXrecy;
    Unbinder unbinder;
    private int page;//当前页数
    private int count=10;//数量
    private ShowAllHotFile_Adapter showAllHotFile_adapter;
    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        page=1;
        allIshotXrecy.setPullRefreshEnabled(true);
        allIshotXrecy.setLoadingMoreEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        allIshotXrecy.setLayoutManager(linearLayoutManager);
        showAllHotFile_adapter=new ShowAllHotFile_Adapter(getActivity());
        allIshotXrecy.setAdapter(showAllHotFile_adapter);
        allIshotXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
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
    }
    private void load() {
        setGet(String.format(Constant.Banner_Path,page,count),ShowFile_HotShopBean.class);
    }
    @Override
    public int getContent() {
        return R.layout.fragment_is_hot;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShowFile_HotShopBean){
            ShowFile_HotShopBean showFile_hotShopBean= (ShowFile_HotShopBean) data;
            if (showFile_hotShopBean.getStatus().equals("0000")){
                if (page==1){
                    showAllHotFile_adapter.setList(showFile_hotShopBean.getResult());
                }else {
                    showAllHotFile_adapter.addList(showFile_hotShopBean.getResult());
                }
                page++;
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
    }
}
