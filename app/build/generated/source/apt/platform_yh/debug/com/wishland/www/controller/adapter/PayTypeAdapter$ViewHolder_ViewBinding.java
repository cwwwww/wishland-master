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

public class PayTypeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PayTypeAdapter.ViewHolder target;

  @UiThread
  public PayTypeAdapter$ViewHolder_ViewBinding(PayTypeAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tiName = Utils.findRequiredViewAsType(source, R.id.ti_name, "field 'tiName'", TextView.class);
    target.tiBank = Utils.findRequiredViewAsType(source, R.id.ti_bank, "field 'tiBank'", TextView.class);
    target.tiNumber = Utils.findRequiredViewAsType(source, R.id.ti_number, "field 'tiNumber'", TextView.class);
    target.tiAddress = Utils.findRequiredViewAsType(source, R.id.ti_address, "field 'tiAddress'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PayTypeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tiName = null;
    target.tiBank = null;
    target.tiNumber = null;
    target.tiAddress = null;
  }
}
