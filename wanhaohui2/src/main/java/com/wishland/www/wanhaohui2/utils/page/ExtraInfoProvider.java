package com.wishland.www.wanhaohui2.utils.page;

import android.widget.ListView;


import com.wishland.www.wanhaohui2.base.SuperAdapter;

import java.util.Map;

/**
 * Created by buck on 2017/10/4.
 */

public interface ExtraInfoProvider {

    void addHeaderView(ListView lv);

    void addFooterView(ListView lv);

    void addHeaderView(SuperAdapter adapter);

    void addFooterView(SuperAdapter adapter);

    /**
     * 子类可以从写此方法设置请求参数
     * 父类会自动把分页信息添加进去，子类不需要自己控制分页信息
     * 只需要提供额外的信息即可
     */
    void requestParams(Map<String, Object> params);
}
