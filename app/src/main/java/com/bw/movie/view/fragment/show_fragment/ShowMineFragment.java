package com.bw.movie.view.fragment.show_fragment;


import android.view.KeyEvent;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;

/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:我的页面
 */
public class ShowMineFragment extends BaseFragment {

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {

    }

    @Override
    public int getContent() {
        return R.layout.fragment_show_mine;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
    private long exitTime=0;
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {

                        exitTime = System.currentTimeMillis();
                        showToast("再按一次退出程序");
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }
}
