package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.huiche.activity.BusinessDetailActivity;
import com.huiche.activity.mine.WebViewActivity;
import com.huiche.bean.PosterInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/***
 * 商家详情轮播图adapter
 *
 * @author lyy
 */
public class ViewPageAdaper_Poster extends PagerAdapter {
    private List<PosterInfo> list;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Context mContext;

    public ViewPageAdaper_Poster(Context context, List<PosterInfo> lists,
                                 ImageLoader imageLoader, DisplayImageOptions options) {
        this.list = lists;
        this.imageLoader = imageLoader;
        this.options = options;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        position %= list.size();
        if (position < 0) {
            position = list.size() + position;
        }
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ScaleType.FIT_XY);
        // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = iv.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(iv);
        }
        final PosterInfo posterInfo = list.get(position);
        imageLoader.displayImage(posterInfo.content, iv, options);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//				点击内容后的操作类型（0：无操作；1：跳转到网页；2：进入原生商家详情）
                if (posterInfo.operationType.equals("1")) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", posterInfo.adUrl);
                    intent.putExtra("title", "首页广告");
                    mContext.startActivity(intent);
                }
                //跳到原生商家详情
                else if (posterInfo.operationType.equals("2")) {
                    //截取url获取商家id
                    String url = posterInfo.adUrl;
                    String[] urlt = new String[2];
                    urlt = url.split("id=");
                    Intent intent2 = new Intent();
                    if (!urlt[1].equals("")) {
                        intent2.setClass(mContext, BusinessDetailActivity.class);
                        intent2.putExtra("businessStoreId", urlt[1]);
                        intent2.putExtra("empty", "0");
                        mContext.startActivity(intent2);
                    }

                }
            }
        });
        // 必须要调用addview否则无法显示
        ((ViewPager) container).addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //Warning：不要在这里调用removeView
    }

}
