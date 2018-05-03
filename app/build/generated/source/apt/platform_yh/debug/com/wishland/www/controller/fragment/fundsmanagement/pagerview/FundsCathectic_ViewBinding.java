// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.fragment.fundsmanagement.pagerview;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsCathectic_ViewBinding implements Unbinder {
  private FundsCathectic target;

  private View view2131624188;

  private View view2131624189;

  private View view2131624190;

  private View view2131624191;

  private View view2131624192;

  private View view2131624193;

  private View view2131624196;

  private View view2131624195;

  @UiThread
  public FundsCathectic_ViewBinding(final FundsCathectic target, View source) {
    this.target = target;

    View view;
    target.fundsDealRadiobuttonAdd = Utils.findRequiredViewAsType(source, R.id.funds_deal_radiobutton_add, "field 'fundsDealRadiobuttonAdd'", RadioButton.class);
    target.fundsDealRadiobuttonGet = Utils.findRequiredViewAsType(source, R.id.funds_deal_radiobutton_get, "field 'fundsDealRadiobuttonGet'", RadioButton.class);
    target.fundsDealRadiobuttonAtm = Utils.findRequiredViewAsType(source, R.id.funds_deal_radiobutton_atm, "field 'fundsDealRadiobuttonAtm'", RadioButton.class);
    target.fundsDealRadiobuttonOther = Utils.findRequiredViewAsType(source, R.id.funds_deal_radiobutton_other, "field 'fundsDealRadiobuttonOther'", RadioButton.class);
    target.fundsDealRadiogroup = Utils.findRequiredViewAsType(source, R.id.funds_deal_radiogroup, "field 'fundsDealRadiogroup'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.funds_deal_start_yearmd, "field 'fundsDealStartYearmd' and method 'onViewClicked'");
    target.fundsDealStartYearmd = Utils.castView(view, R.id.funds_deal_start_yearmd, "field 'fundsDealStartYearmd'", Button.class);
    view2131624188 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.funds_deal_start_hour, "field 'fundsDealStartHour' and method 'onViewClicked'");
    target.fundsDealStartHour = Utils.castView(view, R.id.funds_deal_start_hour, "field 'fundsDealStartHour'", Button.class);
    view2131624189 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.funds_deal_start_minute, "field 'fundsDealStartMinute' and method 'onViewClicked'");
    target.fundsDealStartMinute = Utils.castView(view, R.id.funds_deal_start_minute, "field 'fundsDealStartMinute'", Button.class);
    view2131624190 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.funds_deal_end_yearmd, "field 'fundsDealEndYearmd' and method 'onViewClicked'");
    target.fundsDealEndYearmd = Utils.castView(view, R.id.funds_deal_end_yearmd, "field 'fundsDealEndYearmd'", Button.class);
    view2131624191 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.funds_deal_end_hour, "field 'fundsDealEndHour' and method 'onViewClicked'");
    target.fundsDealEndHour = Utils.castView(view, R.id.funds_deal_end_hour, "field 'fundsDealEndHour'", Button.class);
    view2131624192 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.funds_deal_end_minute, "field 'fundsDealEndMinute' and method 'onViewClicked'");
    target.fundsDealEndMinute = Utils.castView(view, R.id.funds_deal_end_minute, "field 'fundsDealEndMinute'", Button.class);
    view2131624193 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.fundsDealContentLinear = Utils.findRequiredViewAsType(source, R.id.funds_deal_content_linear, "field 'fundsDealContentLinear'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.funds_deal_button, "field 'fundsDealButton' and method 'onViewClicked'");
    target.fundsDealButton = Utils.castView(view, R.id.funds_deal_button, "field 'fundsDealButton'", Button.class);
    view2131624196 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.funds_deal_content, "field 'fundsDealContent' and method 'onViewClicked'");
    target.fundsDealContent = Utils.castView(view, R.id.funds_deal_content, "field 'fundsDealContent'", Button.class);
    view2131624195 = view;
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
    FundsCathectic target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fundsDealRadiobuttonAdd = null;
    target.fundsDealRadiobuttonGet = null;
    target.fundsDealRadiobuttonAtm = null;
    target.fundsDealRadiobuttonOther = null;
    target.fundsDealRadiogroup = null;
    target.fundsDealStartYearmd = null;
    target.fundsDealStartHour = null;
    target.fundsDealStartMinute = null;
    target.fundsDealEndYearmd = null;
    target.fundsDealEndHour = null;
    target.fundsDealEndMinute = null;
    target.fundsDealContentLinear = null;
    target.fundsDealButton = null;
    target.fundsDealContent = null;

    view2131624188.setOnClickListener(null);
    view2131624188 = null;
    view2131624189.setOnClickListener(null);
    view2131624189 = null;
    view2131624190.setOnClickListener(null);
    view2131624190 = null;
    view2131624191.setOnClickListener(null);
    view2131624191 = null;
    view2131624192.setOnClickListener(null);
    view2131624192 = null;
    view2131624193.setOnClickListener(null);
    view2131624193 = null;
    view2131624196.setOnClickListener(null);
    view2131624196 = null;
    view2131624195.setOnClickListener(null);
    view2131624195 = null;
  }
}
