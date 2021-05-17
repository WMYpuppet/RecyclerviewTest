package com.wcl.administrator.recyclerviewtest.adapter;

import android.content.Context;

import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.bean.ArticleBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 作者：Created by Administrator on 2021/4/6.
 * 邮箱：
 */
public class ArticleAdapter1 extends  CommonAdapter<ArticleBean.DataBean.DatasBean> {

    public ArticleAdapter1(Context context, List<ArticleBean.DataBean.DatasBean> datas) {
        super(context, R.layout.item_article,datas);
    }

    @Override
    protected void convert(ViewHolder holder, ArticleBean.DataBean.DatasBean articleListBean, int position) {
        Boolean isNewest = articleListBean.getNiceDate().contains("小时") || articleListBean.getNiceDate().contains("分钟");
        holder.setText(R.id.tv_author, "作者：" + articleListBean.getAuthor())
                .setText(R.id.tv_chapterName,
                        "分类:" + articleListBean.getChapterName())
                .setText(R.id.tv_title, articleListBean.getTitle()
                        .replaceAll("&ldquo;", "\"")
                        .replaceAll("&rdquo;", "\"")
                        .replaceAll("&mdash;", "—"))
                .setVisible(R.id.tv_new, isNewest)
                .setText(R.id.tv_project, articleListBean.getSuperChapterName())
                .setText(R.id.tv_time, "时间：" + articleListBean.getNiceDate());
//                .setImageResource(R.id.imv_like, articleListBean.isCollect() ?
//                        R.drawable.icon_like :
//                        R.drawable.icon_unlike)
//               // 收藏和取消收藏
//                .setOnClickListener(R.id.imv_like, v -> {
//                    if (!(boolean)SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
//                        ToastUtils.showShort("请先登录");
//                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
//                        return;
//                    }
//                    if (articleListBean.isCollect()) {
//                        mPresenter.unCollectArticle(articleListBean.getId(), position);
//                    } else {
//                        mPresenter.collectArticle(articleListBean.getId(), position);
//                    }
//                })
//                .setOnClickListener(R.id.tv_project, v -> {
//                   // Intent intent = new Intent(mContext, X5WebView.class);
//                  //  intent.putExtra("mUrl",SyncStateContract.Constants.BASE_URL + articleListBean.getTags().get(0).getUrl());
//                   // intent.putExtra("mTitle", articleListBean.getTags().get(0).getName());
//                    mContext.startActivity(intent);
//              }
//                );
    }




}
