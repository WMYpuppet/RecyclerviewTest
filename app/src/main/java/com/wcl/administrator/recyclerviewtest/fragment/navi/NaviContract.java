package com.wcl.administrator.recyclerviewtest.fragment.navi;

import com.wcl.administrator.recyclerviewtest.base.BaseView;
import com.wcl.administrator.recyclerviewtest.bean.NaviBean;

import java.util.List;

/**
 * 作者：Created by Administrator on 2021/5/8.
 * 邮箱：
 */
public interface NaviContract {

    interface View extends BaseView {
        void updateView(List<NaviBean.DataBean> dataBean);
    }

    interface Presenter {
        void getData();
        void destroy();
    }
}
