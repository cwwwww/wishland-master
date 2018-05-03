// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LineMeWalletAdapter$ViewHolder_ViewBinding implements Unbinder {
  private LineMeWalletAdapter.ViewHolder target;

  @UiThread
  public LineMeWalletAdapter$ViewHolder_ViewBinding(LineMeWalletAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.lineGridviewItemTextname = Utils.findRequiredViewAsType(source, R.id.line_gridview_item_textname, "field 'lineGridviewItemTextname'", TextView.class);
    target.lineGridviewItemTextmoney = Utils.findRequiredViewAsType(source, R.id.line_gridview_item_textmoney, "field 'lineGridviewItemTextmoney'", TextView.class);
    target.lineGridviewItemOk = Utils.findRequiredViewAsType(source, R.id.line_gridview_item_ok, "field 'lineGridviewItemOk'", ImageView.class);
    target.lineGridviewItemClickBg = Utils.findRequiredViewAsType(source, R.id.line_gridview_item_click_bg, "field 'lineGridviewItemClickBg'", RelativeLayout.class);
    target.lineitemrequest = Utils.findRequiredViewAsType(source, R.id.line_item_request, "field 'lineitemrequest'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LineMeWalletAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.lineGridviewItemTextname = null;
    target.lineGridviewItemTextmoney = null;
    target.lineGridviewItemOk = null;
    target.lineGridviewItemClickBg = null;
    target.lineitemrequest = null;
  }
}
