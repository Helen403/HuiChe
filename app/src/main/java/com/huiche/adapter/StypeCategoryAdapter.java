package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.huiche.R;
import com.huiche.bean.SubCategory;
import com.huiche.utils.SetSizeUtils;

import java.util.List;

/**
 * 二级菜单
 * Created by Administrator on 2016/7/19 0019.
 */
public class StypeCategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<SubCategory> dataList;
//    private int selectPosition;
    private onItemCheckListener checkListener;
    private String checkStr;

    public StypeCategoryAdapter(Context context, List<SubCategory> dataList, String category) {
        this.mContext = context;
        this.dataList = dataList;
        this.checkStr = category;
    }

    /**
     * 设置被选中的item
     *
     * @param selectPosition
     */
    public void setSelection(int selectPosition) {
//        this.selectPosition = selectPosition;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_stype_orderby, null);
            viewHolder.checkTextView = (CheckedTextView) convertView.findViewById(R.id.checkTextView_stype);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SubCategory subCategory = dataList.get(position);
        if (checkStr.equals(subCategory.getName())) {
            viewHolder.checkTextView.setChecked(true);
        }
        viewHolder.checkTextView.setText(subCategory.getName());
        SetSizeUtils.setSizeOnlyHeightOf(mContext, viewHolder.checkTextView, 12);
        viewHolder.checkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkTextView.setChecked(true);
                checkListener.onItemChecked(position);
                checkStr = subCategory.name;
            }
        });
        return convertView;
    }

    class ViewHolder {
        CheckedTextView checkTextView;
    }

    /**
     * item选择回调
     */
    public interface onItemCheckListener {
        void onItemChecked(int position);
    }

    public void setOnItemCheckListener(onItemCheckListener listener) {
        this.checkListener = listener;
    }

}
