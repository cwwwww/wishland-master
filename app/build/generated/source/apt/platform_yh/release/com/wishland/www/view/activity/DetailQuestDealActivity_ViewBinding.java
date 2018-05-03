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
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailQuestDealActivity_ViewBinding implements Unbinder {
  private DetailQuestDealActivity target;

  private View view2131624341;

  @UiThread
  public DetailQuestDealActivity_ViewBinding(DetailQuestDealActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailQuestDealActivity_ViewBinding(final DetailQuestDealActivity target, View source) {
    this.target = target;

    View view;
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    target.fundslistview = Utils.findRequiredViewAsType(source, R.id.detail_quest_listview, "field 'fundslistview'", ListView.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
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
    DetailQuestDealActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.topWelcome = null;
    target.fundslistview = null;
    target.questrefresh = null;

    view2131624341.setOnClickListener(null);
    view2131624341 = null;
  }
}
