package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.utils.SetSizeUtils;

import java.util.List;

/**
 * 一级菜单
 * Created by Administrator on 2016/7/19 0019.
 */
public class PtypeCategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> dataList;
    private int selectPosition;

    public PtypeCategoryAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    /**
     * 设置被选中的item，改变背景颜色
     *
     * @param selectPosition
     */
    public void setSelection(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_ptype_orderby, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_select_orderBy);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (selectPosition == position) {
            viewHolder.tv.setSelected(true);
        } else {
            viewHolder.tv.setSelected(false);
        }
        viewHolder.tv.setText(dataList.get(position));
        SetSizeUtils.setSizeOnlyHeightOf(mContext, viewHolder.tv, 12);
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
