// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
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

public class MainActivity2_ViewBinding implements Unbinder {
  private MainActivity2 target;

  private View view2131624348;

  private View view2131624349;

  private View view2131624350;

  private View view2131624130;

  private View view2131624131;

  private View view2131624132;

  private View view2131624133;

  private View view2131624135;

  private View view2131624346;

  private View view2131624347;

  @UiThread
  public MainActivity2_ViewBinding(MainActivity2 target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity2_ViewBinding(final MainActivity2 target, View source) {
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
    view = Utils.findRequiredView(source, R.id.main_betting_button, "field 'mainBettingButton' and method 'onViewClicked'");
    target.mainBettingButton = Utils.castView(view, R.id.main_betting_button, "field 'mainBettingButton'", Button.class);
    view2131624130 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.main_fund_button, "field 'mainFundButton' and method 'onViewClicked'");
    target.mainFundButton = Utils.castView(view, R.id.main_fund_button, "field 'mainFundButton'", Button.class);
    view2131624131 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.main_line_button, "field 'mainLineButton' and method 'onViewClicked'");
    target.mainLineButton = Utils.castView(view, R.id.main_line_button, "field 'mainLineButton'", Button.class);
    view2131624132 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mainMessageCountText = Utils.findRequiredViewAsType(source, R.id.main_message_count_text, "field 'mainMessageCountText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.main_customer_button, "field 'mainCustomerButton' and method 'onViewClicked'");
    target.mainCustomerButton = Utils.castView(view, R.id.main_customer_button, "field 'mainCustomerButton'", Button.class);
    view2131624133 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.main_messagepage_button, "field 'mainMessagepageButton' and method 'onViewClicked'");
    target.mainMessagepageButton = Utils.castView(view, R.id.main_messagepage_button, "field 'mainMessagepageButton'", Button.class);
    view2131624135 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
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
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity2 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainLinearLayout = null;
    target.topImageview = null;
    target.signout = null;
    target.settingButton = null;
    target.loginUsername = null;
    target.mainBettingButton = null;
    target.mainFundButton = null;
    target.mainLineButton = null;
    target.mainMessageCountText = null;
    target.mainCustomerButton = null;
    target.mainMessagepageButton = null;
    target.topAllRelativelayout = null;
    target.mianGroupButton = null;
    target.signin = null;
    target.loginin = null;

    view2131624348.setOnClickListener(null);
    view2131624348 = null;
    view2131624349.setOnClickListener(null);
    view2131624349 = null;
    view2131624350.setOnClickListener(null);
    view2131624350 = null;
    view2131624130.setOnClickListener(null);
    view2131624130 = null;
    view2131624131.setOnClickListener(null);
    view2131624131 = null;
    view2131624132.setOnClickListener(null);
    view2131624132 = null;
    view2131624133.setOnClickListener(null);
    view2131624133 = null;
    view2131624135.setOnClickListener(null);
    view2131624135 = null;
    view2131624346.setOnClickListener(null);
    view2131624346 = null;
    view2131624347.setOnClickListener(null);
    view2131624347 = null;
  }
}
