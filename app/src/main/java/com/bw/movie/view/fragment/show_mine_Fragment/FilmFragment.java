package com.bw.movie.view.fragment.show_mine_Fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.MineCareCinemaRecyAdapter;
import com.bw.movie.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 刘洋洋
 */
public class FilmFragment extends BaseFragment {

    @BindView(R.id.mine_care_film_recy)
    RecyclerView mineCareFilmRecy;
    Unbinder unbinder;
    private int page = 1;
    private int count = 10;
    private MineCareCinemaRecyAdapter mineCareCinemaRecyAdapter;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {

    }

    @Override
    public int getContent() {
        return R.layout.fragment_film;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
