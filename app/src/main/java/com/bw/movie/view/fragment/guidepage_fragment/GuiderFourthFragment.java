package com.bw.movie.view.fragment.guidepage_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.view.activity.logandregactivity.GuideActivity;
import com.bw.movie.view.activity.logandregactivity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:引导页四
 */
public class GuiderFourthFragment extends Fragment {
    @BindView(R.id.guide_but)
    Button guideBut;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.guide_but)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
