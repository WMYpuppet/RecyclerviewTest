package com.wcl.administrator.recyclerviewtest.fragment.project.projectlist;

import com.wcl.administrator.recyclerviewtest.bean.ProjectListBean;
import com.wcl.administrator.recyclerviewtest.http.HttpManager;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Created by Administrator on 2021/4/25.
 * 邮箱：
 */
public class ProjectListPresenter implements ProjectListContract.Presenter {

    private ProjectListContract.View mView;

    public ProjectListPresenter(ProjectListContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectList(int page, int cid) {
        HttpManager.getInstance().HttpManager().getProjectList(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProjectListBean>() {
                    @Override
                    public void accept(ProjectListBean projectListBean) throws Exception {
                        if (mView == null) {
                            return;
                        }
                        mView.updateProject(projectListBean.getData());
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
    public void collectArticle(int id, final int position) {
        HttpManager.getInstance().HttpManager().insideCollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.updateCollect(responseBean, position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onFail();
                    }
                });
    }

    @Override
    public void unCollectArticle(int id, final int position) {
        HttpManager.getInstance().HttpManager().articleListUncollect(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.updateUnCollect(responseBean, position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
