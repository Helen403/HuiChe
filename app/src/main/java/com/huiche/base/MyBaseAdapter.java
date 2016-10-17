package com.huiche.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Administrator Context mContext;
 *         LayoutInflater mInflater;
 *         List<E> datalist;
 */
public abstract class MyBaseAdapter<E> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<E> datalist;

    public MyBaseAdapter(Context mContext, List<E> datalist) {
        initBaseData(mContext);
        this.datalist = datalist;
    }

    public MyBaseAdapter(Context mContext) {
        initBaseData(mContext);
    }

    private void initBaseData(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (datalist == null) {
            throw new RuntimeException("Datalist fill data can not be null");
        } else {
            return datalist.size();
        }

    }

    @Override
    public Object getItem(int position) {
        if (datalist == null) {
            throw new RuntimeException(
                    "Datalist fill data and the datelist can not be null");
        } else {
            return datalist.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
