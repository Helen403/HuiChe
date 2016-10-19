package com.huiche.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huiche.PostResult.NotifyInfo;
import com.huiche.R;
import com.huiche.activity.LoginActivity;
import com.huiche.activity.MainActivity;
import com.huiche.activity.mine.ApplyManActivity;
import com.huiche.activity.mine.CarManagerFisterActivity;
import com.huiche.activity.mine.ChangePhoneActivity;
import com.huiche.activity.mine.DetailsActivity;
import com.huiche.activity.mine.FeedBackActivity;
import com.huiche.activity.mine.GoodsReceiptActivity;
import com.huiche.activity.mine.LineFinishOrderActivity;
import com.huiche.activity.mine.LineOrderActivity;
import com.huiche.activity.mine.LineUnPayActivity;
import com.huiche.activity.mine.LineWaitPayActivity;
import com.huiche.activity.mine.MessageActivity;
import com.huiche.activity.mine.MyCardQuanActivity;
import com.huiche.activity.mine.MyCollectionsActivity;
import com.huiche.activity.mine.MyCommentActivity;
import com.huiche.activity.mine.MyjiFenActivity;
import com.huiche.activity.mine.PersionalMessageActivity;
import com.huiche.activity.mine.SecurityHelenActivity;
import com.huiche.activity.mine.WebViewActivity;
import com.huiche.adapter.MyPersionalAdapter;
import com.huiche.base.BaseFragment;
import com.huiche.base.MyApplication;
import com.huiche.customer_view.MyGridView;
import com.huiche.share.onekeyshare.OnekeyShare;
import com.huiche.share.onekeyshare.OnekeyShareTheme;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ImagLoadUtils;
import com.huiche.view.CircleImageView;
import com.huiche.view.ShareDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * 我的(个人中心)fragment
 *
 * @author Administrator
 */
public class MineFragmentTTTTTTTTTTT extends BaseFragment implements OnClickListener {
    private View convertView;
    private MyGridView mygridview;
    private String[] strType;
    private int[] imgData;
    private Context context;
    private MyPersionalAdapter adapter;
    private LinearLayout lucky_draw, sign_day, ll_recommend;
    private LinearLayout ll_integral_surplus;
    private static SharedPreferences share;
    private Editor editor;
    private RelativeLayout rl_line_order;
    private LinearLayout ll_integral_income;
    private LinearLayout ll_integral_used;
    private RelativeLayout ll_wait_pay, ll_un_pay, ll_finish, ll_wait_evaluate;
    private LinearLayout ll_message;
    private TextView tv_income, tv_outcome, tv_integral, tv_card, tv_phone,
            tv_name, tv_current_notify, tv_next_notify;
    private ImageButton btn_message;
    private CircleImageView user_img;
    private ImageButton imb_title_bar_back;
    private LinearLayout ll_notlogin;
    private LinearLayout ll_user_integral;
    private LinearLayout ll_user_login;

