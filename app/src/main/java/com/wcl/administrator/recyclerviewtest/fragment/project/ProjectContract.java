package com.wcl.administrator.recyclerviewtest.fragment.project;

import com.wcl.administrator.recyclerviewtest.base.BaseView;
import com.wcl.administrator.recyclerviewtest.bean.ProjectBean;

/**
 * 作者：Created by Administrator on 2021/4/25.
 * 邮箱：
 */
public interface ProjectContract {


    interface View extends BaseView {
        void updateProject(ProjectBean projectBean);
    }

    interface Presenter {
        void destroy();
        void getProjectList();
    }
}
