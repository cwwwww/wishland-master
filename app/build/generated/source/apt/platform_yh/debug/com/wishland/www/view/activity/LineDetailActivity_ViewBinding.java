// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LineDetailActivity_ViewBinding implements Unbinder {
  private LineDetailActivity target;

  private View view2131624295;

  private View view2131624296;

  private View view2131624297;

  private View view2131624298;

  private View view2131624299;

  private View view2131624300;

  private View view2131624301;

  private View view2131624302;

  private View view2131624341;

  private View view2131624303;

  @UiThread
  public LineDetailActivity_ViewBinding(LineDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LineDetailActivity_ViewBinding(final LineDetailActivity target, View source) {
    this.target = target;

    View view;
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ed_s_year, "field 'edSYear' and method 'onViewClicked'");
    target.edSYear = Utils.castView(view, R.id.ed_s_year, "field 'edSYear'", Button.class);
    view2131624295 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_s_hour, "field 'edSHour' and method 'onViewClicked'");
    target.edSHour = Utils.castView(view, R.id.ed_s_hour, "field 'edSHour'", Button.class);
    view2131624296 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_s_minute, "field 'edSMinute' and method 'onViewClicked'");
    target.edSMinute = Utils.castView(view, R.id.ed_s_minute, "field 'edSMinute'", Button.class);
    view2131624297 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_e_year, "field 'edEYear' and method 'onViewClicked'");
    target.edEYear = Utils.castView(view, R.id.ed_e_year, "field 'edEYear'", Button.class);
    view2131624298 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_e_hour, "field 'edEHour' and method 'onViewClicked'");
    target.edEHour = Utils.castView(view, R.id.ed_e_hour, "field 'edEHour'", Button.class);
    view2131624299 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_e_minute, "field 'edEMinute' and method 'onViewClicked'");
    target.edEMinute = Utils.castView(view, R.id.ed_e_minute, "field 'edEMinute'", Button.class);
    view2131624300 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_from, "field 'edFrom' and method 'onViewClicked'");
    target.edFrom = Utils.castView(view, R.id.ed_from, "field 'edFrom'", Button.class);
    view2131624301 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_to, "field 'edTo' and method 'onViewClicked'");
    target.edTo = Utils.castView(view, R.id.ed_to, "field 'edTo'", Button.class);
    view2131624302 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.edListview = Utils.findRequiredViewAsType(source, R.id.ed_listview, "field 'edListview'", ListView.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "method 'onViewClicked'");
    view2131624341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ed_button, "method 'onViewClicked'");
    view2131624303 = view;
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
    LineDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.topWelcome = null;
    target.edSYear = null;
    target.edSHour = null;
    target.edSMinute = null;
    target.edEYear = null;
    target.edEHour = null;
    target.edEMinute = null;
    target.edFrom = null;
    target.edTo = null;
    target.edListview = null;
    target.emptyLayout = null;

    view2131624295.setOnClickListener(null);
    view2131624295 = null;
    view2131624296.setOnClickListener(null);
    view2131624296 = null;
    view2131624297.setOnClickListener(null);
    view2131624297 = null;
    view2131624298.setOnClickListener(null);
    view2131624298 = null;
    view2131624299.setOnClickListener(null);
    view2131624299 = null;
    view2131624300.setOnClickListener(null);
    view2131624300 = null;
    view2131624301.setOnClickListener(null);
    view2131624301 = null;
    view2131624302.setOnClickListener(null);
    view2131624302 = null;
    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624303.setOnClickListener(null);
    view2131624303 = null;
  }
}
