// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.customgridview.EmptyLayout;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AccountRequestBankActivity_ViewBinding implements Unbinder {
  private AccountRequestBankActivity target;

  private View view2131624341;

  @UiThread
  public AccountRequestBankActivity_ViewBinding(AccountRequestBankActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccountRequestBankActivity_ViewBinding(final AccountRequestBankActivity target,
      View source) {
    this.target = target;

    View view;
    target.accountbanklist = Utils.findRequiredViewAsType(source, R.id.account_banklist, "field 'accountbanklist'", ListView.class);
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
    target.emptyLayout = Utils.findRequiredViewAsType(source, R.id.empty_layout, "field 'emptyLayout'", EmptyLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "method 'onViewClicked'");
    view2131624341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AccountRequestBankActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.accountbanklist = null;
    target.topWelcome = null;
    target.questrefresh = null;
    target.emptyLayout = null;

    view2131624341.setOnClickListener(null);
    view2131624341 = null;
  }
}
