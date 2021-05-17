package com.wcl.administrator.recyclerviewtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by Administrator on 2021/4/25.
 * 邮箱：
 */
public class ViewPagerAdapter  extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> listTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> listTitle) {
        super(fm);
        this.mFragments = mFragments;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }


}
