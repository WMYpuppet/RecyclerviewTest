package com.wcl.administrator.recyclerviewtest.fragment.project;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.adapter.ViewPagerAdapter;
import com.wcl.administrator.recyclerviewtest.base.mvp.BaseMvpFragment;
import com.wcl.administrator.recyclerviewtest.bean.ProjectBean;
import com.wcl.administrator.recyclerviewtest.fragment.project.projectlist.ProjectListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseMvpFragment implements ProjectContract.View {

    @BindView(R.id.tab_project)
    TabLayout tabProject;
    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.vp_project)
    ViewPager vpProject;

    public static final String TAG = "ProjectFragment";

    private static ProjectFragment instance = null;

    public static final int REQ_CODE = 0x11;

    ViewPagerAdapter mProjectAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    private ProjectPresenter projectPresenter;

    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment getInstance() {
        if (instance == null) {
            instance = new ProjectFragment();
        }
        return instance;
    }

    public static ProjectFragment getInstance(String title) {
        if (instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG, title);
            instance = new ProjectFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }


    @Override
    protected void initData() {
        projectPresenter = new ProjectPresenter(this);
        projectPresenter.getProjectList();

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void updateProject(ProjectBean projectBean) {
        if (null != titleList) {
            titleList.clear();
        }
        for (ProjectBean.DataBean pb : projectBean.getData()) {
            Fragment fragment = null;
            titleList.add(pb.getName());
            fragment = ProjectListFragment.newInstance(pb.getId());
            fragmentList.add(fragment);
        }
        tabProject.setupWithViewPager(vpProject);//把ViewPager和TabLayout进行关联
        tabProject.setTabMode(TabLayout.MODE_SCROLLABLE);
        mProjectAdapter = new ViewPagerAdapter(getFragmentManager(), fragmentList, titleList);
        vpProject.setAdapter(mProjectAdapter);
        vpProject.setOffscreenPageLimit(5);
        vpProject.setCurrentItem(0);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        projectPresenter.destroy();
    }
}
