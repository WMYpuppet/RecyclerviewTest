package com.wcl.administrator.recyclerviewtest.fragment.navi;

import com.wcl.administrator.recyclerviewtest.bean.NaviBean;
import com.wcl.administrator.recyclerviewtest.http.HttpManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Created by Administrator on 2021/5/8.
 * 邮箱：
 */
public class NaviPresenter  implements NaviContract.Presenter{
    private NaviContract.View mView;

    public NaviPresenter(NaviContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {
        HttpManager.getInstance().HttpManager().getNaviData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NaviBean>() {
                    @Override
                    public void accept(NaviBean naviBean) throws Exception {
                          mView.updateView(naviBean.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onFail();
                    }
                });

    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
}
