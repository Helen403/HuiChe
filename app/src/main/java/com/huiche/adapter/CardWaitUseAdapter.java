package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.PostResult.MyCardResultData;
import com.huiche.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 * 个人中心，待使用卡券
 */
public class CardWaitUseAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyCardResultData> allData = new ArrayList<MyCardResultData>();
    private int cardStatus;

    public CardWaitUseAdapter(Context context, List<MyCardResultData> allData, int cardStatus) {
        this.mContext = context;
        this.allData = allData;
        this.cardStatus = cardStatus;

    }

    @Override
    public int getCount() {
        return allData.size();
    }

    @Override
    public Object getItem(int position) {
        return allData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private ViewHolder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card_di, null);
            holder = new ViewHolder();
            holder.ll_card = (LinearLayout) convertView.findViewById(R.id.ll_card);
            holder.tv_fuhao = (TextView) convertView.findViewById(R.id.tv_fuhao);
            holder.tv_money = (TextView) convertView.findViewById(R.id.my_tv_money);
            holder.card_type = (TextView) convertView.findViewById(R.id.card_type);
            holder.card_detail = (TextView) convertView.findViewById(R.id.my_card_detail);

            holder.card_status = (ImageView) convertView.findViewById(R.id.card_status);
            holder.card_time = (TextView) convertView.findViewById(R.id.card_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //待使用的卡券都是有背景颜色
        //couponsTypeId=4是通用券，粉色的，其他值就是蓝色的（有商家的）
        MyCardResultData data = allData.get(position);
        String couponsTypeId = data.getCouponsTypeId();
        String status = data.getStatus();
        //待使用
        if (cardStatus == 1) {
            if (couponsTypeId.equals("4")) {
                holder.ll_card.setBackgroundResource(R.drawable.pink_youhuiquan);
                holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.text_color_pink));
                holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.text_color_pink));
                holder.card_status.setVisibility(View.GONE);
                holder.card_type.setTextColor(mContext.getResources().getColor(R.color.small_blace));
            } else {
                holder.ll_card.setBackgroundResource(R.drawable.b_youhuiquan);
                holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.bule_title));
                holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.bule_title));
                holder.card_type.setTextColor(mContext.getResources().getColor(R.color.small_blace));
                holder.card_status.setVisibility(View.GONE);
            }
        }
        //已过期
        else if (cardStatus == 2) {
            holder.ll_card.setBackgroundResource(R.drawable.bianjiao);
            holder.card_status.setVisibility(View.VISIBLE);
            holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.deep_grey));
            holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.deep_grey));
            holder.card_type.setTextColor(mContext.getResources().getColor(R.color.deep_grey));
            holder.card_detail.setTextColor(mContext.getResources().getColor(R.color.ssmall_grey));
            holder.card_time.setTextColor(mContext.getResources().getColor(R.color.ssmall_grey));
        }
        //已使用
        else if (cardStatus == 3) {
            holder.ll_card.setBackgroundResource(R.drawable.bianjiao);
            holder.card_status.setVisibility(View.GONE);
            holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.deep_grey));
            holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.deep_grey));
            holder.card_type.setTextColor(mContext.getResources().getColor(R.color.deep_grey));
            holder.card_detail.setTextColor(mContext.getResources().getColor(R.color.ssmall_grey));
            holder.card_time.setTextColor(mContext.getResources().getColor(R.color.ssmall_grey));
        }
        if (couponsTypeId.equals("4")) {
            holder.card_type.setText("平台通用券");
        } else {
            holder.card_type.setText(data.getBusinessStoreName());
        }

        String money = data.getPrice();
        holder.tv_money.setText(money);
        String starTime = data.getStartTime();
        String endTime = data.getEndTime();
        holder.card_time.setText(starTime.substring(0, 10) + "至" + endTime.substring(0, 10));
        holder.card_detail.setText("满" + data.getDeduction() + "元可使用");
        holder.card_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ss = position;
            }
        });


        return convertView;
    }

    class ViewHolder {
        public TextView tv_fuhao, tv_money, card_type, card_detail, card_time;
        private LinearLayout ll_card;
        private ImageView card_status;
    }
}
