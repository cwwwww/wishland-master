// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.fragment.line;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.CustomGridView;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LineConversionPage_ViewBinding implements Unbinder {
  private LineConversionPage target;

  private View view2131624313;

  private View view2131624315;

  private View view2131624318;

  private View view2131624320;

  private View view2131624322;

  private View view2131624324;

  private View view2131624325;

  private View view2131624326;

  private View view2131624327;

  private View view2131624340;

  private View view2131624321;

  @UiThread
  public LineConversionPage_ViewBinding(final LineConversionPage target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lt_from, "field 'ltfrom' and method 'onViewClicked'");
    target.ltfrom = Utils.castView(view, R.id.lt_from, "field 'ltfrom'", TextView.class);
    view2131624313 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lt_to, "field 'ltto' and method 'onViewClicked'");
    target.ltto = Utils.castView(view, R.id.lt_to, "field 'ltto'", TextView.class);
    view2131624315 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.lineSetMoneyEdittext = Utils.findRequiredViewAsType(source, R.id.line_set_money_edittext, "field 'lineSetMoneyEdittext'", EditText.class);
    view = Utils.findRequiredView(source, R.id.line_all_button, "field 'lineAllButton' and method 'onViewClicked'");
    target.lineAllButton = Utils.castView(view, R.id.line_all_button, "field 'lineAllButton'", Button.class);
    view2131624318 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_100_button, "field 'line100Button' and method 'onViewClicked'");
    target.line100Button = Utils.castView(view, R.id.line_100_button, "field 'line100Button'", Button.class);
    view2131624320 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_500_button, "field 'line500Button' and method 'onViewClicked'");
    target.line500Button = Utils.castView(view, R.id.line_500_button, "field 'line500Button'", Button.class);
    view2131624322 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_1000_button, "field 'line1000Button' and method 'onViewClicked'");
    target.line1000Button = Utils.castView(view, R.id.line_1000_button, "field 'line1000Button'", Button.class);
    view2131624324 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_5000_button, "field 'line5000Button' and method 'onViewClicked'");
    target.line5000Button = Utils.castView(view, R.id.line_5000_button, "field 'line5000Button'", Button.class);
    view2131624325 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_10000_button, "field 'line10000Button' and method 'onViewClicked'");
    target.line10000Button = Utils.castView(view, R.id.line_10000_button, "field 'line10000Button'", Button.class);
    view2131624326 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_putin_button, "field 'linePutinButton' and method 'onViewClicked'");
    target.linePutinButton = Utils.castView(view, R.id.line_putin_button, "field 'linePutinButton'", Button.class);
    view2131624327 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.lineToWalletGridview = Utils.findRequiredViewAsType(source, R.id.line_to_wallet_gridview, "field 'lineToWalletGridview'", CustomGridView.class);
    target.lineMeWalletGridview = Utils.findRequiredViewAsType(source, R.id.line_me_wallet_gridview, "field 'lineMeWalletGridview'", CustomGridView.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    target.lineSrcollview = Utils.findRequiredViewAsType(source, R.id.line_srcollview, "field 'lineSrcollview'", ScrollView.class);
    target.flTopView = Utils.findRequiredViewAsType(source, R.id.fl_top_view, "field 'flTopView'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.button_pc, "method 'onViewClicked'");
    view2131624340 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.line_200_button, "method 'onViewClicked'");
    view2131624321 = view;
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
    LineConversionPage target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ltfrom = null;
    target.ltto = null;
    target.lineSetMoneyEdittext = null;
    target.lineAllButton = null;
    target.line100Button = null;
    target.line500Button = null;
    target.line1000Button = null;
    target.line5000Button = null;
    target.line10000Button = null;
    target.linePutinButton = null;
    target.lineToWalletGridview = null;
    target.lineMeWalletGridview = null;
    target.questrefresh = null;
    target.emptyLayout = null;
    target.lineSrcollview = null;
    target.flTopView = null;

    view2131624313.setOnClickListener(null);
    view2131624313 = null;
    view2131624315.setOnClickListener(null);
    view2131624315 = null;
    view2131624318.setOnClickListener(null);
    view2131624318 = null;
    view2131624320.setOnClickListener(null);
    view2131624320 = null;
    view2131624322.setOnClickListener(null);
    view2131624322 = null;
    view2131624324.setOnClickListener(null);
    view2131624324 = null;
    view2131624325.setOnClickListener(null);
    view2131624325 = null;
    view2131624326.setOnClickListener(null);
    view2131624326 = null;
    view2131624327.setOnClickListener(null);
    view2131624327 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
    view2131624321.setOnClickListener(null);
    view2131624321 = null;
  }
}
