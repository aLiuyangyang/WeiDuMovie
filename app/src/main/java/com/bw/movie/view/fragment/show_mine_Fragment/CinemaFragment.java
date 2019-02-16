package com.bw.movie.view.fragment.show_mine_Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.MineCareCinemaRecyAdapter;
import com.bw.movie.adapter.showmine_adapter.MineCareRecyAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.bean.MinecinemaBean;
import com.bw.movie.bean.MoviewPageListBean;
import com.bw.movie.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CinemaFragment extends BaseFragment {
    @BindView(R.id.mine_care_cinema_recy)
    RecyclerView mineCareCinemaRecy;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    Unbinder unbinder;
    private MineCareRecyAdapter mineCareRecyAdapter;
    private int page = 1;//页数
    private int count = 10;//每页请求的数量

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mineCareCinemaRecy.setLayoutManager(linearLayoutManager);
        mineCareRecyAdapter = new MineCareRecyAdapter(getActivity());
        mineCareCinemaRecy.setAdapter(mineCareRecyAdapter);
        setGet(String.format(Constant.CinemaPageList_Path, page, count), MinecinemaBean.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_cinema;
    }

    @Override
    public void success(Object data) {
     if (data instanceof MinecinemaBean){
         MinecinemaBean minecinemaBean= (MinecinemaBean) data;
         if (minecinemaBean.getStatus().equals("0000")){
             mineCareRecyAdapter.setList(minecinemaBean.getResult());
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
