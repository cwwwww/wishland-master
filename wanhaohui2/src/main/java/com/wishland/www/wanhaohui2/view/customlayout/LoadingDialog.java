package com.wishland.www.wanhaohui2.view.customlayout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.wishland.www.wanhaohui2.R;

/**
 * Created by admin on 2017/10/23.
 */

public class LoadingDialog extends Dialog {
    private Context context;
    private static LoadingDialog dialog;
    private ImageView ivProgress;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    //显示dialog的方法
    public static LoadingDialog showDialog(Context context){
        dialog = new LoadingDialog(context, R.style.MyDialog);//dialog样式
        dialog.setContentView(R.layout.dialog_layout);//dialog布局文件
        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
        return dialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && dialog != null){
            ivProgress = (ImageView) dialog.findViewById(R.id.ivProgress);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_progress_anim);
            ivProgress.startAnimation(animation);
        }
    }
}
