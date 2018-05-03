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

public class PayTextAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PayTextAdapter.ViewHolder target;

  @UiThread
  public PayTextAdapter$ViewHolder_ViewBinding(PayTextAdapter.ViewHolder target, View source) {
    this.target = target;

    target.TText = Utils.findRequiredViewAsType(source, R.id.T_text, "field 'TText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PayTextAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.TText = null;
  }
}
