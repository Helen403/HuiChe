package com.huiche.utils;

import android.graphics.Bitmap;

import com.huiche.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 加载网络图片帮助类,设置相应默认图片
 *
 * @author Administrator
 */
public final class LoadImageUtil {
    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public LoadImageUtil() {
        initImageLoader();
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        //.showImageOnLoading(R.drawable.cc)
        //.showImageOnLoading(R.drawable.cc)
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.shangj_beij11)
                        // 加载中要显示图片
                .showImageForEmptyUri(R.drawable.shangj_beij11)
                        // url为空要显示图片
                .showImageOnFail(R.drawable.shangj_beij11)
                .cacheInMemory(true)
                        // 加载失败要显示图片
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();
    }

}
