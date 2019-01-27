package com.bw.movie.view.activity.logandregactivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.fragment.show_fragment.ShowCinemaFragment;
import com.bw.movie.view.fragment.show_fragment.ShowFilmFragment;
import com.bw.movie.view.fragment.show_fragment.ShowMineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * date:2019/1/25
 * author:刘洋洋(DELL)
 * function:首页Activity
 */
public class ShowActivity extends BaseActivity {


    @BindView(R.id.flPager)
    FrameLayout flPager;
    @BindView(R.id.rbFilm)
    RadioButton rbFilm;
    @BindView(R.id.rbCinema)
    RadioButton rbCinema;
    @BindView(R.id.rbMine)
    RadioButton rbMine;
    private FragmentTransaction fragmentTransaction;
    private Unbinder bind;
    @Override
    public void initView() {
        bind = ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flPager,new ShowFilmFragment()).commit();
    }

    @Override
    public int getContent() {
        return R.layout.activity_show;
    }
    @Override
    public void success(Object data) {

    }
    @Override
    public void fail(String error) {

    }
    @OnClick({R.id.rbFilm, R.id.rbCinema, R.id.rbMine})
    public void onViewClicked(View view) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.rbFilm:
                fragmentTransaction.replace(R.id.flPager,new ShowFilmFragment()).commit();
                setAddAnimator(view);
                setCutAnimator(rbCinema);
                setCutAnimator(rbMine);
                break;
            case R.id.rbCinema:
                fragmentTransaction.replace(R.id.flPager,new ShowCinemaFragment()).commit();
                setAddAnimator(view);
                setCutAnimator(rbFilm);
                setCutAnimator(rbMine);
                break;
            case R.id.rbMine:
                fragmentTransaction.replace(R.id.flPager,new ShowMineFragment()).commit();
                setAddAnimator(view);
                setCutAnimator(rbCinema);
                setCutAnimator(rbFilm);
                break;
                default:break;
        }
    }
    //点击放大
    private void setAddAnimator(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.17f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.17f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(0);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }
    //点击缩小
    private void setCutAnimator(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(0);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
