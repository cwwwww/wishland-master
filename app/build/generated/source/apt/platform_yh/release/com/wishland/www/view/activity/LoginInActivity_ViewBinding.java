// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginInActivity_ViewBinding implements Unbinder {
  private LoginInActivity target;

  private View view2131624337;

  private View view2131624338;

  private View view2131624339;

  private View view2131624333;

  @UiThread
  public LoginInActivity_ViewBinding(LoginInActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginInActivity_ViewBinding(final LoginInActivity target, View source) {
    this.target = target;

    View view;
    target.loginEditusername = Utils.findRequiredViewAsType(source, R.id.login_edit_username, "field 'loginEditusername'", EditText.class);
    target.loginEditpasswork = Utils.findRequiredViewAsType(source, R.id.login_edit_passwork, "field 'loginEditpasswork'", EditText.class);
    view = Utils.findRequiredView(source, R.id.login_loginin_button, "field 'loginLogininButton' and method 'onViewClicked'");
    target.loginLogininButton = Utils.castView(view, R.id.login_loginin_button, "field 'loginLogininButton'", Button.class);
    view2131624337 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_no_signin, "field 'loginNoSignin' and method 'onViewClicked'");
    target.loginNoSignin = Utils.castView(view, R.id.login_no_signin, "field 'loginNoSignin'", Button.class);
    view2131624338 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_no_passwork, "field 'loginNoPasswork' and method 'onViewClicked'");
    target.loginNoPasswork = Utils.castView(view, R.id.login_no_passwork, "field 'loginNoPasswork'", Button.class);
    view2131624339 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.login_checkbox_button = Utils.findRequiredViewAsType(source, R.id.login_checkbox_button, "field 'login_checkbox_button'", CheckBox.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.backbutton, "field 'backButton' and method 'onViewClicked'");
    target.backButton = Utils.castView(view, R.id.backbutton, "field 'backButton'", Button.class);
    view2131624333 = view;
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
    LoginInActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginEditusername = null;
    target.loginEditpasswork = null;
    target.loginLogininButton = null;
    target.loginNoSignin = null;
    target.loginNoPasswork = null;
    target.login_checkbox_button = null;
    target.emptyLayout = null;
    target.backButton = null;

    view2131624337.setOnClickListener(null);
    view2131624337 = null;
    view2131624338.setOnClickListener(null);
    view2131624338 = null;
    view2131624339.setOnClickListener(null);
    view2131624339 = null;
    view2131624333.setOnClickListener(null);
    view2131624333 = null;
  }
}
