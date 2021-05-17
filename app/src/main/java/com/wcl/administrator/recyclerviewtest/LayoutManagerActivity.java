package com.wcl.administrator.recyclerviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.wcl.administrator.recyclerviewtest.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LayoutManagerActivity extends AppCompatActivity {
    @BindView(R.id.horizontal_btn)
    Button horizontalBtn;
    @BindView(R.id.vertical_btn)
    Button verticalBtn;
    @BindView(R.id.grid_view_btn)
    Button gridViewBtn;
    @BindView(R.id.pubu_btn)
    Button pubuBtn;
    @BindView(R.id.remove_btn)
    Button removeBtn;
    @BindView(R.id.insert_btn)
    Button insertBtn;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<String> mData;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_manager);
        ButterKnife.bind(this);
        initData();
        initViews();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mData.add("item" + i);
        }
    }

    private void initViews() {
        mAdapter = new MyAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @OnClick({R.id.horizontal_btn, R.id.vertical_btn, R.id.grid_view_btn, R.id.pubu_btn, R.id.insert_btn, R.id.remove_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.horizontal_btn:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                break;
            case R.id.vertical_btn:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.grid_view_btn:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.pubu_btn:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.insert_btn:
                insert(3, "NewItem");
                break;
            case R.id.remove_btn:
                remove(0);
                break;
        }
    }

    private void insert(int position, String content) {
        mData.add(position, content);
        mAdapter.notifyItemInserted(position);
    }

    private void remove(int position) {
        mData.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
