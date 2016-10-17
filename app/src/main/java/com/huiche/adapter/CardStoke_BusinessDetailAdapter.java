package com.huiche.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.LoginActivity;
import com.huiche.activity.mine.MyCardQuanActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.CardStokeInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.WindowUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class CardStoke_BusinessDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<CardStokeInfo> dataList;
    private WindowUtils windowUtils;
    private AlertDialog notifyDialog;
    private TextView tv_dialogNormal_title;
    private TextView tv_cancelNormal;
    private TextView tv_confirmNormal;
    private TextView tv_dialogNormal_content;

    public CardStoke_BusinessDetailAdapter(Context context, List<CardStokeInfo> data) {
        mContext = context;
        dataList = data;
        windowUtils = new WindowUtils(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_card_business, null);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_cardStock_price);
            viewHolder.produceName = (TextView) convertView.findViewById(R.id.tv_productName_cardQuan);
            viewHolder.useLimit = (TextView) convertView.findViewById(R.id.tv_cardStock_useLimit);
            viewHolder.usableTime = (TextView) convertView.findViewById(R.id.tv_usable_time);
            viewHolder.clickGet = (LinearLayout) convertView.findViewById(R.id.ll_clickGet);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CardStokeInfo info = dataList.get(position);
        viewHolder.price.setText(info.price);
        viewHolder.produceName.setText(info.businessName);
        viewHolder.useLimit.setText("满" + info.deduction + "可使用");
        //去掉后面的时分秒00：00:00
        String startTime = "";
        String endTime = "";
        if (info.startTime.length() > 10) {
            startTime = info.startTime.subSequence(0, 10).toString();
        }
        if (info.endTime.length() > 10) {
            endTime = info.endTime.subSequence(0, 10).toString();
        }
//        viewHolder.usableTime.setText(info.startTime + " 至 " + info.endTime);
        viewHolder.usableTime.setText(startTime + " 至 " + endTime);
        viewHolder.clickGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击领取
                getCardQuan(info.id);
            }
        });

        return convertView;

    }

    /**
     * 会员获取卡券
     */
    private void getCardQuan(int id) {
        if (MyApplication.token.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请先登录");
            /*************************************************************/
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent1);
            /*************************************************************/
            return;
        }
        final RequestParams params = new RequestParams();
        params.put("id", id);
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.GET_CARD_QUAN, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "请检查网络");
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                dialog.dialogcancel();
                String msg = response.optString("msg");
                String status = response.optString("status");
                int errorCode = response.optInt("errorCode");
                if (status.equals("0")) //领取成功
                    errorCode = -1;
                showDialog(msg, errorCode);
            }
        });
    }

    /**
     * 显示结果弹框
     */
    private void showDialog(String msg, int errorCode) {
        if (notifyDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = View.inflate(mContext, R.layout.dialog_normal, null);
            tv_dialogNormal_title = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_title);
            tv_cancelNormal = (TextView) dialogView.findViewById(R.id.tv_cancelNormal);
            tv_dialogNormal_content = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_content);
            tv_confirmNormal = (TextView) dialogView.findViewById(R.id.tv_confirmNormal);
            notifyDialog = builder.show();
            Window dialogWindow = notifyDialog.getWindow();
            dialogWindow.setContentView(dialogView);
            int width = windowUtils.getdisplayWidth();
            dialogWindow.setContentView(dialogView);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //显示比例宽度5/7
            lp.width = width * 5 / 7;
//            lp.height = lp.width*10/16;
            dialogWindow.setAttributes(lp);
        } else {
            notifyDialog.show();
        }
        if (errorCode == -1) {
            //领取成功
            tv_dialogNormal_title.setText(msg);
            tv_dialogNormal_content.setText("您的卡券已经放入卡包");
            tv_cancelNormal.setText("关闭");
            tv_confirmNormal.setText("点击查看");
            tv_confirmNormal.setVisibility(View.VISIBLE);
        } else {
            tv_dialogNormal_title.setText("领取失败");
            tv_dialogNormal_content.setText(msg);
            tv_confirmNormal.setVisibility(View.GONE);
            tv_cancelNormal.setText("关闭");
        }
        tv_cancelNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDialog.dismiss();
            }
        });
        tv_confirmNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyCardQuanActivity.class);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        });

    }

    public class ViewHolder {
        TextView price;
        TextView produceName;
        TextView useLimit;
        TextView usableTime;
        LinearLayout clickGet;
    }

}
