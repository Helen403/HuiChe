package com.huiche.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.huiche.R;
import com.huiche.bean.ProductInfo;
import com.huiche.lib.lib.base.MyBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9.
 */
public class BusinessDetailHelenGridviewAdapter extends MyBaseAdapter<ProductInfo> {

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public BusinessDetailHelenGridviewAdapter(ArrayList<ProductInfo> data) {
        super(data);
        initImageLoader();
    }


    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
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

    @Override
    public int getContentView() {
        return R.layout.item_bussiness_detail_new_gridview;
    }

    @Override
    public void onInitView(View view, ProductInfo productInfo, int position) {
        String url = productInfo.goodsImgs.get(0);
        ImageView imageView = getViewById(R.id.iv_);

        imageLoader.displayImage(url, imageView, options);
        setText(productInfo.name, R.id.tv_);

        setText("会员价:￥"+productInfo.realPrice , R.id.money);
    }
}
