package com.wishland.www.wanhaohui2.view.fragment;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by JayCruz on 2017/12/19.
 */

public class UmengFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        try {
            String name = getClass().getSimpleName();

            MobclickAgent.onPageStart(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            String name = getClass().getSimpleName();
            MobclickAgent.onPageEnd(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
