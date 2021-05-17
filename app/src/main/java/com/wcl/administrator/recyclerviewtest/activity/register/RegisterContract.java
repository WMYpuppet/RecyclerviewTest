package com.wcl.administrator.recyclerviewtest.activity.register;

import com.wcl.administrator.recyclerviewtest.http.ResponseBean;

/**
 * 作者：Created by Administrator on 2021/4/8.
 * 邮箱：
 */
public interface RegisterContract {

    interface View  {
        void updateView(ResponseBean responseBean);
    }

    interface Presenter  {
        void postRegister(String username, String password, String password2);
        void destroy();
    }
}
