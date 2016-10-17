package com.huiche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseImageAdapter;
import com.huiche.bean.BusinessInfo;
import com.huiche.bean.BusinessInfo_of;
import com.huiche.utils.SetGridViewHeightUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterListView_MoreProductActivity extends BaseImageAdapter<BusinessInfo> {
    private Context mcontext;
    private List<BusinessInfo> listData;
    private List<BusinessInfo_of> emptyList = new ArrayList<>();
    private ViewHolder holder;

    public AdapterListView_MoreProductActivity(Context context,
                                               List<BusinessInfo> list) {
        super(context, list);
        // TODO Auto-generated constructor stub
        this.mcontext = context;
        this.listData = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview_morproductactivity, null);
            holder.cityName = (TextView) convertView.findViewById(R.id.info_item_listView_MorPActivity);
            holder.gridview = (GridView) convertView.findViewById(R.id.gridView_item_listView_MorPActivity);
//			SetSizeUtils.setSizeOnlyHeightOf(context, holder.cityName, 14);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cityName.setText(listData.get(position).getpName());
        SetGridViewHeightUtils.setGridViewSize(mcontext, holder.gridview,
                listData.get(position).getsName().size(), 4, 12);
        GR_AD_ListView_MorProductAdapter adapter =
                new GR_AD_ListView_MorProductAdapter(context, listData.get(position).getsName());
        holder.gridview.setAdapter(adapter);
        return convertView;
    }

    class ViewHolder {
        private TextView cityName;
        private GridView gridview;

    }

    public void refreshData(List<BusinessInfo> lists) {
        listData.clear();
        listData.addAll(lists);
        notifyDataSetChanged();
    }

}
