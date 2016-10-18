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
import com.huiche.activity.LoginActivity;
import com.huiche.activity.mine.ApplyManActivity;
import com.huiche.activity.mine.CallPhoneActivity;
import com.huiche.activity.mine.CarManagerFisterActivity;
import com.huiche.activity.mine.FeedBackActivity;
import com.huiche.activity.mine.GoodsReceiptActivity;
import com.huiche.activity.mine.MineActivity;
import com.huiche.activity.mine.MyCollectionsActivity;
import com.huiche.activity.mine.MyCommentActivity;
import com.huiche.activity.mine.MyPartnerActivity;
import com.huiche.activity.mine.MyjiFenActivity;
import com.huiche.activity.mine.OilcardActivity;
import com.huiche.activity.mine.SecurityHelenActivity;
import com.huiche.adapter.MyPersionalAdapter;
import com.huiche.base.MyApplication;
import com.huiche.constant.Constants;
import com.huiche.customer_view.MyGridView;
import com.huiche.lib.lib.base.BaseFragment;
import com.huiche.view.CircleImageView;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MineHelenFragment extends BaseFragment {

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
        mygridview = (com.huiche.customer_view.MyGridView) contentView.findViewById(R.id.mygridview);
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

                    //点击头像
                    case R.id.icon:
                        Intent intent1 = new Intent(getActivity(), MineActivity.class);
                        startActivity(intent1);
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
                    case 8:
                        applyMan();
                        break;
                }
            }
        });

    }

    //申请合伙人
    private void applyMan() {
        if (MyApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        //通过
        if ("1".equals(MyApplication.loginResultBean.data.partner_state)){
            //我的合伙人
            Intent intent = new Intent(getActivity(), MyPartnerActivity.class);
            startActivity(intent);

        }else {
            //申请合伙人
            Intent intent = new Intent(getActivity(), ApplyManActivity.class);
            startActivity(intent);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.startActivityForResult.LOGINRESULT && requestCode == Constants.startActivityForResult.LOGIN) {
            //登录成功后设置个人信息
            //设置头像
            setImageByUrl(MyApplication.loginResultBean.data.headerimg, icon);
            //设置名称

            if (TextUtils.isEmpty(MyApplication.loginResultBean.data.username)) {
                tv_3.setText(MyApplication.phone);
            } else {
                tv_3.setText(MyApplication.loginResultBean.data.username);
            }

            //设置积分
            tvIntegral.setText(MyApplication.loginResultBean.data.points + "");
            //设置卡卷
            tvIncome.setText(MyApplication.loginResultBean.data.vol + "");
            //设置收藏
            tvOutcome.setText(MyApplication.loginResultBean.data.collect + "");
        }
    }

}
