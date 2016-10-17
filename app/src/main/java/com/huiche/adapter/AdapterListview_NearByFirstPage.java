package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.PayActivity;
import com.huiche.base.BaseImageAdapter;
import com.huiche.bean.BusinessInfoAll;
import com.huiche.utils.SetSizeUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * 首页 nearByFragment 中listview
 *
 * @author Administrator
 */
public class AdapterListview_NearByFirstPage extends BaseImageAdapter<BusinessInfoAll> {

    private ViewHolder holder;
    private List<BusinessInfoAll> mList = new ArrayList<>();
    private Context mContext;

    public AdapterListview_NearByFirstPage(Context context, List<BusinessInfoAll> list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview_nearbyfirstpage, null);
            holder.storeName = (TextView) convertView.findViewById(R.id.storeName_Listview_NearbyFristPage);
            holder.storeAddress = (TextView) convertView.findViewById(R.id.storeAddress_Listview_NearbyFristPage);
            holder.buyOrder = (TextView) convertView.findViewById(R.id.buyOreder_Listview_NearbyFristPage);
            holder.storeDistance = (TextView) convertView.findViewById(R.id.stroeDistance_Listview_NearbyFristPage);
            holder.V = (ImageView) convertView.findViewById(R.id.vip_Image);
            holder.dui = (ImageView) convertView.findViewById(R.id.exchange_Image);
            holder.ji = (ImageView) convertView.findViewById(R.id.score_Image);
            holder.businessStroe_image = (ImageView) convertView.findViewById(R.id.image_fristListView_NearByFragment);
            holder.bei = (LinearLayout) convertView.findViewById(R.id.multiple_Image);
            holder.cutOff_rule = (View) convertView.findViewById(R.id.cut_off_rule_Image);
            holder.multiple_Image = (LinearLayout) convertView.findViewById(R.id.multiple_Image);
            holder.quan_Image = (LinearLayout) convertView.findViewById(R.id.img_quan);
            holder.bei_Image = (LinearLayout) convertView.findViewById(R.id.img_bei);
            holder.img_multiple = (ImageView) convertView.findViewById(R.id.img_multiple);
            // holder.img_bei=(ImageView)convertView.findViewById(R.id.img_bei);
            //holder.img_quan=(ImageView)convertView.findViewById(R.id.img_quan);
            holder.tv_store_msg = (TextView) convertView.findViewById(R.id.tv_store_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BusinessInfoAll allBean = mList.get(position);
        String businessName = allBean.getBusinessName();
        String area = allBean.getBusinessArea();
        if (businessName != null) {
            holder.storeName.setText(businessName + "");
        }
        if (area != null) {
            holder.storeAddress.setText(area + "");
        }
        //设置图片显示比例
        SetSizeUtils.setSizeOnlyWidthOf(mContext, holder.businessStroe_image, 3, 1, 16, 10);
        try {
            imageLoader.displayImage(allBean.getBusinessStoreImages().get(0), holder.businessStroe_image, options);
        } catch (Exception e) {

        }
        if (!allBean.getDistinct().equals("0")) {
            holder.storeDistance.setText(Float.parseFloat(allBean.getDistinct())*1000 + "m");
        } else {
            holder.storeDistance.setText("无信息");
        }
        if (allBean.getIsVip().equals("true")) {
            holder.V.setVisibility(View.VISIBLE);
        } else {
            holder.V.setVisibility(View.GONE);
        }
        if (allBean.getIsExchange().equals("兑")) {
            holder.dui.setVisibility(View.VISIBLE);
        } else {
            holder.dui.setVisibility(View.GONE);
        }
        if (allBean.getIsIntegral().equals("积")) {
            holder.ji.setVisibility(View.VISIBLE);
        } else {
            holder.ji.setVisibility(View.VISIBLE);
        }
        //此时没有减，如果有减的话，在加上 减的字段
        if (allBean.getIsDouble().equals("双")) {
            holder.cutOff_rule.setVisibility(View.VISIBLE);
        } else {
            holder.cutOff_rule.setVisibility(View.GONE);
        }
        if (allBean.getIsDouble().equals("双")) {
            holder.bei.setVisibility(View.VISIBLE);
        } else {
            holder.bei.setVisibility(View.GONE);
        }
        //显示商家优惠图标
        //值
        if (!allBean.getIsValue().equals("")) {
            holder.multiple_Image.setVisibility(View.VISIBLE);
        } else {
            holder.multiple_Image.setVisibility(View.GONE);
        }
        //倍
        if (!allBean.getIsDouble().equals("")) {
            holder.bei_Image.setVisibility(View.VISIBLE);
        } else {
            holder.bei_Image.setVisibility(View.GONE);
        }
        //券
        if (!allBean.getIsCoupons().equals("")) {
            holder.quan_Image.setVisibility(View.VISIBLE);
        } else {
            holder.quan_Image.setVisibility(View.GONE);
        }
        //显示优惠积分
        //
        String inter = allBean.getIntegralScale();
        holder.tv_store_msg.setText("会员消费100积" + inter + "分");


        /***************************************************************/
        //判断是否可以买单

        if (!allBean.isHavePay()) {
//            holder.buyOrder.setBackgroundResource(R.drawable.textview_background_stoke_helen);
//            holder.buyOrder.setTextColor(Color.parseColor("#AFAFAF"));
            holder.buyOrder.setEnabled(false);
//            holder.buyOrder.setVisibility(View.INVISIBLE);
            holder.buyOrder.setBackgroundResource(R.drawable.textview_background_stoke_dis);
            holder.buyOrder.setTextColor(Color.parseColor("#CCCCCC"));
        } else {

            holder.buyOrder.setEnabled(true);
//            holder.buyOrder.setVisibility(View.VISIBLE);
            holder.buyOrder.setTextColor(Color.parseColor("#E8231A"));
            holder.buyOrder.setBackgroundResource(R.drawable.textview_background_stoke);
            /*****************************************************/

        }

        holder.buyOrder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //跳转到买单详情
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("businessArea", allBean.getBusinessArea());
                intent.putExtra("businessName", allBean.getBusinessName());
                intent.putExtra("businessStoreImage", allBean.getBusinessStoreImages().get(0));
                intent.putExtra("businessID", allBean.getId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        private TextView storeName;
        private TextView storeAddress;
        private TextView buyOrder;
        private TextView storeDistance, tv_store_msg;
        private ImageView dui;
        private ImageView V;
        private ImageView ji;
        private ImageView businessStroe_image;
        private LinearLayout jian;
        private LinearLayout bei;
        private View cutOff_rule;
        private LinearLayout multiple_Image, quan_Image, bei_Image;
        private ImageView img_multiple, img_quan, img_bei;
    }

    public void reflishData(List<BusinessInfoAll> lists) {
        mList.clear();
        mList.addAll(lists);
        notifyDataSetChanged();
    }

    public void setData(List<BusinessInfoAll> lists) {
        mList = lists;
        notifyDataSetChanged();
    }
}
