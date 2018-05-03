package com.wishland.www.wanhaohui2.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.MyApplication;

/**
 * Created by admin on 2017/10/12.
 */

public class FrescoUtil {
    private static ImagePipeline imagePipeline = Fresco.getImagePipeline();

    public static ImagePipeline getImagePipeline() {
        return imagePipeline;
    }

    /**
     * 加载网络gif图片
     *
     * @param simpleDraweeView
     * @param gifUrl
     */
    public static void loadGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl) {
        String replace = gifUrl.replace(" ", "%20").replace("&", "_");
        Uri uri = Uri.parse(replace);
        simpleDraweeView.setImageURI(uri);
        AbstractDraweeController build = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(build);
    }

    /**
     * 加载网络gif图片，填充显示（fit_xy）
     *
     * @param simpleDraweeView
     * @param gifUrl
     */
    public static void loadAllGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl) {
        String replace = gifUrl.replace(" ", "%20").replace("&", "_");
        Uri uri = Uri.parse(replace);
        simpleDraweeView.setImageURI(uri);


        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(MyApplication.baseContext.getResources().getDrawable(R.drawable.default_img));


        AbstractDraweeController build = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setOldController(simpleDraweeView.getController())
                .build();

        simpleDraweeView.setHierarchy(builder.build());
        simpleDraweeView.setController(build);
    }


    /**
     * 加载网络非动图
     *
     * @param simpleDraweeView
     * @param imgUrl
     */
    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String imgUrl) {
        simpleDraweeView.setImageURI(imgUrl);
    }

    /**
     * 加载网络gif图片，圆形
     */
    public static void loadRoundPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String imgUrl) {

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setBorder(MyApplication.baseContext.getResources().getColor(R.color.text_hint), 1.0f);
        roundingParams.setRoundAsCircle(true);
        String replace = imgUrl.replace(" ", "%20").replace("&", "_");
        Uri uri = Uri.parse(replace);
        simpleDraweeView.setImageURI(uri);

        AutoRotateDrawable autoRotateDrawable = new AutoRotateDrawable(MyApplication.baseContext.getResources().getDrawable(R.drawable.loading), 1500);
        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(roundingParams)
                .setProgressBarImage(autoRotateDrawable)
                .setPlaceholderImage(MyApplication.baseContext.getResources().getDrawable(R.drawable.default_img));


        AbstractDraweeController build = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setOldController(simpleDraweeView.getController())
                .build();

        simpleDraweeView.setHierarchy(builder.build());
        simpleDraweeView.setController(build);
    }

}
