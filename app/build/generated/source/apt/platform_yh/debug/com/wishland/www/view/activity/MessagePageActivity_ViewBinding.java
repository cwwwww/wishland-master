// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import com.wishland.www.view.refresh.MaterialRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessagePageActivity_ViewBinding implements Unbinder {
  private MessagePageActivity target;

  private View view2131624341;

  private View view2131624340;

  @UiThread
  public MessagePageActivity_ViewBinding(MessagePageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MessagePageActivity_ViewBinding(final MessagePageActivity target, View source) {
    this.target = target;

    View view;
    target.messageAllTextview = Utils.findRequiredViewAsType(source, R.id.message_all_textview, "field 'messageAllTextview'", TextView.class);
    target.messageShowListview = Utils.findRequiredViewAsType(source, R.id.message_show_listview, "field 'messageShowListview'", ListView.class);
    target.messagePageitemTextview = Utils.findRequiredViewAsType(source, R.id.message_pageitem_textview, "field 'messagePageitemTextview'", TextView.class);
    target.questrefresh = Utils.findRequiredViewAsType(source, R.id.quest_refresh, "field 'questrefresh'", MaterialRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "field 'topFanhui' and method 'onViewClicked'");
    target.topFanhui = Utils.castView(view, R.id.top_fanhui, "field 'topFanhui'", ImageView.class);
    view2131624341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.topWelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topWelcome'", TextView.class);
    view = Utils.findRequiredView(source, R.id.button_pc, "method 'onViewClicked'");
    view2131624340 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MessagePageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.messageAllTextview = null;
    target.messageShowListview = null;
    target.messagePageitemTextview = null;
    target.questrefresh = null;
    target.topFanhui = null;
    target.topWelcome = null;

    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
