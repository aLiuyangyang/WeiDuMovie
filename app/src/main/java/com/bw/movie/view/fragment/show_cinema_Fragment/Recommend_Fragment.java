package com.bw.movie.view.fragment.show_cinema_Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Recommend_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.utils.Constant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Recommend_Fragment extends BaseFragment {
        @BindView(R.id.recommend_cinema_xrecy)
        XRecyclerView recommendCinemaXrecy;
        private int page;//当前页数
        private ShowCinema_Recommend_Adapter showCinema_adapter;
        @Override
        public void initView(View view) {
                ButterKnife.bind(this,view);
        }

        @Override
        public void initData(View view) {
                page=1;
                recommendCinemaXrecy.setLoadingMoreEnabled(true);
                recommendCinemaXrecy.setPullRefreshEnabled(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                recommendCinemaXrecy.setLayoutManager(linearLayoutManager);
                showCinema_adapter=new ShowCinema_Recommend_Adapter(getActivity());
                recommendCinemaXrecy.setAdapter(showCinema_adapter);
                recommendCinemaXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
                        @Override
                        public void onRefresh() {
                                page=1;
                                recommendCinemaXrecy.refreshComplete();
                                load();
                        }
                        @Override
                        public void onLoadMore() {
                                recommendCinemaXrecy.loadMoreComplete();
                                load();
                        }
                });
                load();
        }
        private void load() {
                setGet(String.format(Constant.Recommend_Path,page),ShowCinemaBean.class);
        }

        @Override
        public int getContent() {
                return R.layout.fragment_recommend;
        }

        @Override
        public void success(Object data) {
                if (data instanceof ShowCinemaBean){
                        ShowCinemaBean showCinemaBean= (ShowCinemaBean) data;
                        if (showCinemaBean.getStatus().equals("0000")){
                                List<ShowCinemaBean.ResultBean> result = showCinemaBean.getResult();
                                if (page==1){
                                        showCinema_adapter.setList(result);
                                }else {
                                        showCinema_adapter.addList(result);
                                }
                                page++;
                        }
                }
        }

        @Override
        public void fail(String error) {

        }

}
