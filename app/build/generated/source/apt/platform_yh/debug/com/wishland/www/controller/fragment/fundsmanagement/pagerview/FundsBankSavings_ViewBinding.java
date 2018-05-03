// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.fragment.fundsmanagement.pagerview;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ExpandableListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsBankSavings_ViewBinding implements Unbinder {
  private FundsBankSavings target;

  @UiThread
  public FundsBankSavings_ViewBinding(FundsBankSavings target, View source) {
    this.target = target;

    target.fbListview = Utils.findRequiredViewAsType(source, R.id.fb_listview, "field 'fbListview'", ExpandableListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FundsBankSavings target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fbListview = null;
  }
}
