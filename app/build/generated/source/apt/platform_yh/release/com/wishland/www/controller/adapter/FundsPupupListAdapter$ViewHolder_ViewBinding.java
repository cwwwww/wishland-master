// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsPupupListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private FundsPupupListAdapter.ViewHolder target;

  @UiThread
  public FundsPupupListAdapter$ViewHolder_ViewBinding(FundsPupupListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.fundsPopupItemText = Utils.findRequiredViewAsType(source, R.id.funds_popup_item_text, "field 'fundsPopupItemText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FundsPupupListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fundsPopupItemText = null;
  }
}
