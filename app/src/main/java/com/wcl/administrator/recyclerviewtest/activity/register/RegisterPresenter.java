package com.wcl.administrator.recyclerviewtest.activity.register;

import com.wcl.administrator.recyclerviewtest.http.HttpManager;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Created by Administrator on 2021/4/8.
 * 邮箱：
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void postRegister(String username, String password, String password2) {
        HttpManager.getInstance().HttpManager().postRegister(username, password, password2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        if (responseBean != null) {
                            if (view == null) {
                                return;
                            }
                            view.updateView(responseBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void destroy() {
        if (view != null) {
            view = null;
        }
    }
}
