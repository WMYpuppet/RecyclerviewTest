package com.wcl.administrator.recyclerviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wcl.administrator.recyclerviewtest.adapter.MyRecyclerViewAdapter;
import com.wcl.administrator.recyclerviewtest.bean.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewBaseActivity extends AppCompatActivity {

    @BindView(R.id.rv_recyclerView)
    RecyclerView rvRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Data> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_base);

        ButterKnife.bind(this);
        initData();

        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));//控制布局为LinearLayout或者是GridView或者是瀑布流布局
        adapter = new MyRecyclerViewAdapter(list, this);
        rvRecyclerView.setAdapter(adapter);
        // 设置item及item中控件的点击事件
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, MyRecyclerViewAdapter.ViewName viewName, int position) {
                switch (v.getId()) {
                    case R.id.btn_agree:
                        Toast.makeText(getApplicationContext(), "同意" + (position + 1)+viewName, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_refuse:
                        Toast.makeText(getApplicationContext(), "拒绝" + (position + 1)+viewName, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "你点击了item按钮" + (position + 1)+viewName, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new Data(R.mipmap.ic_launcher, "小孔", "让我们成为好友吧！"));
        list.add(new Data(R.mipmap.ic_launcher, "小姬", "让我们成为好友吧！"));
        list.add(new Data(R.mipmap.ic_launcher, "老胡", "让我们成为好友吧！"));
        list.add(new Data(R.mipmap.ic_launcher, "小丽", "让我们成为好友吧！"));
        list.add(new Data(R.mipmap.ic_launcher, "小马", "让我们成为好友吧！"));
    }


}
