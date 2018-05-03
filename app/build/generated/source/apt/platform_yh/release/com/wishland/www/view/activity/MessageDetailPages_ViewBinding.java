// Generated code from Butter Knife. Do not modify!
package com.wishland.www.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageDetailPages_ViewBinding implements Unbinder {
  private MessageDetailPages target;

  private View view2131624341;

  private View view2131624369;

  private View view2131624340;

  @UiThread
  public MessageDetailPages_ViewBinding(MessageDetailPages target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MessageDetailPages_ViewBinding(final MessageDetailPages target, View source) {
    this.target = target;

    View view;
    target.messageListitemTextviewOk = Utils.findRequiredViewAsType(source, R.id.message_listitem_textview_ok, "field 'messageListitemTextviewOk'", TextView.class);
    target.messageListitemDelitem = Utils.findRequiredViewAsType(source, R.id.message_listitem_delitem, "field 'messageListitemDelitem'", ImageView.class);
    target.messageListitemTextviewTime = Utils.findRequiredViewAsType(source, R.id.message_listitem_textview_time, "field 'messageListitemTextviewTime'", TextView.class);
    target.messageDetailpagesMessageTitle = Utils.findRequiredViewAsType(source, R.id.message_detailpages_message_title, "field 'messageDetailpagesMessageTitle'", TextView.class);
    target.messageDetailpagesMessageContent = Utils.findRequiredViewAsType(source, R.id.message_detailpages_message_content, "field 'messageDetailpagesMessageContent'", TextView.class);
    view = Utils.findRequiredView(source, R.id.top_fanhui, "field 'topFanhui' and method 'onViewClicked'");
    target.topFanhui = Utils.castView(view, R.id.top_fanhui, "field 'topFanhui'", ImageView.class);
    view2131624341 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.message_detail_fanhui, "field 'messageDetailFanhui' and method 'onViewClicked'");
    target.messageDetailFanhui = Utils.castView(view, R.id.message_detail_fanhui, "field 'messageDetailFanhui'", Button.class);
    view2131624369 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.topwelcome = Utils.findRequiredViewAsType(source, R.id.top_welcome, "field 'topwelcome'", TextView.class);
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
    MessageDetailPages target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.messageListitemTextviewOk = null;
    target.messageListitemDelitem = null;
    target.messageListitemTextviewTime = null;
    target.messageDetailpagesMessageTitle = null;
    target.messageDetailpagesMessageContent = null;
    target.topFanhui = null;
    target.messageDetailFanhui = null;
    target.topwelcome = null;

    view2131624341.setOnClickListener(null);
    view2131624341 = null;
    view2131624369.setOnClickListener(null);
    view2131624369 = null;
    view2131624340.setOnClickListener(null);
    view2131624340 = null;
  }
}
