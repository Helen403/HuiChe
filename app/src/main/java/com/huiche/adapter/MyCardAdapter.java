package com.huiche.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.PayInfoCard;

import java.util.List;


/**
 * Created by Administrator on 2016/8/20.
 */
public class MyCardAdapter extends BaseAdapter {

    List<PayInfoCard.RowsBean> data;
    private double payMoney;
    private Context mContext;

    public MyCardAdapter(Context context, List<PayInfoCard.RowsBean> data, double payMoney) {
        this.data = data;
        this.payMoney = payMoney;
        this.mContext = context;
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

    private ViewHolder holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card_my, null);
            holder = new ViewHolder();
            holder.ll_card = (LinearLayout) convertView.findViewById(R.id.ll_card);
            holder.tv_money = (TextView) convertView.findViewById(R.id.my_card_tv_money);
            holder.card_type = (TextView) convertView.findViewById(R.id.oo_card_type);
            holder.card_detail = (TextView) convertView.findViewById(R.id.my_card_detail);
            holder.card_time = (TextView) convertView.findViewById(R.id.card_time);
            holder.tv_fuhao = (TextView) convertView.findViewById(R.id.tv_fuhao);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //当买单金额大于等于卡券使用规则时，背景为红色，可使用，否则不可使用 显示背景为灰色
        //需要注意的是，如果couponsTypeId=4是商家券，需要显示商家名称
        int duction = data.get(position).getDeduction();
        if (payMoney >= duction) {
            if (data.get(position).getCouponsTypeId() == 4) {
                holder.ll_card.setBackgroundResource(R.drawable.pink_youhuiquan);
                holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.text_color_pink));
                holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.text_color_pink));
                holder.card_type.setText(data.get(position).getCoupName());
            } else {
                holder.ll_card.setBackgroundResource(R.drawable.b_youhuiquan);
                holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.status_color));
                holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.status_color));
                String name = data.get(position).getBusinessStoreName();
                holder.card_type.setText(data.get(position).getBusinessStoreName());
            }

        } else {
            holder.ll_card.setBackgroundResource(R.drawable.bianjiao);
            holder.tv_fuhao.setTextColor(mContext.getResources().getColor(R.color.small_status));
            holder.tv_money.setTextColor(mContext.getResources().getColor(R.color.small_status));
        }
//        //设置数据
        int money = data.get(position).getPrice();
        holder.tv_money.setText(money + "");

        holder.card_detail.setText("满" + data.get(position).getDeduction() + "可以使用");
        String starTime = data.get(position).getStartTime();
        String endTime = data.get(position).getEndTime();
        holder.card_time.setText(starTime.substring(0, 10) + "至" + endTime.substring(0, 10));

        return convertView;
    }

    class ViewHolder {
        public TextView tv_money, card_type, card_detail, card_time, tv_fuhao;
        private LinearLayout ll_card;

    }
}
