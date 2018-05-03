// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonalAccountActivity_ViewBinding implements Unbinder {
  private PersonalAccountActivity target;

  private View view2131624438;

  private View view2131624439;

  private View view2131624420;

  private View view2131624424;

  private View view2131624341;

  private View view2131624433;

  @UiThread
  public PersonalAccountActivity_ViewBinding(PersonalAccountActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PersonalAccountActivity_ViewBinding(final PersonalAccountActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.personalaccount_user_show_hide, "field 'personalaccountUserShowHide' and method 'onViewClicked'");
    target.personalaccountUserShowHide = Utils.castView(view, R.id.personalaccount_user_show_hide, "field 'personalaccountUserShowHide'", TextView.class);
    view2131624438 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.personalaccountUserSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_user_SR, "field 'personalaccountUserSR'", TextView.class);
    target.personalaccountUsertimeSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_usertime_SR, "field 'personalaccountUsertimeSR'", TextView.class);
    target.personalaccountUserloginintimeSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_userloginintime_SR, "field 'personalaccountUserloginintimeSR'", TextView.class);
    target.personalaccountUsermoneySR = Utils.findRequiredViewAsType(source, R.id.personalaccount_usermoney_SR, "field 'personalaccountUsermoneySR'", TextView.class);
    target.personalaccountUsermoneycountSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_usermoneycount_SR, "field 'personalaccountUsermoneycountSR'", TextView.class);
    target.personalaccountPayeeusernameSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_payeeusername_SR, "field 'personalaccountPayeeusernameSR'", TextView.class);
    target.personalaccountPayeebankSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_payeebank_SR, "field 'personalaccountPayeebankSR'", TextView.class);
    target.personalaccountBankaccountSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_bankaccount_SR, "field 'personalaccountBankaccountSR'", TextView.class);
    target.personalaccountBankaddressSR = Utils.findRequiredViewAsType(source, R.id.personalaccount_bankaddress_SR, "field 'personalaccountBankaddressSR'", TextView.class);
    target.personalaccountUserdataLinearlayout = Utils.findRequiredViewAsType(source, R.id.personalaccount_userdata_linearlayout, "field 'personalaccountUserdataLinearlayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.personalaccount_pass_showhide, "field 'personalaccountPassShowhide' and method 'onViewClicked'");
    target.personalaccountPassShowhide = Utils.castView(view, R.id.personalaccount_pass_showhide, "field 'personalaccountPassShowhide'", TextView.class);
    view2131624439 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.personalaccountOldpassword = Utils.findRequiredViewAsType(source, R.id.personalaccount_oldpassword, "field 'personalaccountOldpassword'", EditText.class);
    target.personalaccountNewpassword = Utils.findRequiredViewAsType(source, R.id.personalaccount_newpassword, "field 'personalaccountNewpassword'", EditText.class);
    target.personalaccountMnewpassword = Utils.findRequiredViewAsType(source, R.id.personalaccount_mnewpassword, "field 'personalaccountMnewpassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.personalaccount_oknewpassword, "field 'personalaccountOknewpassword' and method 'onViewClicked'");
    target.personalaccountOknewpassword = Utils.castView(view, R.id.personalaccount_oknewpassword, "field 'personalaccountOknewpassword'", Button.class);
    view2131624420 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.personalaccountGetmoneyoldpassword = Utils.findRequiredViewAsType(source, R.id.personalaccount_getmoneyoldpassword, "field 'personalaccountGetmoneyoldpassword'", EditText.class);
    target.personalaccountGetmoneynewpassword = Utils.findRequiredViewAsType(source, R.id.personalaccount_getmoneynewpassword, "field 'personalaccountGetmoneynewpassword'", EditText.class);
    target.prepersonalaccountGetmoneynewpassword = Utils.findRequiredViewAsType(source, R.id.prepersonalaccount_getmoneynewpassword, "field 'prepersonalaccountGetmoneynewpassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.personalaccount_okgetmoneynewpassword, "field 'personalaccountOkgetmoneynewpassword' and method 'onViewClicked'");
    target.personalaccountOkgetmoneynewpassword = Utils.castView(view, R.id.personalaccount_okgetmoneynewpassword, "field 'personalaccountOkgetmoneynewpassword'", Button.class);
    view2131624424 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.personalaccountChangepassLinear = Utils.findRequiredViewAsType(source, R.id.personalaccount_changepass_linear, "field 'personalaccountChangepassLinear'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "field 'topFanhui' and method 'onViewClicked'");
    target.topFanhui = Utils.castView(view, R.id.top_fanhui, "field 'topFanhui'", ImageView.class);
    view2131624341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.persion_updata_bindbank, "method 'onViewClicked'");
    view2131624433 = view;
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
    PersonalAccountActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.personalaccountUserShowHide = null;
    target.personalaccountUserSR = null;
    target.personalaccountUsertimeSR = null;
    target.personalaccountUserloginintimeSR = null;
    target.personalaccountUsermoneySR = null;
    target.personalaccountUsermoneycountSR = null;
    target.personalaccountPayeeusernameSR = null;
    target.personalaccountPayeebankSR = null;
    target.personalaccountBankaccountSR = null;
    target.personalaccountBankaddressSR = null;
    target.personalaccountUserdataLinearlayout = null;
    target.personalaccountPassShowhide = null;
    target.personalaccountOldpassword = null;
    target.personalaccountNewpassword = null;
    target.personalaccountMnewpassword = null;
    target.personalaccountOknewpassword = null;
    target.personalaccountGetmoneyoldpassword = null;
    target.personalaccountGetmoneynewpassword = null;
    target.prepersonalaccountGetmoneynewpassword = null;
    target.personalaccountOkgetmoneynewpassword = null;
    target.personalaccountChangepassLinear = null;
    target.topFanhui = null;
    target.topWelcome = null;
    target.questrefresh = null;
    target.emptyLayout = null;

    view2131624438.setOnClickListener(null);
    view2131624438 = null;
    view2131624439.setOnClickListener(null);
    view2131624439 = null;
    view2131624420.setOnClickListener(null);
    view2131624420 = null;
    view2131624424.setOnClickListener(null);
    view2131624424 = null;
    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624433.setOnClickListener(null);
    view2131624433 = null;
  }
}
