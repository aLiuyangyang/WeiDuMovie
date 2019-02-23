package com.bw.movie.view.activity.logandregactivity;

import android.app.FragmentManager;
import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.view.fragment.show_fragment.ShowCinemaFragment;
import com.bw.movie.view.fragment.show_fragment.ShowFilmFragment;
import com.bw.movie.view.fragment.show_fragment.ShowMineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivity extends BaseActivity {

    @BindView(R.id.three_frag)
    FrameLayout three_frag;
    @BindView(R.id.success_button_movie)
    ImageView mSuccess_button_movie;
    @BindView(R.id.success_button_movies)
    ImageView mSuccess_button_movies;
    @BindView(R.id.success_button_cinema)
    ImageView mSuccess_button_cinema;
    @BindView(R.id.success_button_cinemas)
    ImageView mSuccess_button_cinemas;
    @BindView(R.id.success_button_mine)
    ImageView mSuccess_button_mine;
    @BindView(R.id.success_button_mines)
    ImageView mSuccess_button_mines;

    private android.support.v4.app.FragmentManager mManager;

    private ShowFilmFragment mMovieFragment;
    private ShowCinemaFragment mCinemaFragment;
    private ShowMineFragment mMineFragment;

    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieFragment = new ShowFilmFragment();
        mCinemaFragment = new ShowCinemaFragment();
        mMineFragment = new ShowMineFragment();
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().add(R.id.three_frag,mMovieFragment,mMovieFragment.getClass().getName()).commit();
    }

    @Override
    public void initView() {

    }

    // 用来计算返回键的点击间隔时间

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }*/

    @Override
    public void initData() {
        ButterKnife.bind(this);
        //防止底部导航栏顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    @OnClick({R.id.success_button_movie,R.id.success_button_cinema,R.id.success_button_mine})
    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.success_button_movie:
                mSuccess_button_movie.setVisibility(View.INVISIBLE);
                mSuccess_button_movies.setVisibility(View.VISIBLE);
                mSuccess_button_cinema.setVisibility(View.VISIBLE);
                mSuccess_button_cinemas.setVisibility(View.INVISIBLE);
                mSuccess_button_mine.setVisibility(View.VISIBLE);
                mSuccess_button_mines.setVisibility(View.INVISIBLE);
                android.support.v4.app.FragmentManager movie = getSupportFragmentManager();
                //FragmentManager movie = getSupportFragmentManager();
                FragmentTransaction transactionFilm = movie.beginTransaction();
                transactionFilm.hide(mMineFragment);
                transactionFilm.hide(mCinemaFragment);
                transactionFilm.show(mMovieFragment);
                transactionFilm.commit();
                break;
            case R.id.success_button_cinema:
                mSuccess_button_movie.setVisibility(View.VISIBLE);
                mSuccess_button_movies.setVisibility(View.INVISIBLE);
                mSuccess_button_cinema.setVisibility(View.INVISIBLE);
                mSuccess_button_cinemas.setVisibility(View.VISIBLE);
                mSuccess_button_mine.setVisibility(View.VISIBLE);
                mSuccess_button_mines.setVisibility(View.INVISIBLE);
                android.support.v4.app.FragmentManager cinema = getSupportFragmentManager();
                //FragmentManager cinema = getSupportFragmentManager();
                FragmentTransaction transactionCinema = cinema.beginTransaction();
                if (cinema.findFragmentByTag(mCinemaFragment.getClass().getName()) == null) {
                    transactionCinema.add(R.id.three_frag, mCinemaFragment, mCinemaFragment.getClass().getName());
                }
                transactionCinema.hide(mMovieFragment);
                transactionCinema.hide(mMineFragment);
                transactionCinema.show(mCinemaFragment);
                transactionCinema.commit();
                break;
            case R.id.success_button_mine:
                mSuccess_button_movie.setVisibility(View.VISIBLE);
                mSuccess_button_movies.setVisibility(View.INVISIBLE);
                mSuccess_button_cinema.setVisibility(View.VISIBLE);
                mSuccess_button_cinemas.setVisibility(View.INVISIBLE);
                mSuccess_button_mine.setVisibility(View.INVISIBLE);
                mSuccess_button_mines.setVisibility(View.VISIBLE);
                android.support.v4.app.FragmentManager mine = getSupportFragmentManager();
                //FragmentManager mine = getSupportFragmentManager();
                FragmentTransaction transactionMine = mine.beginTransaction();
                if (mine.findFragmentByTag(mMineFragment.getClass().getName()) == null) {
                    transactionMine.add(R.id.three_frag, mMineFragment, mMineFragment.getClass().getName());
                }
                transactionMine.hide(mMovieFragment);
                transactionMine.hide(mCinemaFragment);
                transactionMine.show(mMineFragment);
                transactionMine.commit();
                break;
        }
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

}
