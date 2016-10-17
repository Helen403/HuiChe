package com.huiche.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.huiche.activity.PayOrderResultActivity;
import com.huiche.base.MyApplication;
import com.huiche.constant.MyRequestCode;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private final String ACTION_NAME ="UPDATE";

	//兑换时取消支付
	private final String DUIHUANFAIL="DUIHUANFAIL";
	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_wxpayentry);
		api=WXAPIFactory.createWXAPI(this, MyApplication.appid);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//		Log.d("alipay", "onPayFinish, errCode = " + resp.errCode);
		//兑换支付成功
		if (resp.errCode == 0 && MyApplication.payTyPe==2) {
//			Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
			Intent broIntent = new Intent();
			broIntent.setAction(MyRequestCode.FINISH);
			sendBroadcast(broIntent);
//			Intent intent = new Intent(WXPayEntryActivity.this, PaySuccessActivity.class);
//			startActivity(intent);
			finish();
		}
		//买单支付成功
		else if(resp.errCode==0 && MyApplication.payTyPe==1){
                Intent intent=new Intent(WXPayEntryActivity.this, PayOrderResultActivity.class);
			startActivity(intent);
			Intent mIntent = new Intent(ACTION_NAME);
			//发送广播
			sendBroadcast(mIntent);
			finish();
		}
		else if (resp.errCode == -1) {
			Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
			finish();
		}
		//兑换进入的微信支付
		else if(resp.errCode==-2 && MyApplication.payTyPe==2){
			Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT).show();
			Intent broIntent = new Intent();
			broIntent.setAction(MyRequestCode.FINISH);
			sendBroadcast(broIntent);
			finish();
		}
		//买单微信支付的返回结果
		else if(resp.errCode==-2 && MyApplication.payTyPe==1){
			Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT).show();
			Intent mIntent = new Intent(ACTION_NAME);
			//发送广播
			sendBroadcast(mIntent);
			finish();

		}


	}
}