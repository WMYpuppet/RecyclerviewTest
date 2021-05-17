package com.wcl.administrator.recyclerviewtest.mvp;

import com.wcl.administrator.recyclerviewtest.bean.ArticleBean;
import com.wcl.administrator.recyclerviewtest.bean.HomeBanner;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

/**
 * 作者：Created by Administrator on 2021/4/2.
 * 邮箱：
 */
public interface HomeContract {
    interface View {

        void updateBanner(HomeBanner homeBanner);

        void updateArticle(ArticleBean.DataBean articleBean);

        void updateCollect(ResponseBean responseBean, int position);

        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter {

        void getBannerList();

        void getArticleList(int page);

        void collectArticle(int id, int position);

        void unCollectArticle(int id, int position);
    }
}
