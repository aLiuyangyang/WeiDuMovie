package com.bw.movie.view.fragment.showfragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.ShowFile_Banner_Adapter;
import com.bw.movie.adapter.ShowFilm_Coming_Adapter;
import com.bw.movie.adapter.ShowFilm_HotShop_Adapter;
import com.bw.movie.adapter.ShowFilm_NewShowing_Adapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ShowFile_ComingBean;
import com.bw.movie.bean.ShowFile_HotShopBean;
import com.bw.movie.bean.ShowFile_Banner_Info;
import com.bw.movie.bean.ShowFile_NewShowingBean;
import com.bw.movie.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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
    @BindView(R.id.now_search)
    SearchView nowSearch;
    @BindView(R.id.original_search)
    TextView originalSearch;
    @BindView(R.id.search_image)
    ImageView searchImage;
     @BindView(R.id.recyc_flow)
    RecyclerCoverFlow recycFlow;
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
        //热门电影
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotRecy.setLayoutManager(linearLayoutManager);
        showFilm_hotShop_adapter = new ShowFilm_HotShop_Adapter(getContext());
        hotRecy.setAdapter(showFilm_hotShop_adapter);
        setGet(String.format(Constant.Hotfile_Path, page,count), ShowFile_HotShopBean.class);
        //正在热映
        LinearLayoutManager linearLayoutManagerc=new LinearLayoutManager(getActivity());
        linearLayoutManagerc.setOrientation(OrientationHelper.HORIZONTAL);
        newShowingRecy.setLayoutManager(linearLayoutManagerc);
        newShowingRecy.setAdapter(showFilm_newShowing_adapter);
        showFilm_newShowing_adapter = new ShowFilm_NewShowing_Adapter(getContext());
        setGet(String.format(Constant.Coming_Path,page,count),ShowFile_ComingBean.class);
        //即将上映
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        comingSooeRecy.setLayoutManager(linearLayoutManager1);
        showFilm_coming_adapter = new ShowFilm_Coming_Adapter(getContext());
        comingSooeRecy.setAdapter(showFilm_coming_adapter);
        setGet(String.format(Constant.NWESHOWING_Path, page,count),ShowFile_NewShowingBean.class);

        //搜索
        originalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalSearch.setVisibility(View.INVISIBLE);
                searchImage.setVisibility(View.INVISIBLE);
                nowSearch.setVisibility(View.VISIBLE);
            }
        });
        //搜索
        nowSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originalSearch.setVisibility(View.VISIBLE);
                searchImage.setVisibility(View.VISIBLE);
                nowSearch.setVisibility(View.INVISIBLE);
            }
        });

        areaPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                ShowFile_HotShopBean hotShopBean = (ShowFile_HotShopBean) data;
            if (hotShopBean.getStatus().equals("0000")) {
                showFilm_hotShop_adapter.setMlist(hotShopBean.getResult());
                setGet(Constant.Banner_Path, ShowFile_Banner_Info.class);
            }
        }else if (data instanceof ShowFile_NewShowingBean){
                ShowFile_NewShowingBean showFile_newShowingBean= (ShowFile_NewShowingBean) data;
                if (showFile_newShowingBean.getStatus().equals("0000")){
                    showFilm_newShowing_adapter.setLlist(showFile_newShowingBean.getResult());
                }
        } else if (data instanceof ShowFile_ComingBean){
               ShowFile_ComingBean showFile_comingBean= (ShowFile_ComingBean) data;
                if (showFile_comingBean.getStatus().equals("0000")){
                showFilm_coming_adapter.setClist(showFile_comingBean.getResult());
            }
        } else if (data instanceof ShowFile_Banner_Info) {
            ShowFile_Banner_Info showFile_banner_info = (ShowFile_Banner_Info) data;
            List<ShowFile_Banner_Info.ResultBean> result = showFile_banner_info.getResult();
            ShowFile_Banner_Adapter showFile_banner_adapter = new ShowFile_Banner_Adapter(getContext(), result);
            recycFlow.setAdapter(showFile_banner_adapter);
            recycFlow.smoothScrollToPosition(4);
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
}
