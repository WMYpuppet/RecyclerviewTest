package com.wcl.administrator.recyclerviewtest.adapter;

import android.view.View;

/**
 * Created by Administrator on 2018/5/7.
 */
public interface OnItemClickHandler {
    /**
     * 点击事件
     * @param view
     * @param position
     */
    void itemClick(View view, int position);

    /**
     * 长按事件
     * @param view
     * @param position
     */
    void itemLongClick(View view, int position);
}
