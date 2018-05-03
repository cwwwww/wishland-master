package com.wishland.www.wanhaohui2.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by admin on 2018/1/11.
 */

public class AnimationUtil {

    /**
     * bx X轴的开始位置
     * by Y轴的开始位置
     * ex X轴的结束位置
     * ey Y轴的结束位置
     * duration 动画持续的时间
     * offset 动画延迟多久之后执行
     */
    public static AnimationSet Translate(View view, float bx, float by, float ex, float ey, long duration, long offset) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, bx,
                Animation.RELATIVE_TO_SELF, ex,
                Animation.RELATIVE_TO_SELF, by,
                Animation.RELATIVE_TO_SELF, ey);
        translateAnimation.setDuration(duration);
        animationSet.addAnimation(translateAnimation);

        /*
         * 第一行的设置如果为true，则动画执行完之后效果定格在执行完之后的状态
         * 第二行的设置如果为false，则动画执行完之前效果定格在执行完之后的状态
         * 第三行设置的是一个long类型的值，是指动画延迟多少毫秒之后执行
         * 第四行定义的是动画重复几次执行
         */
        animationSet.setFillAfter(true);
        animationSet.setFillBefore(false);
        animationSet.setStartOffset(offset);
//        animationSet.setRepeatCount(3);

        view.startAnimation(animationSet);
        return animationSet;
    }

    public static void Alpha_Translate(View view, float bx, float by, float ex, float ey, long duration, long offset) {
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(duration);
        animationSet.addAnimation(alphaAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, bx,
                Animation.RELATIVE_TO_SELF, ex,
                Animation.RELATIVE_TO_SELF, by,
                Animation.RELATIVE_TO_SELF, ey);
        translateAnimation.setDuration(duration);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillAfter(true);
        animationSet.setFillBefore(false);
        animationSet.setStartOffset(offset);
        view.startAnimation(animationSet);
    }

    public static AnimationSet Scale_Translate(View view, float bx, float by, float ex, float ey, long duration, long offset) {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1, 0.1f, 1, 0.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(duration);
        animationSet.addAnimation(scaleAnimation);


        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, bx,
                Animation.RELATIVE_TO_SELF, ex,
                Animation.RELATIVE_TO_SELF, by,
                Animation.RELATIVE_TO_SELF, ey);
        translateAnimation.setDuration(duration);
        animationSet.addAnimation(translateAnimation);
        animationSet.setFillAfter(true);
        animationSet.setFillBefore(false);
        animationSet.setStartOffset(offset);
        view.startAnimation(animationSet);
        return animationSet;
    }

    public static AnimationSet Rotate(View view, long offset) {
        AnimationSet animationSet = new AnimationSet(true);
        //后面的四个参数定义的是旋转的圆心位置
        RotateAnimation rotateAnimation = new RotateAnimation(
                -20, 20,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        animationSet.setStartOffset(offset);
        rotateAnimation.setRepeatCount(3);
        animationSet.addAnimation(rotateAnimation);
        view.startAnimation(animationSet);
        return animationSet;
    }
}
