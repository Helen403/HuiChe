package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyBaseAdapter;

import java.util.List;


public class PopReportErrorAdapter extends MyBaseAdapter<String> {

    public PopReportErrorAdapter(Context mContext, List<String> datalist) {
        super(mContext, datalist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_listview_pop_reporterror, null);
            mHolder.textView = (TextView) convertView.findViewById(R.id.tv_pop_reportError);
            mHolder.lastDivideLine = (View) convertView.findViewById(R.id.view_lastDivideLine);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.textView.setText(datalist.get(position));
        //最后item显示分割线
        if (position == datalist.size() - 1) {
            mHolder.lastDivideLine.setVisibility(View.VISIBLE);
        } else {
            mHolder.lastDivideLine.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView textView;
        View lastDivideLine;

    }

}
