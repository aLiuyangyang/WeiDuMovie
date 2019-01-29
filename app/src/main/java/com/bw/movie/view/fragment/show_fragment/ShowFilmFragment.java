package com.bw.movie.view.fragment.show_fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.ShowFilm_NewShowing_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFile_Banner_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_Coming_Adapter;
import com.bw.movie.adapter.showfile_adapter.ShowFilm_HotShop_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.bw.movie.bean.ShowFile_NewShowingBean;
import com.bw.movie.utils.Constant;
import com.bw.movie.utils.ImageViewAnimationHelper;
import com.bw.movie.view.activity.ChoseseatActivity;
import com.bw.movie.view.activity.showfileactivity.ShowFileAllActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * date:2019/1/25
 * author:刘洋洋(DELL)
 * function:影片展示
 */
public class ShowFilmFragment extends BaseFragment {
    @BindView(R.id.comingSooe_Recy)
    RecyclerView comingSooeRecy;
    @BindView(R.id.hotRecy)
    RecyclerView hotRecy;
    @BindView(R.id.newShowing_Recy)
    RecyclerView newShowingRecy;
    Unbinder unbinder;
    @BindView(R.id.area_place)
    ImageView areaPlace;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.recyc_flow)
    RecyclerCoverFlow recycFlow;
    @BindView(R.id.hotAll_HotFile)
    ImageView hotAllHotFile;
    @BindView(R.id.hotAll_IsHot)
    ImageView hotAllIsHot;
    @BindView(R.id.hotAll_Coming)
    ImageView hotAllComing;
    @BindView(R.id.file_search_img)
    ImageView fileSearchImg;
    @BindView(R.id.file_search_edit)
    EditText fileSearchEdit;
    @BindView(R.id.file_search_text)
    TextView fileSearchText;
    @BindView(R.id.file_linearLayout_search)
    LinearLayout fileLinearLayoutSearch;
    @BindView(R.id.hfile)
    TextView hfile;
    @BindView(R.id.hview)
    View hview;
    @BindView(R.id.hot)
    RelativeLayout hot;
    @BindView(R.id.ntext)
    TextView ntext;
    @BindView(R.id.nview)
    View nview;
    @BindView(R.id.newshowing)
    RelativeLayout newshowing;
    @BindView(R.id.ctext)
    TextView ctext;
    @BindView(R.id.cview)
    View cview;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    Unbinder unbinder1;
    @BindView(R.id.checked_layout)
    LinearLayout checkedLayout;
    @BindView(R.id.parent_layout)
    LinearLayout parentLayout;
    Unbinder unbinder2;

    private int page;//当前页数
    private int count = 10;//每页请求的数量;
    private ShowFilm_HotShop_Adapter showFilm_hotShop_adapter;
    private ShowFilm_NewShowing_Adapter showFilm_newShowing_adapter;
    private ShowFilm_Coming_Adapter showFilm_coming_adapter;

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        page = 1;
        //轮播图
        setGet(String.format(Constant.Banner_Path, page, count), ShowFile_Banner_Info.class);
        //热门电影
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotRecy.setLayoutManager(linearLayoutManager);
        showFilm_hotShop_adapter = new ShowFilm_HotShop_Adapter(getContext());
        hotRecy.setAdapter(showFilm_hotShop_adapter);
        setGet(String.format(Constant.Hotfile_Path, page, count), ShowFile_HotShopBean.class);
        //正在热映
        LinearLayoutManager linearLayoutManagerc = new LinearLayoutManager(getActivity());
        linearLayoutManagerc.setOrientation(OrientationHelper.HORIZONTAL);
        newShowingRecy.setLayoutManager(linearLayoutManagerc);
        showFilm_newShowing_adapter = new ShowFilm_NewShowing_Adapter(getContext());
        newShowingRecy.setAdapter(showFilm_newShowing_adapter);
        setGet(String.format(Constant.Banner_Path, page, count), ShowFile_Banner_Info.class);
        //即将上映
        LinearLayoutManager linearLayoutManagerj = new LinearLayoutManager(getContext());
        linearLayoutManagerj.setOrientation(LinearLayoutManager.HORIZONTAL);
        comingSooeRecy.setLayoutManager(linearLayoutManagerj);
        showFilm_coming_adapter = new ShowFilm_Coming_Adapter(getContext());
        comingSooeRecy.setAdapter(showFilm_coming_adapter);
        setGet(String.format(Constant.NWESHOWING_Path, page, count), ShowFile_NewShowingBean.class);
        //搜索
        fileSearchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fileLinearLayoutSearch, "translationX", 0f, -380f);
//      设置移动时间
                objectAnimator.setDuration(1000);
//      开始动画
                objectAnimator.start();

            }
        });

        fileSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fileLinearLayoutSearch, "translationX", -380f, 0f);
//      设置移动时间
                objectAnimator.setDuration(1000);
//      开始动画
                objectAnimator.start();

            }
        });

        areaPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ChoseseatActivity.class));
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_show_film;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShowFile_HotShopBean) {
            //热门
            ShowFile_HotShopBean hotShopBean = (ShowFile_HotShopBean) data;
            if (hotShopBean.getStatus().equals("0000")) {
                showFilm_hotShop_adapter.setMlist(hotShopBean.getResult());
            }
        } else if (data instanceof ShowFile_NewShowingBean) {
            //正在上映
            ShowFile_NewShowingBean showFile_newShowingBean = (ShowFile_NewShowingBean) data;
            if (showFile_newShowingBean.getStatus().equals("0000")) {
                showFilm_newShowing_adapter.setLlist(showFile_newShowingBean.getResult());
            }
        } else if (data instanceof ShowFile_Banner_Info) {
            //即将上映
            ShowFile_Banner_Info showFile_banner_info = (ShowFile_Banner_Info) data;
            showFilm_coming_adapter.setClist(showFile_banner_info.getResult());
            ShowFile_Banner_Adapter showFile_banner_adapter = new ShowFile_Banner_Adapter(getContext(), showFile_banner_info.getResult());
            recycFlow.setAdapter(showFile_banner_adapter);
            recycFlow.smoothScrollToPosition(4);
            final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(getContext(), checkedLayout, 2, 30);
            recycFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                @Override
                public void onItemSelected(int position) {
                    imageViewAnimationHelper.startAnimation(position);
                }
            });
        }
    }

    @Override
    public void fail(String error) {
        showLog(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.hotAll_HotFile, R.id.hotAll_IsHot, R.id.hotAll_Coming})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hotAll_HotFile:
                Intent intent = new Intent(getActivity(), ShowFileAllActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
            case R.id.hotAll_IsHot:
                Intent intenti = new Intent(getActivity(), ShowFileAllActivity.class);
                intenti.putExtra("flag", 2);
                startActivity(intenti);
                break;
            case R.id.hotAll_Coming:
                Intent intentc = new Intent(getActivity(), ShowFileAllActivity.class);
                intentc.putExtra("flag", 3);
                startActivity(intentc);
                break;
        }
    }



}
