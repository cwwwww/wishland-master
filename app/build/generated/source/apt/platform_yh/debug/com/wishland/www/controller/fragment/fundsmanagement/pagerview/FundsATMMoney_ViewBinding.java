// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.fragment.fundsmanagement.pagerview;

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

public class FundsATMMoney_ViewBinding implements Unbinder {
  private FundsATMMoney target;

  private View view2131624211;

  private View view2131624209;

  @UiThread
  public FundsATMMoney_ViewBinding(final FundsATMMoney target, View source) {
    this.target = target;

    View view;
    target.handsUserSR = Utils.findRequiredViewAsType(source, R.id.hands_user_SR, "field 'handsUserSR'", TextView.class);
    target.handsUserMameSR = Utils.findRequiredViewAsType(source, R.id.hands_user_mame_SR, "field 'handsUserMameSR'", TextView.class);
    target.handsAccountSR = Utils.findRequiredViewAsType(source, R.id.hands_account_SR, "field 'handsAccountSR'", TextView.class);
    target.handsATMAccountSR = Utils.findRequiredViewAsType(source, R.id.hands_ATM_account_SR, "field 'handsATMAccountSR'", TextView.class);
    target.handsATMSiteSR = Utils.findRequiredViewAsType(source, R.id.hands_ATM_site_SR, "field 'handsATMSiteSR'", TextView.class);
    target.handsATMMoneySREdit = Utils.findRequiredViewAsType(source, R.id.hands_ATM_money_SR_edit, "field 'handsATMMoneySREdit'", EditText.class);
    target.handsATMPasswordSREdit = Utils.findRequiredViewAsType(source, R.id.hands_ATM_password_SR_edit, "field 'handsATMPasswordSREdit'", EditText.class);
    view = Utils.findRequiredView(source, R.id.hands_ATM_button, "field 'handsATMButton' and method 'onViewClicked'");
    target.handsATMButton = Utils.castView(view, R.id.hands_ATM_button, "field 'handsATMButton'", Button.class);
    view2131624211 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.fundsAtmText1 = Utils.findRequiredViewAsType(source, R.id.funds_atm_text1, "field 'fundsAtmText1'", TextView.class);
    target.fundsAtmText2 = Utils.findRequiredViewAsType(source, R.id.funds_atm_text2, "field 'fundsAtmText2'", TextView.class);
    target.fundsAtmText3 = Utils.findRequiredViewAsType(source, R.id.funds_atm_text3, "field 'fundsAtmText3'", TextView.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.atm_all, "method 'onViewClicked'");
    view2131624209 = view;
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
    FundsATMMoney target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.handsUserSR = null;
    target.handsUserMameSR = null;
    target.handsAccountSR = null;
    target.handsATMAccountSR = null;
    target.handsATMSiteSR = null;
    target.handsATMMoneySREdit = null;
    target.handsATMPasswordSREdit = null;
    target.handsATMButton = null;
    target.fundsAtmText1 = null;
    target.fundsAtmText2 = null;
    target.fundsAtmText3 = null;
    target.emptyLayout = null;

    view2131624211.setOnClickListener(null);
    view2131624211 = null;
    view2131624209.setOnClickListener(null);
    view2131624209 = null;
  }
}
