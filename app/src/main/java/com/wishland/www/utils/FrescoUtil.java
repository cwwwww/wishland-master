package com.wishland.www.utils;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.wishland.www.R;
import com.wishland.www.view.Myapplication;

/**
 * fresco工具类
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
     * 加载网络非动图
     *
     * @param simpleDraweeView
     * @param imgUrl
     */
    public static void loadPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String imgUrl) {
        simpleDraweeView.setImageURI(imgUrl);
    }

    public static void loadRoundPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl) {

        RoundingParams roundingParams = RoundingParams.fromCornersRadii(20, 20, 0, 0);
        AutoRotateDrawable autoRotateDrawable = new AutoRotateDrawable(Myapplication.Mcontext.getResources().getDrawable(R.drawable.loading), 1500);
        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(roundingParams)
                .setProgressBarImage(autoRotateDrawable)
                .setPlaceholderImage(Myapplication.Mcontext.getResources().getDrawable(R.drawable.default_img));

        String replace = gifUrl.replace(" ", "%20").replace("&", "_");
        Uri uri = Uri.parse(replace);
        simpleDraweeView.setImageURI(uri);

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
