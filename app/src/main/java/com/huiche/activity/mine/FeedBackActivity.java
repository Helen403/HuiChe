package com.huiche.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.IsMobileNumber;
import com.huiche.utils.TitleUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * @author Administrator
 *         意见反馈界面
 */

public class FeedBackActivity extends BaseActivity implements OnClickListener {
    private LinearLayout ll_dialog;
    private EditText edit_content, edit_phone;
    private TextView tv_content, tv_right_title;
    private Context mContext;
    private TextView tv_gn, tv_jm, tv_xq, tv_cz, tv_ll, tv_qt;
    private LinearLayout ll_edit, ll_feedback;
    //0为初始用户编辑页面，1为选择意见反馈界面
    private int showType = 0;
    private String gnyj, jmyj, xqyj, czyj, llyj, qtyj;
    //反馈类型 0:功能意见1:界面意见2：您的新需求3：操作意见4：流量问题5：其他
    private int feedBackType = 6;
    private String feedBackMsg, feedBackContact;
    private SharedPreferences share;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_feed_back);
        TitleUtils.setInfoText(this, "意见反馈", "提交");
        mContext = this;
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        ll_dialog = (LinearLayout) findViewById(R.id.ll_dialog);
        tv_content = (TextView) findViewById(R.id.tv_content);
        edit_content = (EditText) findViewById(R.id.edit_content);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        ll_edit = (LinearLayout) findViewById(R.id.ll_edit);
        ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);


        //意见列表
        //找到控件tv_gn,tv_jm,tv_xq,tv_cz,tv_ll,tv_qt;
        tv_gn = (TextView) findViewById(R.id.tv_gn);
        tv_jm = (TextView) findViewById(R.id.tv_jm);
        tv_xq = (TextView) findViewById(R.id.tv_xq);
        tv_cz = (TextView) findViewById(R.id.tv_cz);
        tv_ll = (TextView) findViewById(R.id.tv_ll);
        tv_qt = (TextView) findViewById(R.id.tv_qt);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        ll_dialog.setOnClickListener(this);
        //点击事件
        tv_gn.setOnClickListener(this);
        tv_jm.setOnClickListener(this);
        tv_xq.setOnClickListener(this);
        tv_cz.setOnClickListener(this);
        tv_ll.setOnClickListener(this);
        tv_qt.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dialog:
                isShowFeedBack(showType);

                break;
            //功能意见
            case R.id.tv_gn:
                showType = 1;
                feedBackType = 0;
                isShowFeedBack(showType);
                gnyj = tv_gn.getText().toString();
                writeFeedBack(gnyj);
                break;

            //界面意见
            case R.id.tv_jm:
                showType = 1;
                feedBackType = 1;
                isShowFeedBack(showType);
                jmyj = tv_jm.getText().toString();
                writeFeedBack(jmyj);
                break;
            //您的新需求
            case R.id.tv_xq:
                showType = 1;
                feedBackType = 2;
                isShowFeedBack(showType);
                xqyj = tv_xq.getText().toString();
                writeFeedBack(xqyj);
                break;
            //操作意见
            case R.id.tv_cz:
                showType = 1;
                feedBackType = 3;
                isShowFeedBack(showType);
                czyj = tv_cz.getText().toString();
                writeFeedBack(czyj);
                break;
            //流量问题
            case R.id.tv_ll:
                showType = 1;
                feedBackType = 4;
                isShowFeedBack(showType);
                llyj = tv_ll.getText().toString();
                writeFeedBack(llyj);
                break;
            //其他
            case R.id.tv_qt:
                showType = 1;
                feedBackType = 5;
                isShowFeedBack(showType);
                qtyj = tv_qt.getText().toString();
                writeFeedBack(qtyj);

                break;
            //提交意见
            case R.id.tv_right_title:
                feedBackMsg = edit_content.getText().toString().trim();
                if (feedBackMsg.equals("")) {
                    Toast.makeText(mContext, "反馈内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                feedBackContact = edit_phone.getText().toString().trim();
                if (feedBackContact.equals("")) {
                    Toast.makeText(mContext, "联系方式不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (feedBackType == 6) {
                    Toast.makeText(mContext, "请选择反馈类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isPhone = IsMobileNumber.isMobileNO(feedBackContact);
                if (!isPhone) {
                    Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveFeedBack();
                break;
            default:
                break;
        }

    }


    //保存意见

    private void saveFeedBack() {
        RequestParams params = new RequestParams();
        params.put("feedBackType", feedBackType);
        params.put("feedBackMsg", feedBackMsg);
        params.put("feedBackContact", feedBackContact);
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(FeedBackActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.FEED_BACK, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
                bufferCircleDialog.dialogcancel();
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
                        Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
                        tv_content.setText("");
                        edit_content.setText("");
                        edit_phone.setText("");
                    } else {
                        Toast.makeText(mContext, success, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });


    }

//	//创建popwindow
//	public void initPoputWindow(){
//		View view=LayoutInflater.from(mContext).inflate(R.layout.feed_back_popwindow, null);
//		popupWindow=new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
//		// 点击其他地方消失
//		view.setOnTouchListener(new OnTouchListener() {
//
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//						if (popupWindow != null && popupWindow.isShowing()) {
//							popupWindow.dismiss();
//							popupWindow = null;
//						}
//						return false;
//					}
//				});
//		
//		// 设置关闭pop时复原Activity背景
//				popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//					@Override
//					public void onDismiss() {
//						// 修改Activity背景
//						WindowManager.LayoutParams params = getWindow().getAttributes();
//						params.alpha = 1f;
//						getWindow().setAttributes(params);
//					}
//				});
//				//找到控件tv_gn,tv_jm,tv_xq,tv_cz,tv_ll,tv_qt;
//				tv_gn=(TextView)view.findViewById(R.id.tv_gn);
//				tv_jm=(TextView)view.findViewById(R.id.tv_jm);
//				tv_xq=(TextView)view.findViewById(R.id.tv_xq);
//				tv_cz=(TextView)view.findViewById(R.id.tv_cz);
//				tv_ll=(TextView)view.findViewById(R.id.tv_ll);
//				tv_qt=(TextView)view.findViewById(R.id.tv_qt);
//				//点击事件
//				tv_gn.setOnClickListener(this);
//				tv_jm.setOnClickListener(this);
//				tv_xq.setOnClickListener(this);
//				tv_cz.setOnClickListener(this);
//				tv_ll.setOnClickListener(this);
//				tv_qt.setOnClickListener(this);
//				
//	}
//	
//	/***
//	 * 获取PopupWindow实例
//	 */
//	private void getPopupWindow() {
//		if (null != popupWindow) {
//			popupWindow.dismiss();
//			return;
//		} else {
//			initPoputWindow();
//		}
//	}
//	
//	private void showAllProductPupWindow() {
//		getPopupWindow();
//		popupWindow.showAsDropDown(ll_dialog);
//		// 修改Activity背景为半透明
//		WindowManager.LayoutParams params = getWindow().getAttributes();
//		params.alpha = 0.7f;
//		getWindow().setAttributes(params);
//
//	}

    //是否显示意见反馈列表界面

    public void isShowFeedBack(int type) {
        if (type == 0) {
            showType = 1;
            ll_edit.setVisibility(View.GONE);
            ll_feedback.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            showType = 0;
            ll_edit.setVisibility(View.VISIBLE);
            ll_feedback.setVisibility(View.GONE);
        }

    }

    public void writeFeedBack(String content) {
        tv_content.setText(content);
    }

}
