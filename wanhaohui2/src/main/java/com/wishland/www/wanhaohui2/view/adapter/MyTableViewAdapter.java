package com.wishland.www.wanhaohui2.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Tom on 2017/10/14.
 */

public class MyTableViewAdapter extends FragmentPagerAdapter {
    List<Fragment> pagerList;
    List<String> titleList;
    public MyTableViewAdapter(FragmentManager fm , List<Fragment> pagerList , List<String> titleList) {
        super(fm);
        this.pagerList = pagerList;
        this.titleList = titleList;
    }
    @Override
    public int getCount() {
        return pagerList != null ? pagerList.size() : 0;
    }
    @Override
    public Fragment getItem(int position) {
        return pagerList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
