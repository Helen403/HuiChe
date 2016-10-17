package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyBaseAdapter;
import com.huiche.bean.InfoGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/***
 * 首页 nearByFragment 中listview 中headview中的gridview适配器，添加餐饮美食，休闲娱乐。。。。等
 *
 * @author Administrator
 */
public class GridView_HeadView extends MyBaseAdapter<InfoGridView> {
    private ImageLoader imageLoader;
    private List<InfoGridView> myList;
    private Context context;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    public GridView_HeadView(Context mContext, List<InfoGridView> datalist) {
        super(mContext, datalist);
        // TODO Auto-generated constructor stub
        this.context = mContext;
        this.myList = datalist;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gridview_headview, null);
            ImageView mage = (ImageView) convertView.findViewById(R.id.image_gridview_headviewNearFragment);
            viewHolder.image = mage;
            TextView text = (TextView) convertView.findViewById(R.id.text_gridview_headviewNearFragment);
            viewHolder.text = text;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String url = myList.get(position).getUrl();
        if(url!=null)
            imageLoader.displayImage(myList.get(position).getUrl(), viewHolder.image);
        else
            viewHolder.image.setImageResource(myList.get(position).getImage());//默认图标
        viewHolder.text.setText(myList.get(position).getInfo() + "");
        return convertView;
    }

    class ViewHolder {
        private ImageView image;
        private TextView text;
    }


}
