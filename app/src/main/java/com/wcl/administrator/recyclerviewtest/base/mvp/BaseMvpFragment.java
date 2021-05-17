package com.wcl.administrator.recyclerviewtest.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wcl.administrator.recyclerviewtest.base.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Created by Administrator on 2021/4/23.
 * 邮箱：
 */
public abstract class BaseMvpFragment extends Fragment implements BaseView {

    protected Unbinder unbinder;
    protected View mRootView;


    @Override
    public void onResume() {
        super.onResume();
        if (!NetworkUtils.isConnected()) {
            onNetError();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);

        initData();
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initData();


    @Override
    public void showLoading() {
    }

    @Override
    public void closeLoading() {
    }

    @Override
    public void onSucess() {
    }

    @Override
    public void onFail() {
        ToastUtils.showShort("获取数据失败");
    }

    @Override
    public void onNetError() {
        ToastUtils.showShort("请检查网络是否连接");
    }

    @Override
    public void onReLoad() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
