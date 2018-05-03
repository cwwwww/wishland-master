package com.wishland.www.wanhaohui2.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wishland.www.wanhaohui2.R;

/**
 * Created by admin on 2017/12/1.
 */

public class MyToastUtil {
    public static MyToastUtil mToastEmail;
    public Toast toast;

    private MyToastUtil() {
    }

    public static MyToastUtil getToastEmail() {
        if (mToastEmail == null) {
            mToastEmail = new MyToastUtil();
        }
        return mToastEmail;
    }

    /**
     * 显示
     */
    public void toastShow(Context context, ViewGroup root, String str) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_message, root);
        TextView text = (TextView) view.findViewById(R.id.textToast);
//        text.setText(str); // 设置显示文字
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0); // Toast显示的位置
        toast.setDuration(3000); // Toast显示的时间
        toast.setView(view);
        toast.show();
    }

    public void toastCancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}

