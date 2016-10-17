package com.huiche.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import uk.co.senab.photoview.PhotoView;

/***
 * 商家详情轮播图adapter
 *
 * @author lyy
 */
public class ViewPageAdaper_PhotoAlbum extends PagerAdapter {
    private List<String> urlList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context mContext;

    public ViewPageAdaper_PhotoAlbum(Context context, List<String> lists,
                                     ImageLoader imageLoader, DisplayImageOptions options) {
        this.urlList = lists;
        this.imageLoader = imageLoader;
        this.options = options;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoview = new PhotoView(mContext);
//        container.addView(photoview, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        container.addView(photoview);
        imageLoader.displayImage(urlList.get(position), photoview, options);
        return photoview;
    }

}
