package com.wcl.administrator.recyclerviewtest.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * 作者：Created by Administrator on 2021/4/26.
 * 邮箱：
 */
public class ImageLoaderUtil {
    /**
     * 圆形头像
     */
    public static void LoadCircleImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.centerCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .apply(RequestOptions.circleCropTransform())
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * 常规使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //磁盘缓存，表示既缓存原始图片，也缓存转换过后的图片。
                .transition(new DrawableTransitionOptions().crossFade(800)) //适用于Drawable，过渡动画持续800ms

                .into(imageView);
    }


    /**
     * 自定义RequestOptions使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView,
                                 RequestOptions requestOptions) {
        Glide.with(context).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * 自定义RequestOptions使用
     */
    public static void LoadImage(Fragment fragment, Object url, ImageView imageView,
                                 RequestOptions requestOptions) {
        Glide.with(fragment).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }


    /**
     * 需要回调时使用
     */
    public static void LoadImage(Context context, Object url, ImageViewTarget imageViewTarget) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageViewTarget);
    }

    /**
     * 需要回调时使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView,
                                 RequestListener listener) {
        Glide.with(context).load(url)
                //.thumbnail(0.1f)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .listener(listener)
                .into(imageView);
    }

}
