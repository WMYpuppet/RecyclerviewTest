package com.wcl.administrator.recyclerviewtest.fragment.project.projectlist;

import com.wcl.administrator.recyclerviewtest.base.BaseView;
import com.wcl.administrator.recyclerviewtest.bean.ProjectListBean;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

/**
 * 作者：Created by Administrator on 2021/4/25.
 * 邮箱：
 */
public interface ProjectListContract {
    interface View extends BaseView {
        void updateProject(ProjectListBean.DataBean projectListBean);

        void updateCollect(ResponseBean responseBean, int position);

        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter {
        void getProjectList(int page, int cid);

        void collectArticle(int id, int position);

        void unCollectArticle(int id, int position);

        void destroy();
    }
}
