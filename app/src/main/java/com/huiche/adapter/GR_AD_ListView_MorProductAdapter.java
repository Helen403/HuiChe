package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.ClassifyOfActivity;
import com.huiche.base.BaseImageAdapter;
import com.huiche.bean.BusinessInfo_of;
import com.huiche.utils.SetSizeUtils;

import java.util.List;

/***
 * 更多页面中 listview的item中 gridvie的适配器
 *
 * @author Administrator
 */
public class GR_AD_ListView_MorProductAdapter extends BaseImageAdapter<BusinessInfo_of> {
    private Context context;
//    private List<BusinessInfo_of> datalist;
    private ViewHolder holder;

    public GR_AD_ListView_MorProductAdapter(Context mContext, List<BusinessInfo_of> datalist) {
        super(mContext, datalist);
        this.context = mContext;
//        this.datalist = datalist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gd_lv_moreproduct, null);
            holder.category = (TextView) convertView.findViewById(R.id.text_rd_lv_moreProduct);
            SetSizeUtils.setSizeOnlyHeightOf(context, holder.category, 13);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String categoryName = dataList.get(position).getName();
        holder.category.setText(categoryName);
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassifyOfActivity.class);
                intent.putExtra("more", categoryName);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView category;


    }

}
