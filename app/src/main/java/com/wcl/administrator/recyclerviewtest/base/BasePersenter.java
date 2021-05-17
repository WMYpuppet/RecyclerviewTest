package com.wcl.administrator.recyclerviewtest.base;

/**
 * 作者：Created by Administrator on 2021/4/23.
 * 邮箱：
 */
public interface BasePersenter <T extends BaseView> {

    /**
     * attachView 绑定view
     *
     * @param view
     */
    void attachView(T view);

    /**
     * detachView 解除绑定
     */
    void detachView();
}