package com.huiche.fragment;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.ApplyManActivity;
import com.huiche.activity.CallPhoneActivity;
import com.huiche.activity.CarManagerFisterActivity;
import com.huiche.activity.FeedBackActivity;
import com.huiche.activity.GoodsReceiptActivity;
import com.huiche.activity.LoginActivity;
import com.huiche.activity.MineActivity;
import com.huiche.activity.MyCollectionsActivity;
import com.huiche.activity.MyCommentActivity;
import com.huiche.activity.MyPartnerActivity;
import com.huiche.activity.MyjiFenActivity;
import com.huiche.activity.OilcardActivity;
import com.huiche.activity.SecurityHelenActivity;
import com.huiche.adapter.MyPersionalAdapter;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.base.BaseApplication;
import com.huiche.lib.lib.base.BaseFragment;
import com.huiche.lib.lib.custemview.CircleImageView;
import com.huiche.lib.lib.custemview.MyGridView;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MineFragment extends BaseFragment {

    private RelativeLayout relaTop;
    private ImageButton imbTitleBarBack;
    private TextView tvTitleBarName;
    private ImageButton btnMessage;
    private LinearLayout llNotlogin;
    private LinearLayout llUserIntegral;
    private LinearLayout llIntegralSurplus;
    private TextView tvIntegral;
    private LinearLayout llIntegralIncome;
    private TextView tvIncome;
    private LinearLayout llIntegralUsed;
    private TextView tvOutcome;
    private TextView tv_3;
    CircleImageView icon;
    MyGridView mygridview;

    MyPersionalAdapter myPersionalAdapter;
    private String[] strType = new String[]{"我的订单", "我的评价", "车辆管理", "联系客服", "安全设置", "收获地址", "意见反馈", "我的油卡", "申请合伙人"};
    private int[] imgData = new int[]{R.drawable.the_online_order, R.drawable.hongbao_xsm, R.drawable.my_card_volume, R.drawable.yellow_book
            , R.drawable.my_collection, R.drawable.my_evaluation, R.drawable.security_settings, R.drawable.feedback, R.drawable.contact_customerservice};

    @Override
    public int getContentView() {
        return R.layout.fragment_minehelen;
    }

    @Override
    public void findViews() {
        relaTop = (RelativeLayout) contentView.findViewById(R.id.rela_top);
        imbTitleBarBack = (ImageButton) contentView.findViewById(R.id.imb_title_bar_back);
        tvTitleBarName = (TextView) contentView.findViewById(R.id.tv_title_bar_name);
        btnMessage = (ImageButton) contentView.findViewById(R.id.btn_message);
        llNotlogin = (LinearLayout) contentView.findViewById(R.id.ll_notlogin);
        llUserIntegral = (LinearLayout) contentView.findViewById(R.id.ll_user_integral);
        llIntegralSurplus = (LinearLayout) contentView.findViewById(R.id.ll_integral_surplus);
        tvIntegral = (TextView) contentView.findViewById(R.id.tv_integral);
        llIntegralIncome = (LinearLayout) contentView.findViewById(R.id.ll_integral_income);
        tvIncome = (TextView) contentView.findViewById(R.id.tv_income);
        llIntegralUsed = (LinearLayout) contentView.findViewById(R.id.ll_integral_used);
        tvOutcome = (TextView) contentView.findViewById(R.id.tv_outcome);
        icon = (CircleImageView) contentView.findViewById(R.id.icon);
        mygridview = (MyGridView) contentView.findViewById(R.id.mygridview);
        tv_3 = (TextView) contentView.findViewById(R.id.tv_3);

    }

    @Override
    public void initData() {

        //设置下面的gridview
        setGridView();
    }

    private void setGridView() {
        //重新进入fragment的时候焦点在顶部
        mygridview.setFocusable(false);
        myPersionalAdapter = new MyPersionalAdapter(getActivity(), strType, imgData);
        mygridview.setAdapter(myPersionalAdapter);
    }

    @Override
    public void setListeners() {
        setOnListeners(llNotlogin, icon, llIntegralSurplus, llIntegralIncome, llIntegralUsed);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //点击登录
                    case R.id.ll_notlogin:
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, Constants.startActivityForResult.LOGIN);
                        break;

                    //点击头像  有可能注销当前帐号
                    case R.id.icon:
                        Intent intent1 = new Intent(getActivity(), MineActivity.class);
                        startActivityForResult(intent1, Constants.startActivityForResult.CANCELLATION);
                        break;

                    //我的积分
                    case R.id.ll_integral_surplus:
                        Intent intent2 = new Intent(getActivity(), MyjiFenActivity.class);
                        startActivity(intent2);
                        break;
                    //我的卡卷
                    case R.id.ll_integral_income:
                        Intent intent3 = new Intent(getActivity(), MyjiFenActivity.class);
                        startActivity(intent3);
                        break;
                    //我的收藏
                    case R.id.ll_integral_used:
                        Intent intent4 = new Intent(getActivity(), MyCollectionsActivity.class);
                        startActivity(intent4);
                        break;
                }
            }
        });

        /*********************************************************/
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();

                switch (position) {
                    //线上订单
                    case 0:
                        intent.setClass(getActivity(), MyCollectionsActivity.class);
                        startActivity(intent);
                        break;
                    //我的评论
                    case 1:
                        intent.setClass(getActivity(), MyCommentActivity.class);
                        startActivity(intent);
                        break;

                    //车辆管理
                    case 2:
                        intent.setClass(getActivity(), CarManagerFisterActivity.class);
                        startActivity(intent);
                        break;

                    //联系客服
                    case 3:
                        //获取客服电话
                        intent.setClass(getActivity(), CallPhoneActivity.class);
                        startActivity(intent);
                        break;
                    //安全设置
                    case 4:
                        intent.setClass(getActivity(), SecurityHelenActivity.class);
                        startActivity(intent);
                        break;
                    //收获地址
                    case 5:
                        intent.setClass(getActivity(), GoodsReceiptActivity.class);
                        startActivity(intent);
                        break;
                    //意见反馈
                    case 6:
                        intent.setClass(getActivity(), FeedBackActivity.class);
                        startActivity(intent);
                        break;
                    //我的油卡
                    case 7:
                        intent.setClass(getActivity(), OilcardActivity.class);
                        startActivity(intent);
                        break;
                    //申请合伙人
                    case 8:
                        applyMan();
                        break;
                }
            }
        });

    }

    //申请合伙人
    private void applyMan() {
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        //通过
        if ("1".equals(BaseApplication.loginResultBean.data.partner_state)) {
            //我的合伙人
            Intent intent = new Intent(getActivity(), MyPartnerActivity.class);
            startActivity(intent);

        } else {
            //申请合伙人
            Intent intent = new Intent(getActivity(), ApplyManActivity.class);
            startActivity(intent);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //去登录
        if (resultCode == Constants.startActivityForResult.LOGINRESULT && requestCode == Constants.startActivityForResult.LOGIN) {
            //登录成功后设置个人信息
            //设置头像
            setImageByUrl(BaseApplication.loginResultBean.data.headerimg, icon);
            //设置名称
            if (TextUtils.isEmpty(BaseApplication.loginResultBean.data.username)) {
                tv_3.setText(BaseApplication.phone);
            } else {
                tv_3.setText(BaseApplication.loginResultBean.data.username);
            }

            //设置积分
            tvIntegral.setText(BaseApplication.loginResultBean.data.points + "");
            //设置卡卷
            tvIncome.setText(BaseApplication.loginResultBean.data.vol + "");
            //设置收藏
            tvOutcome.setText(BaseApplication.loginResultBean.data.collect + "");
            if ("1".equals(BaseApplication.loginResultBean.data.partner_state)) {
                strType[8] = "我的合伙人";
                myPersionalAdapter.notifyDataSetChanged();
            }

        }

        //退出当前帐号
        if (resultCode == Constants.startActivityForResult.CANCELLATIONRESULT && requestCode == Constants.startActivityForResult.CANCELLATION) {
            icon.setImageResource(R.drawable.ic_launcher);
            //设置名称
            tv_3.setText("登录/注册");
            //设置积分
            tvIntegral.setText(0 + "");
            //设置卡卷
            tvIncome.setText(0 + "");
            //设置收藏
            tvOutcome.setText(0 + "");
            strType[8] = "申请合伙人";
            myPersionalAdapter.notifyDataSetChanged();
        }


    }

}
