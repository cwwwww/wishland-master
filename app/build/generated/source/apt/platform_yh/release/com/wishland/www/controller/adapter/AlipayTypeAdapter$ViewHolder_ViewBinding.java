// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AlipayTypeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AlipayTypeAdapter.ViewHolder target;

  @UiThread
  public AlipayTypeAdapter$ViewHolder_ViewBinding(AlipayTypeAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.atUsername = Utils.findRequiredViewAsType(source, R.id.at_username, "field 'atUsername'", TextView.class);
    target.alipayFrameText = Utils.findRequiredViewAsType(source, R.id.alipay_frame_text, "field 'alipayFrameText'", LinearLayout.class);
    target.alipayFrameImage = Utils.findRequiredViewAsType(source, R.id.alipay_frame_image, "field 'alipayFrameImage'", ImageView.class);
    target.atName = Utils.findRequiredViewAsType(source, R.id.at_name, "field 'atName'", TextView.class);
    target.atUser = Utils.findRequiredViewAsType(source, R.id.at_user, "field 'atUser'", TextView.class);
    target.tiState = Utils.findRequiredViewAsType(source, R.id.ti_state, "field 'tiState'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AlipayTypeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.atUsername = null;
    target.alipayFrameText = null;
    target.alipayFrameImage = null;
    target.atName = null;
    target.atUser = null;
    target.tiState = null;
  }
}
