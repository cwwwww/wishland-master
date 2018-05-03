// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.CustomListView;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlipayActivity_ViewBinding implements Unbinder {
  private AlipayActivity target;

  private View view2131624154;

  private View view2131624158;

  private View view2131624159;

  private View view2131624341;

  private View view2131624340;

  @UiThread
  public AlipayActivity_ViewBinding(AlipayActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AlipayActivity_ViewBinding(final AlipayActivity target, View source) {
    this.target = target;

    View view;
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.alipaytypename = Utils.findRequiredViewAsType(source, R.id.alipay_type_name, "field 'alipaytypename'", TextView.class);
    target.alipaysetid = Utils.findRequiredViewAsType(source, R.id.alipay_setid, "field 'alipaysetid'", TextView.class);
    target.alipaysetidname = Utils.findRequiredViewAsType(source, R.id.alipay_setid_name, "field 'alipaysetidname'", TextView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", NestedScrollView.class);
    target.alipayText01 = Utils.findRequiredViewAsType(source, R.id.alipay_text01, "field 'alipayText01'", TextView.class);
    target.alipayTl = Utils.findRequiredViewAsType(source, R.id.alipay_tl, "field 'alipayTl'", CustomListView.class);
    target.alipayLt = Utils.findRequiredViewAsType(source, R.id.alipay_lt, "field 'alipayLt'", CustomListView.class);
    target.alipayText02 = Utils.findRequiredViewAsType(source, R.id.alipay_text02, "field 'alipayText02'", TextView.class);
    target.alipayUsername = Utils.findRequiredViewAsType(source, R.id.alipay_username, "field 'alipayUsername'", TextView.class);
    target.alipayEM = Utils.findRequiredViewAsType(source, R.id.alipay_EM, "field 'alipayEM'", EditText.class);
    view = Utils.findRequiredView(source, R.id.alipay_id, "field 'alipayId' and method 'onViewClicked'");
    target.alipayId = Utils.castView(view, R.id.alipay_id, "field 'alipayId'", Button.class);
    view2131624154 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.alipayIdname = Utils.findRequiredViewAsType(source, R.id.alipay_idname, "field 'alipayIdname'", EditText.class);
    target.alipayType = Utils.findRequiredViewAsType(source, R.id.alipay_type, "field 'alipayType'", EditText.class);
    view = Utils.findRequiredView(source, R.id.alipay_data, "field 'alipayData' and method 'onViewClicked'");
    target.alipayData = Utils.castView(view, R.id.alipay_data, "field 'alipayData'", Button.class);
    view2131624158 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.alipay_button, "field 'alipayButton' and method 'onViewClicked'");
    target.alipayButton = Utils.castView(view, R.id.alipay_button, "field 'alipayButton'", Button.class);
    view2131624159 = view;
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
    AlipayActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.topWelcome = null;
    target.alipaytypename = null;
    target.alipaysetid = null;
    target.alipaysetidname = null;
    target.scrollView = null;
    target.alipayText01 = null;
    target.alipayTl = null;
    target.alipayLt = null;
    target.alipayText02 = null;
    target.alipayUsername = null;
    target.alipayEM = null;
    target.alipayId = null;
    target.alipayIdname = null;
    target.alipayType = null;
    target.alipayData = null;
    target.alipayButton = null;
    target.questRefresh = null;
    target.emptyLayout = null;

    view2131624154.setOnClickListener(null);
    view2131624154 = null;
    view2131624158.setOnClickListener(null);
    view2131624158 = null;
    view2131624159.setOnClickListener(null);
    view2131624159 = null;
    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
