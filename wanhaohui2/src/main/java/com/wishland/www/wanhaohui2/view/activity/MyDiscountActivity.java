package com.wishland.www.wanhaohui2.view.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.widget.MsgView;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.view.adapter.MyTableViewAdapter;
import com.wishland.www.wanhaohui2.view.fragment.MyDiscountListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/10/11.
 */

public class MyDiscountActivity extends BaseStyleActivity implements MyDiscountListFragment.CallBackValue {
    Unbinder unbinder;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager main_viewpager;

    List<String> tableTitleList = new ArrayList<>();
    List<Fragment> viewList = new ArrayList<>();
    private MyTableViewAdapter tableViewAdapter;
    private MyDiscountListFragment myDiscountListFragment1;
    private MyDiscountListFragment myDiscountListFragment2;
    private MyDiscountListFragment myDiscountListFragment3;

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initDate() {
        setTitle("我的优惠");
        String aid = getIntent().getStringExtra("id");
        if (aid != null && myDiscountListFragment2 != null && !"".equals(aid)) {
            mTabLayout.setCurrentTab(1);
            myDiscountListFragment2.applyDiscountActivity(aid);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_my_discount, R.layout.base_toolbar_back);
        unbinder = ButterKnife.bind(this);

        tableTitleList.add("全部优惠");
        tableTitleList.add("已申请");
        tableTitleList.add("失效");
        myDiscountListFragment1 = MyDiscountListFragment.newInstance("0");
        viewList.add(myDiscountListFragment1);

        myDiscountListFragment2 = MyDiscountListFragment.newInstance("1");
        viewList.add(myDiscountListFragment2);

        myDiscountListFragment3 = MyDiscountListFragment.newInstance("2");
        viewList.add(myDiscountListFragment3);

        tableViewAdapter = new MyTableViewAdapter(getSupportFragmentManager(), viewList, tableTitleList);
        main_viewpager.setAdapter(tableViewAdapter);
        //预加载所有页面
        main_viewpager.setOffscreenPageLimit(3);

        mTabLayout.setViewPager(main_viewpager);

//        mTabLayout.setCurrentTab(1);


//        initTab();

//        mTabLayout.addTab(mTabLayout.newTab().setText(tableTitleList.get(0)));
//        viewList.add(MyDiscountListFragment.newInstance("0"));
//        mTabLayout.addTab(mTabLayout.newTab().setText(tableTitleList.get(1)));
//        viewList.add(MyDiscountListFragment.newInstance("1"));
//        mTabLayout.addTab(mTabLayout.newTab().setText(tableTitleList.get(2)));
//        viewList.add(MyDiscountListFragment.newInstance("2"));
//
//        tableViewAdapter = new MyTableViewAdapter(getSupportFragmentManager(), viewList, tableTitleList);
//        main_viewpager.setAdapter(tableViewAdapter);
//        //预加载所有页面
//        main_viewpager.setOffscreenPageLimit(3);
//
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        //设置TabLayout的指示器高度为0，即达到了隐藏Indicator的目的
////        mTabLayout.setSelectedTabIndicatorHeight(0);
//        mTabLayout.setupWithViewPager(main_viewpager);
    }

//    private void initTab() {
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        mTabLayout.setSelectedTabIndicatorHeight(0);
//        ViewCompat.setElevation(mTabLayout, 10);
//        mTabLayout.setupWithViewPager(main_viewpager);
//        for (int i = 0; i < tableTitleList.size(); i++) {
//            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
//            if (itemTab != null) {
//                itemTab.setCustomView(R.layout.item_tab_my_discount);
//                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id.tv_tab_title);
//                itemTv.setText(tableTitleList.get(i));
//            }
//        }
//        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    @Override
    public void SendCounts1(String counts) {
        mTabLayout.showMsg(1, Integer.parseInt(counts));
        /**设置未读消息消息的背景*/
        MsgView msgView = mTabLayout.getMsgView(1);
        if (msgView != null) {
            msgView.setBackgroundColor(Color.parseColor("#FD6D2D"));
        }
    }

    @Override
    public void SendCounts2(String counts) {
        mTabLayout.showMsg(2, Integer.parseInt(counts));
        MsgView msgView2 = mTabLayout.getMsgView(2);
        if (msgView2 != null) {
            msgView2.setBackgroundColor(Color.parseColor("#FD6D2D"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
