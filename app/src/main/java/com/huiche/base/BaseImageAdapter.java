package com.huiche.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.huiche.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

public abstract class BaseImageAdapter<E> extends BaseAdapter {
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader;
	protected Context context;
	protected List<E> dataList;
	protected LayoutInflater inflater;
	public BaseImageAdapter(Context context, List<E> list) {
		this.context = context;
		this.dataList = list;
		imageLoader = ImageLoader.getInstance();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.shangj_beij11)
				.showImageForEmptyUri(R.drawable.shangj_beij11)
				.showImageOnFail(R.drawable.shangj_beij11).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(388)).build();
	}
	@Override
	public int getCount() {

		if (null == dataList) {
			throw new RuntimeException("");
		} else {

			return dataList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (null == dataList) {
			throw new RuntimeException("");
		} else {
			return dataList.get(position);
		}

	}

	@Override
	public long getItemId(int position) {

		return position;
	}
}
