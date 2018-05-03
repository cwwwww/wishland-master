// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FundsBankExpandAdapter$GroupHolder_ViewBinding implements Unbinder {
  private FundsBankExpandAdapter.GroupHolder target;

  @UiThread
  public FundsBankExpandAdapter$GroupHolder_ViewBinding(FundsBankExpandAdapter.GroupHolder target,
      View source) {
    this.target = target;

    target.fgImageview = Utils.findRequiredViewAsType(source, R.id.fg_imageview, "field 'fgImageview'", SimpleDraweeView.class);
    target.fgTextview = Utils.findRequiredViewAsType(source, R.id.fg_textview, "field 'fgTextview'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FundsBankExpandAdapter.GroupHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fgImageview = null;
    target.fgTextview = null;
  }
}
