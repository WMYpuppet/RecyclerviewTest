package com.wcl.administrator.recyclerviewtest.activity.login;

import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

/**
 * 作者：Created by Administrator on 2021/4/8.
 * 邮箱：
 */
public interface LoginContract {
    interface View  {
        void updateView(ResponseBean responseBean);
    }

    interface Presenter  {
        void postLogin(String username, String password);
        void destroy();
    }
}
