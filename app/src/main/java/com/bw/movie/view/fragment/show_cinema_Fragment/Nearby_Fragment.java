package com.bw.movie.view.fragment.show_cinema_Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Nearby_Adapter;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Recommend_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.bean.ShowNearbyBean;
import com.bw.movie.utils.Constant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Nearby_Fragment extends BaseFragment {

        @BindView(R.id.nearby_cinema_xrecy)
        XRecyclerView nearbyCinemaXrecy;
        private int page=1;//当前页数
        private ShowCinema_Nearby_Adapter showCinema_adapter;

        @Override
        public void initView(View view) {
            ButterKnife.bind(this, view);
                load();
        }

        @Override
        public void initData(View view) {
                page=1;
                nearbyCinemaXrecy.setLoadingMoreEnabled(true);
                nearbyCinemaXrecy.setPullRefreshEnabled(true);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                nearbyCinemaXrecy.setLayoutManager(linearLayoutManager);
                showCinema_adapter=new ShowCinema_Nearby_Adapter(getActivity());
                nearbyCinemaXrecy.setAdapter(showCinema_adapter);
                nearbyCinemaXrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
                        @Override
                        public void onRefresh() {
                                page=1;
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
        }
        private void load() {
                setGet(String.format(Constant.Nearby_Path,page),ShowNearbyBean.class);
        }

        @Override
        public int getContent() {
                return R.layout.fragment_nearbys;
        }
        @Override
        public void success(Object data) {
                if (data instanceof ShowNearbyBean){
                        ShowNearbyBean showCinemaBean= (ShowNearbyBean) data;
                        if (showCinemaBean.getStatus().equals("0000")){
                                List<ShowNearbyBean.ResultBean> result = showCinemaBean.getResult();
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
