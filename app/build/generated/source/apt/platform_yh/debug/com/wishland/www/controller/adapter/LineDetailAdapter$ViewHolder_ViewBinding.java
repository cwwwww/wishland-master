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

public class LineDetailAdapter$ViewHolder_ViewBinding implements Unbinder {
  private LineDetailAdapter.ViewHolder target;

  @UiThread
  public LineDetailAdapter$ViewHolder_ViewBinding(LineDetailAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'tvNumber'", TextView.class);
    target.tvMdtime = Utils.findRequiredViewAsType(source, R.id.tv_mdtime, "field 'tvMdtime'", TextView.class);
    target.tvBjtime = Utils.findRequiredViewAsType(source, R.id.tv_bjtime, "field 'tvBjtime'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
    target.tvMoney = Utils.findRequiredViewAsType(source, R.id.tv_money, "field 'tvMoney'", TextView.class);
    target.tvState = Utils.findRequiredViewAsType(source, R.id.tv_state, "field 'tvState'", TextView.class);
    target.tvover = Utils.findRequiredViewAsType(source, R.id.tv_over, "field 'tvover'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LineDetailAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNumber = null;
    target.tvMdtime = null;
    target.tvBjtime = null;
    target.tvContent = null;
    target.tvMoney = null;
    target.tvState = null;
    target.tvover = null;
  }
}
