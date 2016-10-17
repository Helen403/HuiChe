package com.huiche.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.PostResult.CollectionGoods;
import com.huiche.R;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ImagLoadUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyGoodsCollectionAdapter extends BaseAdapter {

    private IOnItemRightClickListener mListener = null;
    private List<CollectionGoods> data = new ArrayList<CollectionGoods>();
    private Context mContext;
    private int mRwidth;
    private SharedPreferences share;

    /**
     * 单击事件监听器
     */
    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }

    public MyGoodsCollectionAdapter(Context context,
                                    List<CollectionGoods> data, int width, IOnItemRightClickListener l) {
        this.mContext = context;
        this.data = data;
        mListener = l;
        this.mRwidth = width;
        share = context.getSharedPreferences("user_data", context.MODE_PRIVATE);
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
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_my_goods_collection, parent, false);
            holder = new ViewHolder();
            holder.tv_address = (TextView) convertView
                    .findViewById(R.id.tv_address);
            holder.tv_markey_price = (TextView) convertView
                    .findViewById(R.id.tv_markey_price);
            holder.tv_crad_money = (TextView) convertView
                    .findViewById(R.id.tv_crad_money);
            holder.tv_confirm = (TextView) convertView
                    .findViewById(R.id.tv_confirm);
            holder.aditem_right = (RelativeLayout) convertView
                    .findViewById(R.id.aditem_right);
            holder.img_goods = (ImageView) convertView
                    .findViewById(R.id.img_goods);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LayoutParams lp2 = new LayoutParams(mRwidth,
                LayoutParams.MATCH_PARENT);
        holder.aditem_right.setLayoutParams(lp2);
        String area = data.get(position).product.businessArea;
        String price = data.get(position).product.businessArea;
        ImagLoadUtils.setImage(data.get(position).product.goodsImgs[0],
                holder.img_goods, mContext);
        holder.tv_address.setText(data.get(position).product.businessArea);
        holder.tv_markey_price.setText("门店价"
                + data.get(position).product.marketPrice + "元");
        holder.tv_crad_money.setText(data.get(position).product.name);
        holder.aditem_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, position);
                }
            }
        });

//添加购物车
        holder.tv_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                params.put("count", 1);
                String iid = data.get(position).id;
                params.put("productId", data.get(position).getProduct().id);
                String token = share.getString("token", "");
                params.put("token", share.getString("token", token));
                AsyncHttp.posts(HttpConstantChc.ADD_GOODS_CART, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable, errorResponse);

                        Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status = response.getString("status");

                            if (status.equals("0")) {
                                boolean success = response.optBoolean("success");
                                if (success) {
                                    Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                });


            }
        });


        return convertView;
    }

    class ViewHolder {
        TextView tv_crad_money, tv_markey_price, tv_address, tv_confirm;
        private RelativeLayout aditem_right;
        private ImageView img_goods;

    }

}
