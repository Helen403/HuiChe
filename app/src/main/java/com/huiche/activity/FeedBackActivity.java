package com.huiche.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.SuggestionBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.KeyBoardUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.Utils.VerificationUtils;
import com.huiche.lib.lib.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/***
 * @author Administrator
 *         意见反馈界面
 */

public class FeedBackActivity extends com.huiche.lib.lib.base.BaseActivity {
    private LinearLayout ll_dialog;
    private EditText edit_content, edit_phone;
    private TextView tv_content, tv_right_title;
    private TextView tv_gn, tv_jm, tv_xq, tv_cz, tv_ll, tv_qt;
    private LinearLayout ll_edit, ll_feedback;
    //0为初始用户编辑页面，1为选择意见反馈界面
    private int showType = 0;
    private String gnyj, jmyj, xqyj, czyj, llyj, qtyj;
    //反馈类型 0:功能意见1:界面意见2：您的新需求3：操作意见4：流量问题5：其他
    private int feedBackType = 6;
    private String feedBackMsg, feedBackContact;


    @Override
    public int getContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void findViews() {
        setTitle("意见反馈");
        getRightBtn().setText("提交");
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
        getRightBtn().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交意见
                saveFeedBack();
            }
        });
        setOnListeners(ll_dialog, tv_gn, tv_jm, tv_xq, tv_cz, tv_ll, tv_qt);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
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

                }
            }
        });


    }


    //保存意见
    private void saveFeedBack() {
        feedBackMsg = edit_content.getText().toString().trim();
        if (feedBackMsg.equals("")) {
            T("反馈内容不能为空");
            return;
        }
        feedBackContact = edit_phone.getText().toString().trim();
        if (feedBackContact.equals("")) {
            T("联系方式不能为空");
            return;
        }
        if (feedBackType == 6) {
            T("请选择反馈类型");
            return;
        }
        if (!VerificationUtils.matcherPhoneNum(feedBackContact)) {
            T("请输入正确的手机号码");
            return;
        }

        if (BaseApplication.loginResultBean.data.id == null) {
            T("请登录");
            return;
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", BaseApplication.loginResultBean.data.id);
        param.put("su_title", tv_content.getText().toString().trim());
        param.put("su_content", feedBackMsg);
        param.put("su_mobile", feedBackContact);

        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\",\"su_title\":\"").append(tv_content.getText().toString().trim()).append("\",\"su_content\":\"").append(feedBackMsg).append("\",\"su_mobile\":\"").append(feedBackContact).append("\"}");

        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.SUGGESTION, param, SuggestionBean.class, new ControlUtils.OnControlUtils<SuggestionBean>() {
            @Override
            public void onSuccess(String url, SuggestionBean suggestionBean, ArrayList<SuggestionBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(suggestionBean.msg);
                setRestart();
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });
    }


    //清除数据
    private void setRestart() {
        tv_content.setText("请选择您的反馈的信息");
        edit_content.setText("");
        edit_phone.setText("");
        KeyBoardUtils.closeKeybord(edit_content);
        KeyBoardUtils.closeKeybord(edit_phone);
    }


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
