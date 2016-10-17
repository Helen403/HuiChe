package com.huiche.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.huiche.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public final class ImagLoadUtils {
    private static ImageLoader imageLoader;

    public static void setImage(String URl, ImageView view, Context context) {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_comments_head)
                .showImageForEmptyUri(R.drawable.user_comments_head)
                .showImageOnFail(R.drawable.user_comments_head).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
        imageLoader.displayImage(URl, view, options);

    }
}