    private String kfNumber;
    private List<NotifyInfo> notifyList = new ArrayList<NotifyInfo>();
    //    List<NotifyInfo> notifyLoadmoreList = new ArrayList<NotifyInfo>();
    private String in_integral, out_integral;
    private String ACTION_NAME = "UPDATE_MINE_MESSAGE";
    private PullToRefreshScrollView scrollView;
    //是否滑动了scrollview刷新数据
    private boolean isHAU = false;
    private ImageView red_bao;
    private ShareDialog shareDialog;


    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        convertView = inflater
                .inflate(R.layout.fragment_mine, container, false);
        context = getActivity();
        return convertView;
    }

    @Override
    public void findViews() {
        share = context.getSharedPreferences("user_data", context.MODE_PRIVATE);
        editor = share.edit();
        mygridview = (MyGridView) convertView.findViewById(R.id.mygridview);
        lucky_draw = (LinearLayout) convertView.findViewById(R.id.lucky_draw);
        sign_day = (LinearLayout) convertView.findViewById(R.id.sign_day);
        ll_recommend = (LinearLayout) convertView
                .findViewById(R.id.ll_recommend);
        ll_integral_surplus = (LinearLayout) convertView
                .findViewById(R.id.ll_integral_surplus);
        rl_line_order = (RelativeLayout) convertView
                .findViewById(R.id.rl_line_order);
        ll_integral_income = (LinearLayout) convertView
                .findViewById(R.id.ll_integral_income);
        ll_integral_used = (LinearLayout) convertView
                .findViewById(R.id.ll_integral_used);
        tv_integral = (TextView) convertView.findViewById(R.id.tv_integral);
        tv_income = (TextView) convertView.findViewById(R.id.tv_income);
        tv_outcome = (TextView) convertView.findViewById(R.id.tv_outcome);
        ll_message = (LinearLayout) convertView.findViewById(R.id.ll_message);
        tv_current_notify = (TextView) convertView.findViewById(R.id.tv_current_notify);
        tv_next_notify = (TextView) convertView.findViewById(R.id.tv_current_notify);
        scrollView = (PullToRefreshScrollView) convertView.findViewById(R.id.scrollView_fragmentMine);
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        // 线下订单控件
        ll_wait_pay = (RelativeLayout) convertView.findViewById(R.id.ll_wait_pay);
        ll_un_pay = (RelativeLayout) convertView.findViewById(R.id.ll_un_pay);
        ll_finish = (RelativeLayout) convertView.findViewById(R.id.ll_finish);
        ll_wait_evaluate = (RelativeLayout) convertView
                .findViewById(R.id.ll_wait_evaluate);
        tv_card = (TextView) convertView.findViewById(R.id.tv_card);
        tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
        tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        btn_message = (ImageButton) convertView.findViewById(R.id.btn_message);
        user_img = (CircleImageView) convertView.findViewById(R.id.user_img);
        imb_title_bar_back = (ImageButton) convertView.findViewById(R.id.imb_title_bar_back);
        ll_notlogin = (LinearLayout) convertView.findViewById(R.id.ll_notlogin);
        ll_user_integral = (LinearLayout) convertView.findViewById(R.id.ll_user_integral);
        ll_user_login = (LinearLayout) convertView.findViewById(R.id.ll_user_login);

        shareDialog = new ShareDialog(getActivity());
        red_bao = (ImageView) convertView.findViewById(R.id.red_bao);
        initRes();
    }

    @Override
    public void initData() {
        //注册消息更新广播
        registerBoradcastReceiver();
        adapter = new MyPersionalAdapter(context, strType, imgData);
        mygridview.setAdapter(adapter);
        getDataFromWeb();
    }

    public void getDataFromWeb() {

        if (MyApplication.token.equals(""))
            return;
        else {
            ll_notlogin.setVisibility(View.GONE);
            ll_user_login.setVisibility(View.VISIBLE);
            ll_user_integral.setVisibility(View.VISIBLE);
        }
        //重新进入fragment的时候焦点在顶部
        mygridview.setFocusable(false);
        scrollView.scrollTo(0, 0);
        GetUserIntegral();
        String cardNo = share.getString("cardNo", "");
        String phone = share.getString("phone", "");
        String name = share.getString("name", "");
        tv_card.setText("卡号:" + cardNo);
        if (phone.equals("")) {
            tv_phone.setText("绑定手机");
        } else {
            if (phone.length() >= 7) {
                tv_phone.setText(phone.substring(0, 3) + "****"
                        + phone.substring(7, phone.length()));
            }
        }
        tv_name.setText(name);
        //获取会员信息
        GetUserMessage();
        //获取系统公告信息
        //当scroll滑动，刷新数据时，防止页面闪动公告信息不刷新
        GetSystemMessage();


    }

    //获取系统公告信息
    private void GetSystemMessage() {
        notifyList.clear();
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("receiveIdentity", 6);
        params.put("page", 1);
        params.put("rows", 99);
        AsyncHttp.posts(HttpConstantChc.GET_SYSTEM_NESSAGE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        try {
                            if (isHAU) {
                                scrollView.onRefreshComplete();
                                isHAU = false;
                            }
                            String msg = response.getString("msg");
                            String status = response.getString("status");
                            if (status.equals("0")) {
                                JSONArray array = response.getJSONArray("rows");
                                notifyList = JSON.parseArray(array.toString(), NotifyInfo.class);
                                TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.system_message_up);
                                tv_current_notify.setText("公告消息");
                                tv_current_notify.startAnimation(animation);
                                //消息循环
                                notifyRunning();
                            } else {
                                tv_current_notify.setText("公告消息");
                                Toast.makeText(getActivity(), msg,
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        tv_current_notify.setText("公告消息");
                        Toast.makeText(getActivity(), "请检查网络",
                                Toast.LENGTH_SHORT).show();
                        if (isHAU) {
                            scrollView.onRefreshComplete();
                            isHAU = false;
                        }
                    }

                });
    }

    RotateAnimation animation;


    public void statAnimation() {
        animation = new RotateAnimation(+45, -45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(800);
        animation.setRepeatMode(RotateAnimation.REVERSE);
        animation.setRepeatCount(2);
        MainActivity activity = (MainActivity) getActivity();
        if (red_bao != null && activity.bufferRedBaoViewDialog.getVisibility() == View.GONE)
            red_bao.startAnimation(animation);
    }


    @Override
    public void setListeners() {
        /**********************************************************/
        tv_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePhoneActivity.class);
                startActivityForResult(intent, 1698);
            }
        });
        /**********************************************************/


        red_bao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.charRedBao();
                red_bao.clearAnimation();
            }
        });


        ll_integral_income.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_integral_used.setOnClickListener(this);
        rl_line_order.setOnClickListener(this);
        ll_wait_pay.setOnClickListener(this);
        ll_un_pay.setOnClickListener(this);
        ll_finish.setOnClickListener(this);
        ll_wait_evaluate.setOnClickListener(this);
        lucky_draw.setOnClickListener(this);
        sign_day.setOnClickListener(this);
        ll_recommend.setOnClickListener(this);
        ll_integral_surplus.setOnClickListener(this);
        btn_message.setOnClickListener(this);

        //当前公告信息
        tv_current_notify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifyList.size() > 0) {
                    MessageDialog(currentCount);
                    showMessageDialog();
                }
            }
        });

        //下一条公告信息
        tv_next_notify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notifyList.size() > 0) {
                    MessageDialog(nextCount);
                    showMessageDialog();
                }
            }
        });
        ll_notlogin.setOnClickListener(this);

        mygridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                if ((share.getString("token", "")).equals("")) {
                    intent.setClass(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                switch (position) {
                    //线上订单
                    case 0:

                        intent.setClass(context, MyCollectionsActivity.class);
                        startActivity(intent);
                        break;
                    //我的红包
                    case 1:


                        intent.setClass(context, MyCommentActivity.class);
                        startActivity(intent);


                        break;

                    //车辆管理
                    case 2:
                        intent.setClass(context, CarManagerFisterActivity.class);
                        startActivity(intent);
                        break;

                    //收货地址
                    //联系客服
                    case 3:
                        //获取客服电话
                        getPhone();

                        break;
                    case 4:
                        intent.setClass(context, SecurityHelenActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(context, GoodsReceiptActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(context, FeedBackActivity.class);
                        startActivity(intent);
                        break;
                    //意见反馈
                    case 7:
                        Intent intent1 = new Intent(getActivity(),
                                DetailsActivity.class);
                        startActivity(intent1);
                        break;
                    case 8:
                        //申请合伙人
                        intent.setClass(context, ApplyManActivity.class);
                        startActivity(intent);
                        break;


                }
            }
        });

        //页面刷新
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //重新请求数据
                    isHAU = true;
                    getDataFromWeb();
                    scrollView.onRefreshComplete();
                }
            }
        });

    }

    //获取服务器客服电话
    private void getPhone() {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        AsyncHttp.post(HttpConstantChc.GET_SYSTEM_PHONE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        try {

                            String msg = response.getString("msg");
                            String status = response.getString("status");
                            kfNumber = response.getString("rows");
                            if (status.equals("0")) {
                                initDialog(kfNumber, "取消", "呼叫");
                                showDialog();


                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT)
                                .show();
                    }

                });


    }

    private void initRes() {
        strType = new String[]{"我的订单", "我的评价", "车辆管理", "联系客服", "安全设置", "收获地址", "意见反馈", "我的油卡", "申请合伙人"};
        imgData = new int[]{R.drawable.the_online_order, R.drawable.hongbao_xsm, R.drawable.my_card_volume, R.drawable.yellow_book
                , R.drawable.my_collection, R.drawable.my_evaluation, R.drawable.security_settings, R.drawable.feedback, R.drawable.contact_customerservice};
    }

    @Override
    public void initViews() {
    }

    /**
     * 获取会员积分记录
     */
    private void GetUserIntegral() {
        /*****************************************************/
        //余额
        tv_income.setText("+0.0");
        //支出
        tv_outcome.setText("-0.0");
        //积分
        tv_integral.setText("0.0");


        /*****************************************************/

        RequestParams params = new RequestParams();
        AsyncHttp.post(HttpConstantChc.USER_INTEGRAL, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        try {
                            String msg = response.getString("msg");
                            String status = response.getString("status");
                            if (status.equals("0")) {
                                JSONObject obj = response.getJSONObject("rows");

                                in_integral = obj.optString("income");
                                out_integral = obj.optString("expenditure");
                                String sum_integral = obj.optString("integral");

                                if (!in_integral.equals("") && !out_integral.equals("") && !sum_integral.equals("")) {
                                    //收入
                                    double inf = Double.parseDouble(in_integral);
                                    String ins;
                                    if (inf > 0) {
                                        ins = new java.text.DecimalFormat("#.0").format(inf);
                                        in_integral = ins;
                                        tv_income.setText("+" + ins);
                                    } else {
                                        in_integral = "0.0";
                                        tv_income.setText("+0.0");
                                    }


                                    //支出
                                    double outf = Double.parseDouble(out_integral);
                                    String outs;
                                    if (outf > 0) {
                                        outs = new java.text.DecimalFormat("#.0").format(outf);
                                        out_integral = outs;
                                        tv_outcome.setText("-" + outs);
                                    } else {
                                        out_integral = "0.0";
                                        tv_outcome.setText("-0.0");
                                    }


                                    //余额
                                    double sumf = Double.parseDouble(sum_integral);
                                    String sums;
                                    if (sumf > 0) {
                                        sums = new java.text.DecimalFormat("#.0").format(sumf);
                                        tv_integral.setText(sums);
                                    } else {

                                        tv_integral.setText("0.0");
                                    }


                                }


                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                                        .show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT)
                                .show();
                    }

                });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if ((share.getString("token", "")).equals("")) {
            intent.setClass(context, LoginActivity.class);
            startActivity(intent);
            return;
        }
        switch (v.getId()) {
            // 积分抽奖
            case R.id.lucky_draw:
                intent.setClass(context, WebViewActivity.class);
                intent.putExtra("url", "http://test.51ujf.cn/mobile/luckdraw.html?token=" + (share.getString("token", "")) + "&v=1000");
                intent.putExtra("title", "积分抽奖");
                startActivity(intent);
                break;
            // 日常签到
            case R.id.sign_day:
                intent.setClass(context, WebViewActivity.class);
                intent.putExtra("url", "http://test.51ujf.cn/mobile/sign.html?v=1000");
                intent.putExtra("title", "每日签到");
                startActivity(intent);
                break;
            // 推荐好友
            case R.id.ll_recommend:
                showShareDialog();
                break;

            //余额  积分
            case R.id.ll_integral_surplus:
                intent.setClass(context, MyjiFenActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_line_order:
                intent.setClass(context, LineOrderActivity.class);
                startActivity(intent);
                break;

            // 我的卡卷
            case R.id.ll_integral_income:
                intent.setClass(context, MyCardQuanActivity.class);
                startActivity(intent);

                break;
            // 我的收藏
            case R.id.ll_integral_used:
                intent.setClass(context, MyCollectionsActivity.class);
                startActivity(intent);
                break;

            // 线下订单--代付款
            case R.id.ll_wait_pay:
                intent.setClass(context, LineWaitPayActivity.class);
                startActivity(intent);
                break;

            // 线下订单--未消费
            case R.id.ll_un_pay:
                intent.setClass(context, LineUnPayActivity.class);
                startActivity(intent);
                break;
            // 线下订单--已完成
            case R.id.ll_finish:
                intent.setClass(context, LineFinishOrderActivity.class);
                startActivity(intent);
                break;
            // 线下订单--待评价
            case R.id.ll_wait_evaluate:
                //intent.setClass(context, LineWaitEvaluateActivity.class);
                intent.setClass(context, WebViewActivity.class);
                intent.putExtra("url", "http://test.51ujf.cn/mobile/order.html?orderType=uncomment&v=1000");
                startActivity(intent);
                break;
            // 个人信息修改
            case R.id.ll_message:
                intent.setClass(context, PersionalMessageActivity.class);
                startActivityForResult(intent, 1010);
                break;
            case R.id.btn_message:
                intent.setClass(context, MessageActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_cancelNormal:
                dimissDialog();
                break;
            case R.id.tv_confirmNormal:
                dimissDialog();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + kfNumber));
                // 开启系统拨号器
                startActivity(intent);
                break;

            //点击微信分享

            case R.id.tv_weixin:
                showShare(getActivity(), "Wechat", true);
                shareDialog.dismiss();
                break;
            //朋友圈
            case R.id.tv_pyq:
                showShare(getActivity(), "WechatMoments", true);
                shareDialog.dismiss();

                break;
            //QQ
            case R.id.tv_qq:
                showShare(getActivity(), "QQ", true);
                shareDialog.dismiss();
                break;
            case R.id.tv_share_cancel:
                shareDialog.dismiss();
                break;


        }
    }

    private void showShareDialog() {

        shareDialog.show();
        shareDialog.tv_share_cancel.setOnClickListener(this);
        shareDialog.img_pyq.setOnClickListener(this);
        shareDialog.img_qq.setOnClickListener(this);
        shareDialog.img_weixin.setOnClickListener(this);

    }

    //自定义分享内容

    /*********************************************/
    public void showShare(Context context, String platformToShare,
                          boolean showContentEdit) {
        String tmp = "https://www.51ujf.cn/wxRec.do?recid=%s";
        String url = String.format(tmp, share.getString("id", ""));
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        // ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC 第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        // oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle("我是" + account + ",喊你一起来抢“钱”啦!");
        oks.setTitleUrl(url);// QQ分享
        oks.setText("要玩就来狠的，赚钱必须这么容易!");
        oks.setImageUrl(wxHeadimage);
        oks.setVenueName("优积分");
        oks.setVenueDescription("This is a beautiful place!");
        assert platformToShare != null;
        if (platformToShare.equals(Wechat.NAME)
                || platformToShare.equals(WechatMoments.NAME)) {
            oks.setUrl(url); // 微信不绕过审核分享链接
            MyApplication.isWXlogin = false;
        }
        Bitmap enableLogo = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ic_launcher);
        Bitmap disableLogo = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ic_launcher);
        String label = "ShareSDK";
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
            }
        };

        // 启动分享
        oks.show(context);
    }



    private String account;
    private String attributionCity;
    private String attributionProvince;
    private String day;
    private String email;
    //    private String integral;
    private String province;
    private String year;
    private String city;
    private String month;
    private String wxHeadimage;
    private String sex;

    //获取会员信息
    private void GetUserMessage() {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("id", share.getString("id", ""));
        //缓冲小菊花
        AsyncHttp.posts(HttpConstantChc.GET_USER_MESSAGE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        try {
                            if (isHAU) {
                                scrollView.onRefreshComplete();
                                isHAU = false;
                            }
                            String msg = response.getString("msg");
                            String status = response.getString("status");
                            if (status.equals("0")) {
                                JSONObject obj = response.getJSONObject("rows");
                                account = obj.optString("account", "");
                                attributionCity = obj.optString("attributionCity", "");
                                attributionProvince = obj.optString("attributionProvince", "");
                                email = obj.optString("email", "");
                                wxHeadimage = obj.optString("wxHeadimage", "");
                                sex = obj.optString("sex", "");
                                province = obj.optString("province", "");
                                city = obj.optString("city", "");
                                year = obj.optString("year", "");
                                month = obj.optString("month", "");
                                day = obj.optString("day", "");
                                String area = obj.optString("area");
                                //将会员信息存入本地
                                if (wxHeadimage.equals("")) {
                                    editor.putString("wxHeadimage", "");
                                }
                                editor.putString("account", account);
                                editor.putString("attributionCity", attributionCity);
                                editor.putString("attributionProvince", attributionProvince);
                                editor.putString("email", email);
                                editor.putString("wxHeadimage", wxHeadimage);
                                editor.putString("sex", sex);
                                editor.putString("province", province);
                                editor.putString("city", city);
                                editor.putString("year", year);
                                editor.putString("month", month);
                                editor.putString("day", day);
                                editor.putString("area", area);
                                editor.commit();
                                //用户头像
                                ImagLoadUtils.setImage(wxHeadimage, user_img, getActivity());


                            } else {
                                Toast.makeText(getActivity(), msg,
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

                        super.onFailure(statusCode, headers, throwable,
                                errorResponse);
                        Toast.makeText(getActivity(), "请检查网络",
                                Toast.LENGTH_SHORT).show();
                        //scrollview刷新数据
                        if (isHAU) {
                            scrollView.onRefreshComplete();
                            isHAU = false;
                        }
                    }

                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //修改个人资料后回调
        if (requestCode == 1010 && resultCode == 1011) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String wxHeadimage = data.getStringExtra("wxHeadimage");
            tv_name.setText(name);
            tv_phone.setText(phone);
            ImagLoadUtils.setImage(wxHeadimage, user_img, getActivity());
        }
        //注销登录状态后回调
        else if (requestCode == 1010 && resultCode == 1008) {
            ll_notlogin.setVisibility(View.VISIBLE);
            //因scrollview内部嵌套了gridview，这样可以让scrollview开始位置在顶部
            mygridview.setFocusable(false);
            scrollView.scrollTo(0, 0);
            ll_user_login.setVisibility(View.GONE);
            ll_user_integral.setVisibility(View.GONE);
        } else if (requestCode == 1985 && resultCode == getActivity().RESULT_OK) {
            ll_notlogin.setVisibility(View.VISIBLE);
            mygridview.setFocusable(false);
            scrollView.scrollTo(0, 0);
            ll_user_login.setVisibility(View.GONE);
            ll_user_integral.setVisibility(View.GONE);
        }
        /****************************************************/
        else if (requestCode == 1698 && resultCode == 1689) {
            String phone = data.getStringExtra("newPhone");
            tv_phone.setText(phone);
        }
    }

    private Dialog payDialog;
    private TextView tv_cancel;
    private TextView tv_confirm;
    //当前消息下标与下条消息下标
    private int currentCount, nextCount;

    //拨打电话弹窗
    private void initDialog(String content, String textLeft, String textRight) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pay, null);
        TextView dialog_content = (TextView) view.findViewById(R.id.tv_dialogNormal_content);

        tv_cancel = (TextView) view.findViewById(R.id.tv_cancelNormal);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirmNormal);
        payDialog = new Dialog(getActivity(), R.style.customDialog);
        payDialog.setContentView(view);
        dialog_content.setText(content);
        tv_cancel.setText(textLeft);
        tv_confirm.setText(textRight);
        Window diawindow = payDialog.getWindow();
        WindowManager m = payDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = payDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);
    }

    private void showDialog() {
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        payDialog.show();
    }

    private Dialog mDialog;

    //系统公告弹窗
    public void MessageDialog(int id) {
        View mview = LayoutInflater.from(getActivity()).inflate(R.layout.message_dialog, null);
        TextView tv_title = (TextView) mview.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) mview.findViewById(R.id.tv_content);
        TextView tv_message_date = (TextView) mview.findViewById(R.id.tv_message_date);
        mDialog = new Dialog(getActivity(), R.style.customDialog);
        mDialog.setContentView(mview);
        mDialog.setCanceledOnTouchOutside(true);
        //显示数据
        tv_title.setText(notifyList.get(id).getTitle());
        tv_content.setText(notifyList.get(id).getContext());
        String date = notifyList.get(id).getEndTime();
        tv_message_date.setText(date.substring(0, 10));
        Window diawindow = mDialog.getWindow();
        diawindow.setWindowAnimations(R.style.dialog_zoom);
        WindowManager m = mDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);
    }

    public void showMessageDialog() {
        mDialog.show();
    }

