package com.bw.movie.view.fragment.show_mine_Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.adapter.showmine_adapter.MineCareCinemaRecyAdapter;
import com.bw.movie.adapter.showmine_adapter.MineObligationRecyAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.ObligationBean;
import com.bw.movie.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ObligationFragment extends BaseFragment {
    @BindView(R.id.film_details_back)
    ImageView filmDetailsBack;
    @BindView(R.id.mine_obliagtion_recy)
    RecyclerView mine_obliagtion_recy;
    @BindView(R.id.search_none)
    RelativeLayout search_none;
    private MineObligationRecyAdapter mineObligationRecyAdapter;
    private int page=1;//第几页
    private int count=10;//每页显示数
    private int status=1;//状态
    @Override
    public void initView(View view) {
        ButterKnife.bind(this,view);
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
        mine_obliagtion_recy.setLayoutManager(linearLayoutManager);
        mineObligationRecyAdapter=new MineObligationRecyAdapter(getActivity());
        mine_obliagtion_recy.setAdapter(mineObligationRecyAdapter);
       setGet(String.format(Constant.BuyTicketRecord_Path,page,count,status),ObligationBean.class);
    }

    @Override
    public int getContent() {
        return R.layout.fragment_ticket;
    }

    @Override
    public void success(Object data) {
        if (data instanceof ObligationBean){
            ObligationBean obligationBean= (ObligationBean) data;
            if (obligationBean.getStatus().equals("0000")){
                List<ObligationBean.ResultBean> result = obligationBean.getResult();
                if (result.size()==0){
                    search_none.setVisibility(View.VISIBLE);
                }else {
                    mineObligationRecyAdapter.setList(result);
                }

            }
        }
    }

    @Override
    public void fail(String error) {

    }

}
