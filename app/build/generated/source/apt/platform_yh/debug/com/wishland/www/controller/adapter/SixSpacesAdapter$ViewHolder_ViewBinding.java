// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SixSpacesAdapter$ViewHolder_ViewBinding implements Unbinder {
  private SixSpacesAdapter.ViewHolder target;

  @UiThread
  public SixSpacesAdapter$ViewHolder_ViewBinding(SixSpacesAdapter.ViewHolder target, View source) {
    this.target = target;

    target.sixspacesListItemView = Utils.findRequiredViewAsType(source, R.id.sixspaces_list_item_view, "field 'sixspacesListItemView'", SimpleDraweeView.class);
    target.sixspacesListItemText = Utils.findRequiredViewAsType(source, R.id.sixspaces_list_item_text, "field 'sixspacesListItemText'", TextView.class);
    target.sixspacesListItemButton = Utils.findRequiredViewAsType(source, R.id.sixspaces_list_item_button, "field 'sixspacesListItemButton'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SixSpacesAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sixspacesListItemView = null;
    target.sixspacesListItemText = null;
    target.sixspacesListItemButton = null;
  }
}
