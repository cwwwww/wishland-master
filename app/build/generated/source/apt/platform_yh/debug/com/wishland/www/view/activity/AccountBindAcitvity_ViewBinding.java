// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AccountBindAcitvity_ViewBinding implements Unbinder {
  private AccountBindAcitvity target;

  private View view2131624122;

  private View view2131624126;

  private View view2131624341;

  @UiThread
  public AccountBindAcitvity_ViewBinding(AccountBindAcitvity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccountBindAcitvity_ViewBinding(final AccountBindAcitvity target, View source) {
    this.target = target;

    View view;
    target.accountUser = Utils.findRequiredViewAsType(source, R.id.account_user, "field 'accountUser'", TextView.class);
    target.accountUserMame = Utils.findRequiredViewAsType(source, R.id.account_user_mame, "field 'accountUserMame'", TextView.class);
    view = Utils.findRequiredView(source, R.id.account_account_bank, "field 'accountAccountBank' and method 'onViewClicked'");
    target.accountAccountBank = Utils.castView(view, R.id.account_account_bank, "field 'accountAccountBank'", TextView.class);
    view2131624122 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.accountAccountNumber = Utils.findRequiredViewAsType(source, R.id.account_account_number, "field 'accountAccountNumber'", EditText.class);
    target.accountBankAddress = Utils.findRequiredViewAsType(source, R.id.account_bank_address, "field 'accountBankAddress'", EditText.class);
    view = Utils.findRequiredView(source, R.id.account_button, "field 'accountButton' and method 'onViewClicked'");
    target.accountButton = Utils.castView(view, R.id.account_button, "field 'accountButton'", Button.class);
    view2131624126 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.fundsAtmText1 = Utils.findRequiredViewAsType(source, R.id.funds_atm_text1, "field 'fundsAtmText1'", TextView.class);
    target.fundsAtmText2 = Utils.findRequiredViewAsType(source, R.id.funds_atm_text2, "field 'fundsAtmText2'", TextView.class);
    target.fundsAtmText3 = Utils.findRequiredViewAsType(source, R.id.funds_atm_text3, "field 'fundsAtmText3'", TextView.class);
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.accountBankWpassword = Utils.findRequiredViewAsType(source, R.id.account_bank_wpassword, "field 'accountBankWpassword'", EditText.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "method 'onViewClicked'");
    view2131624341 = view;
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
    AccountBindAcitvity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.accountUser = null;
    target.accountUserMame = null;
    target.accountAccountBank = null;
    target.accountAccountNumber = null;
    target.accountBankAddress = null;
    target.accountButton = null;
    target.fundsAtmText1 = null;
    target.fundsAtmText2 = null;
    target.fundsAtmText3 = null;
    target.topWelcome = null;
    target.accountBankWpassword = null;
    target.emptyLayout = null;

    view2131624122.setOnClickListener(null);
    view2131624122 = null;
    view2131624126.setOnClickListener(null);
    view2131624126 = null;
    view2131624341.setOnClickListener(null);
    view2131624341 = null;
  }
}
