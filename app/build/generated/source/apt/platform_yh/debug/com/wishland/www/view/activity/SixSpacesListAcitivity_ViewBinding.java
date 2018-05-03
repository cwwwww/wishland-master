// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
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

public class SixSpacesListAcitivity_ViewBinding implements Unbinder {
  private SixSpacesListAcitivity target;

  private View view2131624341;

  @UiThread
  public SixSpacesListAcitivity_ViewBinding(SixSpacesListAcitivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SixSpacesListAcitivity_ViewBinding(final SixSpacesListAcitivity target, View source) {
    this.target = target;

    View view;
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.sixspacesListview = Utils.findRequiredViewAsType(source, R.id.sixspaces_listview, "field 'sixspacesListview'", ListView.class);
    target.sixspacesRefresh = Utils.findRequiredViewAsType(source, R.id.sixspaces_refresh, "field 'sixspacesRefresh'", MaterialRefreshLayout.class);
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
    SixSpacesListAcitivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.topWelcome = null;
    target.sixspacesListview = null;
    target.sixspacesRefresh = null;
    target.emptyLayout = null;

    view2131624341.setOnClickListener(null);
    view2131624341 = null;
  }
}
