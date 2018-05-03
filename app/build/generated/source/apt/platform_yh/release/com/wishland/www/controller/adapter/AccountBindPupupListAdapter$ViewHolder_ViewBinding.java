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

public class AccountBindPupupListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private AccountBindPupupListAdapter.ViewHolder target;

  @UiThread
  public AccountBindPupupListAdapter$ViewHolder_ViewBinding(AccountBindPupupListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.accountBindItemtext = Utils.findRequiredViewAsType(source, R.id.account_bind_item_text, "field 'accountBindItemtext'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccountBindPupupListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.accountBindItemtext = null;
  }
}
