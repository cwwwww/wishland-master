// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity3_ViewBinding implements Unbinder {
  private MainActivity3 target;

  private View view2131624348;

  private View view2131624349;

  private View view2131624350;

  private View view2131624346;

  private View view2131624347;

  private View view2131624137;

  private View view2131624139;

  private View view2131624141;

  private View view2131624143;

  private View view2131624145;

  @UiThread
  public MainActivity3_ViewBinding(MainActivity3 target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity3_ViewBinding(final MainActivity3 target, View source) {
    this.target = target;

    View view;
    target.mainLinearLayout = Utils.findRequiredViewAsType(source, R.id.main_linearLayout, "field 'mainLinearLayout'", LinearLayout.class);
    target.topImageview = Utils.findRequiredViewAsType(source, R.id.top_imageview, "field 'topImageview'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.signout, "field 'signout' and method 'onViewClicked'");
    target.signout = Utils.castView(view, R.id.signout, "field 'signout'", TextView.class);
    view2131624348 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.setting_button, "field 'settingButton' and method 'onViewClicked'");
    target.settingButton = Utils.castView(view, R.id.setting_button, "field 'settingButton'", ImageView.class);
    view2131624349 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_username, "field 'loginUsername' and method 'onViewClicked'");
    target.loginUsername = Utils.castView(view, R.id.login_username, "field 'loginUsername'", TextView.class);
    view2131624350 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mainBettingButton = Utils.findRequiredViewAsType(source, R.id.main_betting_button, "field 'mainBettingButton'", ImageView.class);
    target.tvMain = Utils.findRequiredViewAsType(source, R.id.tv_main, "field 'tvMain'", TextView.class);
    target.mainFundButton = Utils.findRequiredViewAsType(source, R.id.main_fund_button, "field 'mainFundButton'", ImageView.class);
    target.tvFund = Utils.findRequiredViewAsType(source, R.id.tv_fund, "field 'tvFund'", TextView.class);
    target.mainLineButton = Utils.findRequiredViewAsType(source, R.id.main_line_button, "field 'mainLineButton'", ImageView.class);
    target.tvLine = Utils.findRequiredViewAsType(source, R.id.tv_line, "field 'tvLine'", TextView.class);
    target.mainMessagepageButton = Utils.findRequiredViewAsType(source, R.id.main_messagepage_button, "field 'mainMessagepageButton'", ImageView.class);
    target.tvMessage = Utils.findRequiredViewAsType(source, R.id.tv_message, "field 'tvMessage'", TextView.class);
    target.mainMessageCountText = Utils.findRequiredViewAsType(source, R.id.main_message_count_text, "field 'mainMessageCountText'", TextView.class);
    target.mainCustomerButton = Utils.findRequiredViewAsType(source, R.id.main_customer_button, "field 'mainCustomerButton'", ImageView.class);
    target.topAllRelativelayout = Utils.findRequiredViewAsType(source, R.id.top_all_relativelayout, "field 'topAllRelativelayout'", RelativeLayout.class);
    target.mianGroupButton = Utils.findRequiredViewAsType(source, R.id.mian_Group_button, "field 'mianGroupButton'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.signin, "field 'signin' and method 'onViewClicked'");
    target.signin = Utils.castView(view, R.id.signin, "field 'signin'", TextView.class);
    view2131624346 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.loginin, "field 'loginin' and method 'onViewClicked'");
    target.loginin = Utils.castView(view, R.id.loginin, "field 'loginin'", TextView.class);
    view2131624347 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_main, "field 'llMain' and method 'onViewClicked'");
    target.llMain = Utils.castView(view, R.id.ll_main, "field 'llMain'", LinearLayout.class);
    view2131624137 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_fund, "field 'llFund' and method 'onViewClicked'");
    target.llFund = Utils.castView(view, R.id.ll_fund, "field 'llFund'", LinearLayout.class);
    view2131624139 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_line, "field 'llLine' and method 'onViewClicked'");
    target.llLine = Utils.castView(view, R.id.ll_line, "field 'llLine'", LinearLayout.class);
    view2131624141 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.fl_message, "field 'flMessage' and method 'onViewClicked'");
    target.flMessage = Utils.castView(view, R.id.fl_message, "field 'flMessage'", FrameLayout.class);
    view2131624143 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_customer, "field 'llCustomer' and method 'onViewClicked'");
    target.llCustomer = Utils.castView(view, R.id.ll_customer, "field 'llCustomer'", LinearLayout.class);
    view2131624145 = view;
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
    MainActivity3 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainLinearLayout = null;
    target.topImageview = null;
    target.signout = null;
    target.settingButton = null;
    target.loginUsername = null;
    target.mainBettingButton = null;
    target.tvMain = null;
    target.mainFundButton = null;
    target.tvFund = null;
    target.mainLineButton = null;
    target.tvLine = null;
    target.mainMessagepageButton = null;
    target.tvMessage = null;
    target.mainMessageCountText = null;
    target.mainCustomerButton = null;
    target.topAllRelativelayout = null;
    target.mianGroupButton = null;
    target.signin = null;
    target.loginin = null;
    target.llMain = null;
    target.llFund = null;
    target.llLine = null;
    target.flMessage = null;
    target.llCustomer = null;

    view2131624348.setOnClickListener(null);
    view2131624348 = null;
    view2131624349.setOnClickListener(null);
    view2131624349 = null;
    view2131624350.setOnClickListener(null);
    view2131624350 = null;
    view2131624346.setOnClickListener(null);
    view2131624346 = null;
    view2131624347.setOnClickListener(null);
    view2131624347 = null;
    view2131624137.setOnClickListener(null);
    view2131624137 = null;
    view2131624139.setOnClickListener(null);
    view2131624139 = null;
    view2131624141.setOnClickListener(null);
    view2131624141 = null;
    view2131624143.setOnClickListener(null);
    view2131624143 = null;
    view2131624145.setOnClickListener(null);
    view2131624145 = null;
  }
}
