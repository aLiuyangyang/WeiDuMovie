package com.bw.movie.view.fragment.show_mine_Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.MineCareCinemaRecyAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.Details_Info;
import com.bw.movie.bean.MoviewPageListBean;
import com.bw.movie.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 刘洋洋
 */
public class FilmFragment extends BaseFragment {
    @BindView(R.id.mine_care_film_recy)
    RecyclerView mineCareFilmRecy;
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    Unbinder unbinder;
    private int page = 1;//页数
    private int count = 10;//每页请求的数量
    private MineCareCinemaRecyAdapter mineCareCinemaRecyAdapter;

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mineCareFilmRecy.setLayoutManager(linearLayoutManager);
        mineCareCinemaRecyAdapter = new MineCareCinemaRecyAdapter(getActivity());
        mineCareFilmRecy.setAdapter(mineCareCinemaRecyAdapter);
        setGet(String.format(Constant.MoviePageList_Path, page, count), MoviewPageListBean.class);
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_film;
    }

    @Override
    public void success(Object data) {
        if (data instanceof MoviewPageListBean) {
            MoviewPageListBean moviewPageListBean = (MoviewPageListBean) data;
            if (moviewPageListBean.getStatus().equals("0000")) {
                mineCareCinemaRecyAdapter.setList(moviewPageListBean.getResult());
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
