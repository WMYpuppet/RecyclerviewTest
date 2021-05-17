package com.wcl.administrator.recyclerviewtest.fragment.project.projectlist;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.activity.X5WebView;
import com.wcl.administrator.recyclerviewtest.activity.login.LoginActivity;
import com.wcl.administrator.recyclerviewtest.adapter.ProjectListAdapter;
import com.wcl.administrator.recyclerviewtest.base.mvp.BaseMvpFragment;
import com.wcl.administrator.recyclerviewtest.bean.ProjectListBean;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;
import com.wcl.administrator.recyclerviewtest.util.Constants;
import com.wcl.administrator.recyclerviewtest.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectListFragment extends BaseMvpFragment implements ProjectListContract.View {


    private static final String TAG = "ProjectListFragment";
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    int page = 1;
    int cid = 0;
    int pageCount = 0;
    ProjectListAdapter mAdapter;
    List<ProjectListBean.DataBean.DatasBean> projectList = new ArrayList<>();
    ProjectListPresenter projectListPresenter;


    public ProjectListFragment() {
        // Required empty public constructor
    }

    public static ProjectListFragment newInstance(int cid) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, cid);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }


    @Override
    protected void initData() {
        cid = getArguments().getInt(TAG);
        projectListPresenter = new ProjectListPresenter(this);
        mAdapter = new ProjectListAdapter(getActivity(), projectList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(mAdapter);
        rvList.setNestedScrollingEnabled(false);//禁用滑动事件

        mAdapter.setOnItemClickListener(new ProjectListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, ProjectListAdapter.ViewName viewName, int position) {
                     switch (viewName){
                         case ITEM:
                             Intent intent = new Intent(getContext(), X5WebView.class);
                             intent.putExtra("mUrl", projectList.get(position).getLink());
                             intent.putExtra("mTitle", projectList.get(position).getTitle());
                             startActivity(intent);
                             break;
                         case IMVLIKE:
                             if (!(boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
                                 ToastUtils.showShort("请先登录");
                                 startActivity(new Intent(getContext(), LoginActivity.class));
                                 return;
                             }
                             if (projectList.get(position).isCollect()) {
                                 projectListPresenter.unCollectArticle(projectList.get(position).getId(), position);
                             } else {
                                 projectListPresenter.collectArticle(projectList.get(position).getId(), position);
                             }
                             ToastUtils.showShort("imvlike"+position);
                             break;
                     }
            }
        });

        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                    projectListPresenter.getProjectList(page, cid);
                } else {
                    ToastUtils.showShort("已经没有数据了");
                }
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                projectListPresenter.getProjectList(page, cid);
                refreshLayout.finishRefresh();
            }
        });
        page = 1;
        projectListPresenter.getProjectList(page, cid);
    }


    @Override
    public void updateProject(ProjectListBean.DataBean projectListBean) {
        pageCount = projectListBean.getPageCount();
        page = projectListBean.getCurPage();
        if (page == 1) {
            projectList.clear();
        }
        projectList.addAll(projectListBean.getDatas());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        if (responseBean.getErrorCode()==0){
            ToastUtils.showShort("收藏成功");
            projectList.get(position).setCollect(true);
            mAdapter.notifyItemChanged(position);
        }

    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        if (responseBean.getErrorCode()==0){
            ToastUtils.showShort("取消收藏成功");
            projectList.get(position).setCollect(false);
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        projectListPresenter.destroy();
    }
}
