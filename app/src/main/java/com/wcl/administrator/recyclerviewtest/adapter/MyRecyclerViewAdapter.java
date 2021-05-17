package com.wcl.administrator.recyclerviewtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wcl.administrator.recyclerviewtest.R;
import com.wcl.administrator.recyclerviewtest.bean.Data;

import java.util.List;


/**
 * 作者：Created by Administrator on 2021/4/7.
 * 邮箱：
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {
    private List<Data> list;//数据源
    private Context context;//上下文
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    public MyRecyclerViewAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_base, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Data data = list.get(position);
        myViewHolder.ivIcon.setBackgroundResource(data.getIcon());
        myViewHolder.tvUsername.setText(data.getUsername());
        myViewHolder.tvMessage.setText(data.getMessage());
        myViewHolder.itemView.setTag(position);
        myViewHolder.btnAgree.setTag(position);
        myViewHolder.btnRefuse.setTag(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.rv_recyclerView:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvUsername,tvMessage;
        private Button btnAgree,btnRefuse;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvMessage = itemView.findViewById(R.id.tv_message);
            btnAgree = itemView.findViewById(R.id.btn_agree);
            btnRefuse = itemView.findViewById(R.id.btn_refuse);
            itemView.setOnClickListener(MyRecyclerViewAdapter.this);
            btnAgree.setOnClickListener(MyRecyclerViewAdapter.this);
            btnRefuse.setOnClickListener(MyRecyclerViewAdapter.this);
        }
    }

    public enum ViewName {
        ITEM,
        PRACTISE
    }
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
    }


    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

}
