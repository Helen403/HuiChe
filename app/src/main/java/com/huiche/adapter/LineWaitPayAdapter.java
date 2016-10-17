package com.huiche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.PostResult.LineWaitDetail;
import com.huiche.PostResult.LineWaitPayInfo;
import com.huiche.R;
import com.huiche.activity.ExchangeOrderActivity;
import com.huiche.activity.PayDetailActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.DeleteDialog;
import com.huiche.view.MyListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineWaitPayAdapter extends BaseAdapter {
    private Context mContext;
    private DeleteDialog dialog;
    private List<LineWaitPayInfo> data = new ArrayList<LineWaitPayInfo>();
    private int location;

    public LineWaitPayAdapter(Context context, List<LineWaitPayInfo> data) {
        this.mContext = context;
        this.data = data;
        dialog = new DeleteDialog(context, "是否删除该订单");
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

    Holder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_line_waitpay, null);
            holder = new Holder();
            holder.myListview = (MyListView) convertView.findViewById(R.id.myListview);
            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_orderID);
            holder.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
            holder.tv_pay_type = (TextView) convertView.findViewById(R.id.tv_pay_type);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_intenal = (TextView) convertView.findViewById(R.id.tv_intenal);
            holder.tv_wait_pay = (TextView) convertView.findViewById(R.id.tv_wait_pay);
            holder.tv_cancel_grey = (TextView) convertView.findViewById(R.id.tv_cancel_grey);
            holder.ll_shop = (LinearLayout) convertView.findViewById(R.id.ll_shop);
            holder.ll_tip = (LinearLayout) convertView.findViewById(R.id.ll_tip);
            holder.line_view = (View) convertView.findViewById(R.id.line_view);
            holder.view_list = (View) convertView.findViewById(R.id.view_list);
            holder.delete_order = (ImageView) convertView.findViewById(R.id.delete_order);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_wait_pay.setVisibility(View.VISIBLE);
        holder.tv_cancel_grey.setVisibility(View.GONE);
        holder.view_list.setVisibility(View.GONE);

        LineWaitPayInfo info = data.get(position);
        boolean cancecal = info.canCancel;
//		if(cancecal){
//			holder.tv_wait_pay.setVisibility(View.VISIBLE);
//			holder.tv_cancel_grey.setVisibility(View.GONE);
//		}else if(!cancecal){
//			holder.tv_wait_pay.setVisibility(View.GONE);
//			holder.tv_cancel_grey.setVisibility(View.VISIBLE);
//		}

        holder.tv_order.setText(info.no);
        holder.tv_shop_name.setText(info.businessName);
        //验证码
        holder.tv_pay_type.setText(info.checkNo);
        holder.tv_date.setText(info.createDate);
        holder.tv_intenal.setText(info.allIntegral);
        List<LineWaitDetail> dataList = new ArrayList<LineWaitDetail>();
        dataList = data.get(position).productLists;
        if (data.size() > 0) {
            holder.ll_shop.setVisibility(View.VISIBLE);
            holder.line_view.setVisibility(View.VISIBLE);
            holder.view_list.setVisibility(View.VISIBLE);
            LineWaitPayDetailAdapter adapter = new LineWaitPayDetailAdapter(mContext, dataList);
            holder.myListview.setAdapter(adapter);
        } else {
            holder.ll_shop.setVisibility(View.GONE);
            holder.line_view.setVisibility(View.GONE);
            holder.view_list.setVisibility(View.GONE);
        }
        //付款按钮 orderType=4是买单模块，其他进入兑换模块
        holder.tv_wait_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String orderType = data.get(position).orderType;
                if (orderType.equals("4")) {
                    //进入买单模块,要对应传数据
                    intent.setClass(mContext, PayDetailActivity.class);
                    //这个id是订单id
                    intent.putExtra("rows", data.get(position).getId());
                    intent.putExtra("BusinessName", data.get(position).getBusinessName());
                    intent.putExtra("businessID", data.get(position).getBusinessStoreId());
                    //allintegral是积分，需要转化为元,除以10
                    String allin = data.get(position).getAllIntegral();
                    if (!allin.equals("")) {
                        double dall = Double.parseDouble(allin) * 0.1;
                        intent.putExtra("businessMoney", dall + "");
                    }

                    intent.putExtra("freeMoney", data.get(position).getFreeServiceQuota());
                    mContext.startActivity(intent);
                } else {
                    //进入兑换模块
                    String orderId = data.get(position).getId();

                    if (!orderId.equals("")) {
                        int offlineOrderId = Integer.parseInt(orderId);
                        intent = new Intent(mContext, ExchangeOrderActivity.class);
                        intent.putExtra("offlineOrderId", offlineOrderId);
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        //删除订单
        holder.delete_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除弹窗
                location = position;
                dialog.showDialog();

            }
        });

        dialog.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dimissDialog();
            }
        });

        dialog.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dimissDialog();
                deleteOrder(data.get(location).no, data.get(location).getId(), location);
            }
        });


        return convertView;
    }

    //删除商品
    public void deleteOrder(String no, String id, final int location) {
        RequestParams params = new RequestParams();
        params.put("no", no);
        params.put("id", id);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(mContext);
        bufferCircleDialog.show("正在加载", true, null);
        AsyncHttp.post(HttpConstantChc.DELETE_LINE_ORDER, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(mContext, "请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

//                super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    String success = response.getString("msg");
                    if (status.equals("0")) {
                        data.remove(location);
                        notifyDataSetChanged();
                        ToastUtils.ToastShowShort(mContext, success);
                    } else {
                        ToastUtils.ToastShowShort(mContext, success);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        });
    }


    public class Holder {
        public TextView tv_order, tv_shop_name, tv_pay_type, tv_date, tv_intenal, tv_button;
        public TextView tv_wait_pay, tv_cancel_grey;
        public MyListView myListview;
        public LinearLayout ll_shop, ll_tip;
        public View line_view, view_list;
        public ImageView delete_order;

    }


}
