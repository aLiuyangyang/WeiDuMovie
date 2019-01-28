package com.bw.movie.view.fragment.show_cinema_Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Nearby_Adapter;
import com.bw.movie.adapter.showcinema_adapter.ShowCinema_Recommend_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.AttentionBean;
import com.bw.movie.bean.ShowCinemaBean;
import com.bw.movie.utils.Constant;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                showCinema_adapter.setAttentLineners(new ShowCinema_Nearby_Adapter.AttentLineners() {
                        @Override
                        public void setattents(int b, int position, int id) {
                                if(b==1){//取消
                                        setGet(String.format(Constant.Unfollow_Path,id),AttentionBean.class);
                                        showCinema_adapter.cancel(position);
                                }else if(b == 2){//关注
                                        setGet(String.format(Constant.Attention_Path,id),AttentionBean.class);
                                        showCinema_adapter.add(position);
                                }
                        }
                });
        }
        private void load() {
                setGet(String.format(Constant.Nearby_Path,page),ShowCinemaBean.class);
        }

        @Override
        public int getContent() {
                return R.layout.fragment_nearbys;
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
                }else if (data instanceof AttentionBean){
                        AttentionBean attentionBean= (AttentionBean) data;
                        if (attentionBean.getStatus().equals("0000")){
                                showToast(attentionBean.getMessage());
                        }else {
                                showToast(attentionBean.getMessage());
                                load();
                        }
                }
        }

        @Override
        public void fail(String error) {
           showLog(error);
        }


}
