package com.wcl.administrator.recyclerviewtest.activity.login;

import com.wcl.administrator.recyclerviewtest.bean.LoginBean;
import com.wcl.administrator.recyclerviewtest.http.HttpManager;
import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：Created by Administrator on 2021/4/8.
 * 邮箱：
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void postLogin(String username, String password) {
        HttpManager.getInstance().HttpManager().postLogin(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean<LoginBean>>() {
                    @Override
                    public void accept(ResponseBean<LoginBean> loginBeanResponseBean) throws Exception {
                        if (loginBeanResponseBean != null) {
                            if (view == null) {
                                return;
                            }
                            view.updateView(loginBeanResponseBean);
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
