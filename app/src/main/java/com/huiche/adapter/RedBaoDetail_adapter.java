package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.RedBaoDetailBean;
import com.huiche.view.BufferViewDialog;
import com.huiche.view.BufferViewSuccessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class RedBaoDetail_adapter extends BaseAdapter {
    private List<RedBaoDetailBean> data = new ArrayList<RedBaoDetailBean>();
    private Context mContext;
    private BufferViewDialog bufferViewDialog;
    private BufferViewSuccessDialog bufferViewSuccessDialog;

    public RedBaoDetail_adapter(Context mContext, ArrayList<RedBaoDetailBean> data) {
        this.mContext = mContext;
        this.data = data;
        bufferViewDialog = new BufferViewDialog(mContext);
        bufferViewSuccessDialog = new BufferViewSuccessDialog(mContext);
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_red_detail, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.get_card);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 1) {
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bufferViewDialog.show(null, "关闭", true, null);
                }
            });

        } else {
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bufferViewSuccessDialog.show("关闭", true, null);
                }
            });

        }

        return convertView;
    }


    public class ViewHolder {
        public TextView tv;
    }
}
