// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsCathecticDealAdapter$ViewHolder_ViewBinding implements Unbinder {
  private FundsCathecticDealAdapter.ViewHolder target;

  @UiThread
  public FundsCathecticDealAdapter$ViewHolder_ViewBinding(FundsCathecticDealAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.fundsDealItem1T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item1_T, "field 'fundsDealItem1T'", TextView.class);
    target.fundsDealItem1SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item1_SR, "field 'fundsDealItem1SR'", TextView.class);
    target.fundsDealItem2T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item2_T, "field 'fundsDealItem2T'", TextView.class);
    target.fundsDealItem2SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item2_SR, "field 'fundsDealItem2SR'", TextView.class);
    target.fundsDealItem3T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item3_T, "field 'fundsDealItem3T'", TextView.class);
    target.fundsDealItem3SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item3_SR, "field 'fundsDealItem3SR'", TextView.class);
    target.fundsDealItem4T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item4_T, "field 'fundsDealItem4T'", TextView.class);
    target.fundsDealItem4SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item4_SR, "field 'fundsDealItem4SR'", TextView.class);
    target.fundsDealItem5T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item5_T, "field 'fundsDealItem5T'", TextView.class);
    target.fundsDealItem5SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item5_SR, "field 'fundsDealItem5SR'", TextView.class);
    target.fundsDealItem6T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item6_T, "field 'fundsDealItem6T'", TextView.class);
    target.fundsDealItem6SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item6_SR, "field 'fundsDealItem6SR'", TextView.class);
    target.fundsDealItem7T = Utils.findRequiredViewAsType(source, R.id.funds_deal_item7_T, "field 'fundsDealItem7T'", TextView.class);
    target.fundsDealItem7SR = Utils.findRequiredViewAsType(source, R.id.funds_deal_item7_SR, "field 'fundsDealItem7SR'", TextView.class);
    target.fundsDealItem7Linear = Utils.findRequiredViewAsType(source, R.id.funds_deal_item7_Linear, "field 'fundsDealItem7Linear'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FundsCathecticDealAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fundsDealItem1T = null;
    target.fundsDealItem1SR = null;
    target.fundsDealItem2T = null;
    target.fundsDealItem2SR = null;
    target.fundsDealItem3T = null;
    target.fundsDealItem3SR = null;
    target.fundsDealItem4T = null;
    target.fundsDealItem4SR = null;
    target.fundsDealItem5T = null;
    target.fundsDealItem5SR = null;
    target.fundsDealItem6T = null;
    target.fundsDealItem6SR = null;
    target.fundsDealItem7T = null;
    target.fundsDealItem7SR = null;
    target.fundsDealItem7Linear = null;
  }
}
