package com.wcl.administrator.recyclerviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wcl.administrator.recyclerviewtest.activity.X5WebView;
import com.wcl.administrator.recyclerviewtest.adapter.ArticleAdapter;
import com.wcl.administrator.recyclerviewtest.bean.ArticleBean;
import com.wcl.administrator.recyclerviewtest.bean.HomeBanner;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;
import com.wcl.administrator.recyclerviewtest.mvp.HomeContract;
import com.wcl.administrator.recyclerviewtest.mvp.HomePresenter;
import com.wcl.administrator.recyclerviewtest.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RxjavaRetrofitMVP extends AppCompatActivity implements HomeContract.View {

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.banner)
    Banner banner;

    int page = 0;
    int pageCount = 0;
    Unbinder unbinder;
    private HomePresenter mPresenter;

    ArticleAdapter mAdapter;
    List<ArticleBean.DataBean.DatasBean> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_retrofit_mvp);
        unbinder = ButterKnife.bind(this);
        mPresenter = new HomePresenter(this);

        mAdapter = new ArticleAdapter(this, articleList);
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        rvArticle.setAdapter(mAdapter);
        rvArticle.setNestedScrollingEnabled(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                }
                mPresenter.getArticleList(page);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPresenter.getArticleList(page);
                refreshLayout.finishRefresh();
            }
        });
        mPresenter.getBannerList();
        mPresenter.getArticleList(page);

    }


    @Override
    public void updateBanner(final HomeBanner homeBanners) {
        List<String> listBanner = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();
        final List<String> listUrl = new ArrayList<>();
        for (HomeBanner.DataBean homeBanner : homeBanners.getData()) {
            listBanner.add(homeBanner.getImagePath());
            listTitle.add(homeBanner.getTitle());
            listUrl.add(homeBanner.getUrl());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(listBanner);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(listTitle);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(RxjavaRetrofitMVP.this, X5WebView.class);
                intent.putExtra("mUrl", homeBanners.getData().get(position).getUrl());
                startActivity(intent);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void updateArticle(ArticleBean.DataBean articleBean) {
        page = articleBean.getCurPage();
        pageCount = articleBean.getPageCount();
        if (page == 0) {
            articleList.clear();
        }
        articleList.addAll(articleBean.getDatas());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {

    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {

    }


    /**
     * 如果你需要考虑更好的体验，可以这么操作
     */
    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected()) {
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        //开始轮播
        banner.startAutoPlay();
    }



    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
