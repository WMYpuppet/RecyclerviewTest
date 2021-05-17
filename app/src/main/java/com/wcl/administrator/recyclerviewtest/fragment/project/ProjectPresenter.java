package com.wcl.administrator.recyclerviewtest.fragment.project;

import com.wcl.administrator.recyclerviewtest.bean.ProjectBean;
import com.wcl.administrator.recyclerviewtest.http.HttpManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Created by Administrator on 2021/4/25.
 * 邮箱：
 */
public class ProjectPresenter implements ProjectContract.Presenter {
    private ProjectContract.View mView;

    public ProjectPresenter(ProjectContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getProjectList() {
        HttpManager.getInstance().HttpManager().getProjectSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProjectBean>() {
                    @Override
                    public void accept(ProjectBean projectBean) throws Exception {
                        if (mView == null) {
                            return;
                        }
                        mView.updateProject(projectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (mView == null) {
                            return;
                        }
                        mView.onFail();
                    }
                });
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }

}
