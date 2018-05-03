// Generated code from Butter Knife. Do not modify!
package com.wishland.www.controller.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wishland.www.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageListViewAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MessageListViewAdapter.ViewHolder target;

  @UiThread
  public MessageListViewAdapter$ViewHolder_ViewBinding(MessageListViewAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.messageListitemTextviewLeft = Utils.findRequiredViewAsType(source, R.id.message_listitem_textview_left, "field 'messageListitemTextviewLeft'", TextView.class);
    target.messageListitemTextviewOk = Utils.findRequiredViewAsType(source, R.id.message_listitem_textview_ok, "field 'messageListitemTextviewOk'", TextView.class);
    target.messageListitemTextviewRight = Utils.findRequiredViewAsType(source, R.id.message_listitem_textview_right, "field 'messageListitemTextviewRight'", TextView.class);
    target.messageListitemDelitem = Utils.findRequiredViewAsType(source, R.id.message_listitem_delitem, "field 'messageListitemDelitem'", ImageView.class);
    target.messageListitemTextviewTime = Utils.findRequiredViewAsType(source, R.id.message_listitem_textview_time, "field 'messageListitemTextviewTime'", TextView.class);
    target.messageListitemMessageTitle = Utils.findRequiredViewAsType(source, R.id.message_listitem_message_title, "field 'messageListitemMessageTitle'", TextView.class);
    target.messageListitemMessageContent = Utils.findRequiredViewAsType(source, R.id.message_listitem_message_content, "field 'messageListitemMessageContent'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MessageListViewAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.messageListitemTextviewLeft = null;
    target.messageListitemTextviewOk = null;
    target.messageListitemTextviewRight = null;
    target.messageListitemDelitem = null;
    target.messageListitemTextviewTime = null;
    target.messageListitemMessageTitle = null;
    target.messageListitemMessageContent = null;
  }
}
