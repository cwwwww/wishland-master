// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsBankExpandAdapter$ChildHolder_ViewBinding implements Unbinder {
  private FundsBankExpandAdapter.ChildHolder target;

  @UiThread
  public FundsBankExpandAdapter$ChildHolder_ViewBinding(FundsBankExpandAdapter.ChildHolder target,
      View source) {
    this.target = target;

    target.fcImageview = Utils.findRequiredViewAsType(source, R.id.fc_imageview, "field 'fcImageview'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FundsBankExpandAdapter.ChildHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fcImageview = null;
  }
}
