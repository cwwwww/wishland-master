// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.fragment.fundsmanagement;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.CustomViewPager;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsManagementPage_ViewBinding implements Unbinder {
  private FundsManagementPage target;

  private View view2131624199;

  private View view2131624200;

  private View view2131624340;

  @UiThread
  public FundsManagementPage_ViewBinding(final FundsManagementPage target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.funds_setting_account, "field 'fundsSettingAccount' and method 'onViewClicked'");
    target.fundsSettingAccount = Utils.castView(view, R.id.funds_setting_account, "field 'fundsSettingAccount'", TextView.class);
    view2131624199 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.fundsSetaccountMoney = Utils.findRequiredViewAsType(source, R.id.funds_setaccount_money, "field 'fundsSetaccountMoney'", TextView.class);
    target.fundsTablayout = Utils.findRequiredViewAsType(source, R.id.funds_tablayout, "field 'fundsTablayout'", TabLayout.class);
    target.fundsViewpager = Utils.findRequiredViewAsType(source, R.id.funds_viewpager, "field 'fundsViewpager'", CustomViewPager.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.funds_setting, "method 'onViewClicked'");
    view2131624200 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.button_pc, "method 'onViewClicked'");
    view2131624340 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FundsManagementPage target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fundsSettingAccount = null;
    target.fundsSetaccountMoney = null;
    target.fundsTablayout = null;
    target.fundsViewpager = null;
    target.questrefresh = null;
    target.emptyLayout = null;

    view2131624199.setOnClickListener(null);
    view2131624199 = null;
    view2131624200.setOnClickListener(null);
    view2131624200 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
