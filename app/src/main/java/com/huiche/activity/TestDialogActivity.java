package com.huiche.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.WindowUtils;
import com.huiche.view.CircleImageView;
import com.huiche.view.GradientTextView;


/**
 * 测试对话框
 *
 * @author Administrator
 */
public class TestDialogActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private ImageButton imb_titleBack;
    private TextView tv_titleText;
    private TextView tv_redPack;
    private TextView tv_cardStock;
    private Builder builder;
    private WindowUtils windowUtil;
    private AlertDialog redPacketDialog;
    private ImageView iv_open_redPacket;
    private LinearLayout ll_noOpen_redpacket;
    private LinearLayout ll_hasOpen_redpacket;
    private TextView tv_check_IntegralBalance;
    private TextView tv_normal_dialog;
    private TextView tv_cancelNormal;
    private TextView tv_confirmNormal;
    private TextView tv_redPacket_advertise;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_test_dialog);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        tv_redPack = (TextView) findViewById(R.id.tv_redPack);
        tv_cardStock = (TextView) findViewById(R.id.tv_cardStock);
        tv_normal_dialog = (TextView) findViewById(R.id.tv_normal_dialog);
        tv_redPacket_advertise = (TextView) findViewById(R.id.tv_redPacket_advertise);
    }

    @Override
    public void initData() {
        builder = new Builder(mContext);
        windowUtil = new WindowUtils(mContext);
        tv_titleText.setText("测试对话框");
    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
        tv_redPack.setOnClickListener(this);
        tv_cardStock.setOnClickListener(this);
        tv_normal_dialog.setOnClickListener(this);
        tv_redPacket_advertise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb_titleBack:
                finish();

                break;
            case R.id.tv_redPack:
                showRedPacketDialog();

                break;
            case R.id.tv_cardStock:
                showCardStockDialog();
                break;
            case R.id.tv_normal_dialog:
                showNormalDialog();
                break;
            case R.id.tv_redPacket_advertise:
                showRedPacketAdvertiseDialog();
                break;

            default:
                break;
        }

    }

    /**
     * 红包广告对话框
     */
    private void showRedPacketAdvertiseDialog() {
        Builder builder = new Builder(mContext);
        View dialogView = View.inflate(mContext,
                R.layout.dialog_red_packet_advertise, null);
        AlertDialog redPacketAdvertiseDialog = builder.show();
        // width为屏幕宽度5/6 width:height=7:10
        int displayWidth = windowUtil.getdisplayWidth();
        Window dialogWindow = redPacketAdvertiseDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = displayWidth * 5 / 6;
        lp.height = lp.width * 10 / 7;
        dialogWindow.setAttributes(lp);
        dialogWindow.setContentView(dialogView);
        // findView
        ImageView iv_redPacket_advertisePhoto = (ImageView) dialogView
                .findViewById(R.id.iv_redPacket_advertisePhoto);
        CircleImageView civ_redPacket_advertiseIcon = (CircleImageView) dialogView
                .findViewById(R.id.civ_redPacket_advertiseIcon);
        //展示图比例为屏幕宽度5/7 width:height=16:9,圆形头像icon为1/9
        int photoWidth = displayWidth * 5 / 7;
        int phontHeight = photoWidth * 9 / 16;
        iv_redPacket_advertisePhoto.setLayoutParams(new LinearLayout.LayoutParams(photoWidth, phontHeight));
        civ_redPacket_advertiseIcon.setLayoutParams(new LinearLayout.LayoutParams(displayWidth / 9, displayWidth / 9));

    }

    /**
     * 普通提示框
     */
    private void showNormalDialog() {
        Builder builder = new Builder(mContext);
        View dialogView = View.inflate(mContext, R.layout.dialog_normal, null);
        AlertDialog normalDialog = builder.show();
        // width为屏幕宽度5/6,height为5/12,width/2
        int width = windowUtil.getdisplayWidth();
        Window dialogWindow = normalDialog.getWindow();
        dialogWindow.setContentView(dialogView);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = width * 5 / 6;
        lp.height = width * 5 / 12;
        dialogWindow.setAttributes(lp);
        // find控件设置监听
        tv_cancelNormal = (TextView) dialogView
                .findViewById(R.id.tv_cancelNormal);
        tv_confirmNormal = (TextView) dialogView
                .findViewById(R.id.tv_confirmNormal);
        tv_cancelNormal.setOnClickListener(this);
        tv_confirmNormal.setOnClickListener(this);

    }

    /**
     * 弹出卡巻对话框,包括抵扣劵和通用劵的布局，请自行修改背景、文字颜色和visiable
     */
    private void showCardStockDialog() {
        Builder builder = new Builder(mContext);
        AlertDialog dialog = builder.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        int displayWidth = windowUtil.getdisplayWidth();
        // 屏幕宽度的5/6;dialog width:height=6:5
        int dialogWidth = displayWidth * 5 / 6;
        lp.width = dialogWidth;
        lp.height = dialogWidth * 5 / 6;
        dialogWindow.setAttributes(lp);
        View dialogContentView = View.inflate(mContext,
                R.layout.dialog_card_stock, null);
        dialogWindow.setContentView(dialogContentView);
        // find view
        CircleImageView storeIcon = (CircleImageView) dialogContentView
                .findViewById(R.id.civ_cardStock_icon);
        GradientTextView cardStockType = (GradientTextView) dialogContentView
                .findViewById(R.id.tv_cardStock_type);
        LinearLayout ll_cardStock_layout = (LinearLayout) dialogContentView
                .findViewById(R.id.ll_cardStock_layout);
        // cardStockType width为displayWidth/5,width:height=6:5;
        cardStockType.setLayoutParams(new RelativeLayout.LayoutParams(
                displayWidth / 5, dialogWidth / 6));
        // 距离底部7/16height
        cardStockType.setPadding(0, 0, 0, (dialogWidth / 6) * 7 / 16);
        storeIcon.setLayoutParams(new LinearLayout.LayoutParams(
                displayWidth / 7, dialogWidth / 7));
        // 设置数据
        storeIcon.setImageResource(R.drawable.user_comments_head);
        ll_cardStock_layout.setBackgroundResource(R.drawable.pink_youhui);

    }

    /**
     * 弹出红包对话框,包括拆前和拆后的布局，请自行修改
     */
    private void showRedPacketDialog() {
        if (redPacketDialog == null) {
            // 弹出删除对话框
            View dialogView = View.inflate(mContext,
                    R.layout.dialog_red_packet, null);
            redPacketDialog = builder.show();
            // width为屏幕宽度5/6
            int width = windowUtil.getdisplayWidth();
            Window dialogWindow = redPacketDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = width * 5 / 6;
            lp.height = width;
            dialogWindow.setAttributes(lp);
            dialogWindow.setContentView(dialogView);
            // find控件设置监听
            ll_noOpen_redpacket = (LinearLayout) dialogView
                    .findViewById(R.id.ll_noOpen_redpacket);
            ll_hasOpen_redpacket = (LinearLayout) dialogView
                    .findViewById(R.id.ll_hasOpen_redpacket);
            iv_open_redPacket = (ImageView) dialogView
                    .findViewById(R.id.iv_open_redPacket);
            tv_check_IntegralBalance = (TextView) dialogView
                    .findViewById(R.id.tv_check_IntegralBalance);
            iv_open_redPacket.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_noOpen_redpacket.setVisibility(View.INVISIBLE);
                    ll_hasOpen_redpacket.setVisibility(View.VISIBLE);
                    iv_open_redPacket.setVisibility(View.INVISIBLE);
                    tv_check_IntegralBalance.setVisibility(View.VISIBLE);
                }
            });

        } else {
            redPacketDialog.show();
        }

    }

}
