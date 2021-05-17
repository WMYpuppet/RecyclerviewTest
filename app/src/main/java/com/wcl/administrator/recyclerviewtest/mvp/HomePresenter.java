package com.wcl.administrator.recyclerviewtest.mvp;

import android.util.Log;

import com.wcl.administrator.recyclerviewtest.bean.ArticleBean;
import com.wcl.administrator.recyclerviewtest.bean.HomeBanner;
import com.wcl.administrator.recyclerviewtest.http.HttpManager;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Created by Administrator on 2021/4/2.
 * 邮箱：
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getBannerList() {
        HttpManager.getInstance().HttpManager().getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeBanner>() {
                    @Override
                    public void accept(HomeBanner homeBanner) throws Exception {
                        mView.updateBanner(homeBanner);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


    }


    @Override
    public void getArticleList(int page) {
        HttpManager.getInstance().HttpManager().getArticle(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleBean>() {
                    @Override
                    public void accept(ArticleBean articleBean) throws Exception {
                        mView.updateArticle(articleBean.getData());

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("throwable",throwable.getMessage());
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
                });
    }


}
