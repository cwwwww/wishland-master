// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OnlinePaymentActivity_ViewBinding implements Unbinder {
  private OnlinePaymentActivity target;

  private View view2131624341;

  private View view2131624410;

  private View view2131624340;

  @UiThread
  public OnlinePaymentActivity_ViewBinding(OnlinePaymentActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OnlinePaymentActivity_ViewBinding(final OnlinePaymentActivity target, View source) {
    this.target = target;

    View view;
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.ot_minmoney = Utils.findRequiredViewAsType(source, R.id.ot_minmoney, "field 'ot_minmoney'", TextView.class);
    target.otUsername = Utils.findRequiredViewAsType(source, R.id.ot_username, "field 'otUsername'", TextView.class);
    target.otCurrent = Utils.findRequiredViewAsType(source, R.id.ot_current, "field 'otCurrent'", TextView.class);
    target.otMoney = Utils.findRequiredViewAsType(source, R.id.ot_money, "field 'otMoney'", EditText.class);
    target.questRefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questRefresh'", MaterialRefreshLayout.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "field 'topFanhui' and method 'onViewClicked'");
    target.topFanhui = Utils.castView(view, R.id.top_fanhui, "field 'topFanhui'", ImageView.class);
    view2131624341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ot_button, "field 'otButton' and method 'onViewClicked'");
    target.otButton = Utils.castView(view, R.id.ot_button, "field 'otButton'", Button.class);
    view2131624410 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.otradiogroup = Utils.findRequiredViewAsType(source, R.id.ot_radiogroup, "field 'otradiogroup'", RadioGroup.class);
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
    OnlinePaymentActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.topWelcome = null;
    target.ot_minmoney = null;
    target.otUsername = null;
    target.otCurrent = null;
    target.otMoney = null;
    target.questRefresh = null;
    target.emptyLayout = null;
    target.topFanhui = null;
    target.otButton = null;
    target.otradiogroup = null;

    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624410.setOnClickListener(null);
    view2131624410 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
