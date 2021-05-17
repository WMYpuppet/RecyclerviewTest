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
import com.wcl.administrator.recyclerviewtest.bean.ProjectListBean;
import com.wcl.administrator.recyclerviewtest.util.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Created by Administrator on 2021/4/26.
 * 邮箱：
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<ProjectListBean.DataBean.DatasBean> mData;

    public ProjectListAdapter(Context mContext, List<ProjectListBean.DataBean.DatasBean> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_project_list, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ImageLoaderUtil.LoadImage(mContext, mData.get(i).getEnvelopePic(), myViewHolder.imvProject);
        myViewHolder.tvProjectName.setText(mData.get(i).getTitle());
        myViewHolder.tvAuthor.setText(mData.get(i).getAuthor());
        myViewHolder.tvTime.setText(mData.get(i).getNiceDate());
        myViewHolder.tvProjectContent.setText(mData.get(i).getDesc());
        myViewHolder.imvLike.setImageResource(mData.get(i).isCollect() ? R.mipmap.collect : R.mipmap.uncollect);
        myViewHolder.itemView.setTag(i);
        myViewHolder.imvLike.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.imv_like:
                    mOnItemClickListener.onClick(v, ViewName.IMVLIKE, position);
                    break;
                default:
                    mOnItemClickListener.onClick(v, ViewName.ITEM, position);
                    break;
            }
        }


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imv_project)
        ImageView imvProject;
        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_project_content)
        TextView tvProjectContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.imv_like)
        ImageView imvLike;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(ProjectListAdapter.this);
            imvLike.setOnClickListener(ProjectListAdapter.this);

        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * item里面有多个控件可以点击
     */
    public enum ViewName {
        ITEM,
        IMVLIKE
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, ViewName viewName, int position);
    }


}
