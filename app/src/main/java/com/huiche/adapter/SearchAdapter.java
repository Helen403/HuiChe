package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.mine.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class SearchAdapter extends BaseAdapter {
    private List<String> data = new ArrayList<String>();
    private Context mContext;

    public SearchAdapter(Context mContext, ArrayList<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    ViewHolder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_gv, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.search_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(data.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity searchActivity = (SearchActivity) mContext;
                searchActivity.search.setText(data.get(position));
                searchActivity.getData(1, 10, false);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView tv;
    }
}
