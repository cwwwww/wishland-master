// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.fragment.homepage;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.CustomListView;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import com.youth.banner.Banner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomePage_ViewBinding implements Unbinder {
  private HomePage target;

  private View view2131624261;

  private View view2131624340;

  @UiThread
  public HomePage_ViewBinding(final HomePage target, View source) {
    this.target = target;

    View view;
    target.homeBanner = Utils.findRequiredViewAsType(source, R.id.home_banner, "field 'homeBanner'", Banner.class);
    view = Utils.findRequiredView(source, R.id.home_more_text, "field 'homeMoreText' and method 'onViewClicked'");
    target.homeMoreText = Utils.castView(view, R.id.home_more_text, "field 'homeMoreText'", TextView.class);
    view2131624261 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.homeTextvhiewPmd = Utils.findRequiredViewAsType(source, R.id.home_textvhiew_pmd, "field 'homeTextvhiewPmd'", TextView.class);
    target.llHome = Utils.findRequiredViewAsType(source, R.id.ll_home, "field 'llHome'", LinearLayout.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
    target.morelistView = Utils.findRequiredViewAsType(source, R.id.home_more_listview, "field 'morelistView'", CustomListView.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.main_scroll_view, "field 'scrollView'", ScrollView.class);
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
    HomePage target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.homeBanner = null;
    target.homeMoreText = null;
    target.homeTextvhiewPmd = null;
    target.llHome = null;
    target.questrefresh = null;
    target.morelistView = null;
    target.emptyLayout = null;
    target.scrollView = null;

    view2131624261.setOnClickListener(null);
    view2131624261 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
