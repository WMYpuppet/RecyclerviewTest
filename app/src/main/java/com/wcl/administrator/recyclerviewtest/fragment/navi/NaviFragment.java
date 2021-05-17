package com.wcl.administrator.recyclerviewtest.fragment.navi;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.activity.X5WebView;
import com.wcl.administrator.recyclerviewtest.adapter.NaviAdapter;
import com.wcl.administrator.recyclerviewtest.adapter.NaviGridAdapter;
import com.wcl.administrator.recyclerviewtest.base.mvp.BaseMvpFragment;
import com.wcl.administrator.recyclerviewtest.bean.NaviBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaviFragment extends BaseMvpFragment implements NaviContract.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rv_grid)
    RecyclerView rvGrid;

    private NaviPresenter naviPresenter;

    List<NaviBean.DataBean> naviList = new ArrayList<>();
    NaviAdapter mAdapter;
    List<NaviBean.DataBean.ArticlesBean> naviGridList = new ArrayList<>();
    NaviGridAdapter mGridAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navi;
    }

    @Override
    protected void initData() {
        naviPresenter = new NaviPresenter(this);
        naviPresenter.getData();
    }


    @Override
    public void updateView(final List<NaviBean.DataBean> dataBean) {
        naviList.addAll(dataBean);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        naviList.get(0).setChecked(true);
        mAdapter = new NaviAdapter(getActivity(), naviList);
        rvList.setAdapter(mAdapter);

        naviGridList.addAll(dataBean.get(0).getArticles());
        rvGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mGridAdapter = new NaviGridAdapter(getActivity(), naviGridList);
        rvGrid.setAdapter(mGridAdapter);

        mAdapter.setOnItemClickListener(new NaviAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                for (NaviBean.DataBean bean : naviList) {
                    bean.setChecked(false);
                }
                naviList.get(position).setChecked(true);
                mAdapter.notifyDataSetChanged();
                naviGridList.clear();
                naviGridList.addAll(dataBean.get(position).getArticles());
                mGridAdapter.notifyDataSetChanged();
            }
        });
        mGridAdapter.setOnItemClickListener(new NaviGridAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), X5WebView.class);
                intent.putExtra("mUrl", naviGridList.get(position).getLink());
                intent.putExtra("mTitle", naviGridList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }


}
