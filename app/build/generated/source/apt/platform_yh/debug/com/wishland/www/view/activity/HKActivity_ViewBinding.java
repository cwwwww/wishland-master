// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HKActivity_ViewBinding implements Unbinder {
  private HKActivity target;

  private View view2131624242;

  private View view2131624243;

  private View view2131624244;

  private View view2131624245;

  private View view2131624246;

  private View view2131624247;

  private View view2131624250;

  private View view2131624341;

  private View view2131624340;

  @UiThread
  public HKActivity_ViewBinding(HKActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HKActivity_ViewBinding(final HKActivity target, View source) {
    this.target = target;

    View view;
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", NestedScrollView.class);
    target.payText01 = Utils.findRequiredViewAsType(source, R.id.pay_text01, "field 'payText01'", TextView.class);
    target.paytl = Utils.findRequiredViewAsType(source, R.id.pay_tl, "field 'paytl'", ListView.class);
    target.paylt = Utils.findRequiredViewAsType(source, R.id.pay_lt, "field 'paylt'", ListView.class);
    target.payText02 = Utils.findRequiredViewAsType(source, R.id.pay_text02, "field 'payText02'", TextView.class);
    target.payUserid = Utils.findRequiredViewAsType(source, R.id.pay_userid, "field 'payUserid'", TextView.class);
    target.payEM = Utils.findRequiredViewAsType(source, R.id.pay_EM, "field 'payEM'", EditText.class);
    view = Utils.findRequiredView(source, R.id.pay_bank, "field 'payBank' and method 'onViewClicked'");
    target.payBank = Utils.castView(view, R.id.pay_bank, "field 'payBank'", Button.class);
    view2131624242 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.pay_data, "field 'payData' and method 'onViewClicked'");
    target.payData = Utils.castView(view, R.id.pay_data, "field 'payData'", Button.class);
    view2131624243 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.pay_shi, "field 'payShi' and method 'onViewClicked'");
    target.payShi = Utils.castView(view, R.id.pay_shi, "field 'payShi'", Button.class);
    view2131624244 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.pay_fen, "field 'payFen' and method 'onViewClicked'");
    target.payFen = Utils.castView(view, R.id.pay_fen, "field 'payFen'", Button.class);
    view2131624245 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.pay_miao, "field 'payMiao' and method 'onViewClicked'");
    target.payMiao = Utils.castView(view, R.id.pay_miao, "field 'payMiao'", Button.class);
    view2131624246 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.pay_type, "field 'payType' and method 'onViewClicked'");
    target.payType = Utils.castView(view, R.id.pay_type, "field 'payType'", Button.class);
    view2131624247 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.payothertype = Utils.findRequiredViewAsType(source, R.id.pay_other_type, "field 'payothertype'", EditText.class);
    target.payAddress = Utils.findRequiredViewAsType(source, R.id.pay_address, "field 'payAddress'", EditText.class);
    view = Utils.findRequiredView(source, R.id.pay_button, "field 'payButton' and method 'onViewClicked'");
    target.payButton = Utils.castView(view, R.id.pay_button, "field 'payButton'", Button.class);
    view2131624250 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.questRefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questRefresh'", MaterialRefreshLayout.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "method 'onViewClicked'");
    view2131624341 = view;
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
    HKActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.topWelcome = null;
    target.scrollView = null;
    target.payText01 = null;
    target.paytl = null;
    target.paylt = null;
    target.payText02 = null;
    target.payUserid = null;
    target.payEM = null;
    target.payBank = null;
    target.payData = null;
    target.payShi = null;
    target.payFen = null;
    target.payMiao = null;
    target.payType = null;
    target.payothertype = null;
    target.payAddress = null;
    target.payButton = null;
    target.questRefresh = null;
    target.emptyLayout = null;

    view2131624242.setOnClickListener(null);
    view2131624242 = null;
    view2131624243.setOnClickListener(null);
    view2131624243 = null;
    view2131624244.setOnClickListener(null);
    view2131624244 = null;
    view2131624245.setOnClickListener(null);
    view2131624245 = null;
    view2131624246.setOnClickListener(null);
    view2131624246 = null;
    view2131624247.setOnClickListener(null);
    view2131624247 = null;
    view2131624250.setOnClickListener(null);
    view2131624250 = null;
    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
