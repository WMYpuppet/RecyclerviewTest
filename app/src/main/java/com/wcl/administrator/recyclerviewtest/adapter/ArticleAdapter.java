package com.wcl.administrator.recyclerviewtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.bean.ArticleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Created by Administrator on 2021/4/6.
 * 邮箱：
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private Context mContent;
    private List<ArticleBean.DataBean.DatasBean> mData;

    public ArticleAdapter(Context mContent, List<ArticleBean.DataBean.DatasBean> mData) {
        this.mContent = mContent;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.item_article, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Boolean isNewest = mData.get(position).getNiceDate().contains("小时") || mData.get(position).getNiceDate().contains("分钟");
        myViewHolder.tvAuthor.setText("作者：" + mData.get(position).getAuthor());
        myViewHolder.tvTitle.setText(mData.get(position).getTitle());
        myViewHolder.tvChapterName.setText("分类"+mData.get(position).getChapterName());
        if (isNewest) {
            myViewHolder.tvNew.setVisibility(View.VISIBLE);
        }
        myViewHolder.tvProject.setText(mData.get(position).getSuperChapterName());
        myViewHolder.tvTime.setText(mData.get(position).getNiceDate());
        myViewHolder.imvLike.setImageResource(mData.get(position).isCollect()?R.mipmap.collect:R.mipmap.uncollect);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_chapterName)
        TextView tvChapterName;
        @BindView(R.id.imv_like)
        ImageView imvLike;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.tv_project)
        TextView tvProject;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
