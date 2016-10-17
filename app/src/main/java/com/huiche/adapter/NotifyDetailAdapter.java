package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huiche.PostResult.NotifyInfo;
import com.huiche.R;

import java.util.List;


/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class NotifyDetailAdapter extends BaseAdapter{

    private  Context context;
    private List<NotifyInfo> list;

    public NotifyDetailAdapter(Context context,List<NotifyInfo> list ) {
        this.context = context ;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notify_detail,null);
            holder.tv= (TextView) convertView.findViewById(R.id.tv_notifyContext);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(position+1+"."+list.get(position).getContext());
        return convertView;
    }
    public class ViewHolder {
        TextView tv;
    }

}
