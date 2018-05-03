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

public class MoreAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MoreAdapter.ViewHolder target;

  @UiThread
  public MoreAdapter$ViewHolder_ViewBinding(MoreAdapter.ViewHolder target, View source) {
    this.target = target;

    target.moreIm = Utils.findRequiredViewAsType(source, R.id.more_im, "field 'moreIm'", SimpleDraweeView.class);
    target.moreTvLeft = Utils.findRequiredViewAsType(source, R.id.more_tv_left, "field 'moreTvLeft'", TextView.class);
    target.moreTvRight = Utils.findRequiredViewAsType(source, R.id.more_tv_right, "field 'moreTvRight'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MoreAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.moreIm = null;
    target.moreTvLeft = null;
    target.moreTvRight = null;
  }
}
