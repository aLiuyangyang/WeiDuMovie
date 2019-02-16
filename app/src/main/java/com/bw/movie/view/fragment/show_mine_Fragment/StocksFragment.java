package com.bw.movie.view.fragment.show_mine_Fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.MineObligationRecyAdapter;
import com.bw.movie.adapter.showmine_adapter.MineStocksRecyAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ObligationBean;
import com.bw.movie.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StocksFragment extends BaseFragment {
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.mine_stock_recy)
    RecyclerView mineStockRecy;
    Unbinder unbinder;
    private MineStocksRecyAdapter mineStocksRecyAdapter;
    private int page=1;//第几页
    private int count=10;//每页显示数
    private int status=2;//状态
    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        filmDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mineStockRecy.setLayoutManager(linearLayoutManager);
        mineStocksRecyAdapter=new MineStocksRecyAdapter(getActivity());
        mineStockRecy.setAdapter(mineStocksRecyAdapter);
        setGet(String.format(Constant.BuyTicketRecord_Path,page,count,status),ObligationBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.fragment_stocks;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ObligationBean){
            ObligationBean obligationBean= (ObligationBean) data;
            if (obligationBean.getStatus().equals("0000")){
                List<ObligationBean.ResultBean> result = obligationBean.getResult();
                if (result.size()==0){
                    showToast("无待付款信息");
                }else {
                    mineStocksRecyAdapter.setList(obligationBean.getResult());
                }
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