//    public void dimissMessageDialog() {
//        mDialog.dismiss();
//    }

    private String currentNotify, nextNotify;
    private final int UPDATE = 0;

    private void dimissDialog() {
        payDialog.dismiss();
    }

    //系统循环公告
    public void notifyRunning() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < notifyList.size(); i++) {
                        if (i == notifyList.size() - 1) {
                            currentCount = 0;
                            nextCount = 0;
                            currentNotify = notifyList.get(i).getTitle();
                            nextNotify = notifyList.get(0).getTitle();
                        } else {
                            currentCount = i + 1;
                            nextCount = i + 1;
                            currentNotify = notifyList.get(i).getTitle();
                            nextNotify = notifyList.get(i + 1).getTitle();
                        }
                        try {
                            Thread.sleep(3000);
                            mHandler.sendEmptyMessage(UPDATE);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                }
            }
        }.start();
    }

    private boolean firstShow = true;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    TranslateAnimation outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(MyApplication.getInstance(), R.anim.system_message_up);
                    TranslateAnimation inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(MyApplication.getInstance(), R.anim.system_message_into);
                    //添加动画
                    tv_next_notify.setVisibility(View.VISIBLE);
                    if (firstShow) {

                        firstShow = false;
                        tv_next_notify.setText(nextNotify);
                        tv_current_notify.startAnimation(outAnim);
                        tv_next_notify.startAnimation(inAnim);
                    } else {
                        tv_current_notify.setText(currentNotify);
                        tv_next_notify.setText(nextNotify);
                        tv_current_notify.startAnimation(outAnim);
                        tv_next_notify.startAnimation(inAnim);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
//        //重新进入fragment，请求数据
//        getDataFromWeb();
    }

//    public void updateMineFragment() {
//        getDataFromWeb();
//    }

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myInterFilter = new IntentFilter();
        myInterFilter.addAction(ACTION_NAME);
        getActivity().registerReceiver(myBroadcastReceiver, myInterFilter);
    }

    //定义一个更新数据的广播类
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                //更新数据
                getDataFromWeb();
            }
        }
    };


}
