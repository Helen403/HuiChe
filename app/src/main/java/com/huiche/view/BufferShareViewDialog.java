package com.huiche.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.StoreInfo;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * @author Administrator
 *         卡卷领取提示
 */
public class BufferShareViewDialog extends RelativeLayout {
    public Context context;
    View view;
    String businessName;
    String storeDesc;
    String businessStoreImages;
    String url;
    String businessStoreId;

    public BufferShareViewDialog(Context context) {
        super(context);
        this.context = context;
    }


    /**
     * 弹出自定义ProgressDialog
     * <p/>
     * <p/>
     * 上下文
     */
    public void show() {

        view = View.inflate(getContext(), R.layout.dialog_progress_custom_show_share, this);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_share);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });

        TextView sina = (TextView) view.findViewById(R.id.tv_sina);
        TextView wechat = (TextView) view.findViewById(R.id.tv_wechat);
        TextView qq = (TextView) view.findViewById(R.id.tv_qq);
        sina.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//                sp.setText("测试分享的文本");
                //                sp.setImagePath(“/mnt/sdcard/测试分享的图片.jpg”);

//                sp.setTitle(businessName);
//                sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
//                sp.setText(storeDesc);
//                sp.setImageUrl(businessStoreImages);
//                sp.setSite("发布分享的网站名称");
//                sp.setSiteUrl(url);


              //  Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                weibo.setPlatformActionListener(paListener); // 设置分享事件回调
                // 执行图文分享
               // weibo.share(sp);
                view.setVisibility(GONE);
            }
        });
        wechat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                OnekeyShare oks = new OnekeyShare();
//
//                oks.setPlatform("Wechat");
//                // ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC 第二个是SKYBLUE
//                oks.setTheme(OnekeyShareTheme.CLASSIC);
//                // 令编辑页面显示为Dialog模式
//                oks.setDialogMode();
//                // 在自动授权时可以禁用SSO方式
//                oks.disableSSOWhenAuthorize();
//                // oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
//                oks.setTitle("智能生活4.0");
//                oks.setTitleUrl("http://51bangbang.com.cn");// QQ分享
//                oks.setText("无忧帮帮，生活更简单。http://51bangbang.com.cn");
//                // oks.setImagePath(MyConfig.FILEDIR + "/icon.png"); // 分享sdcard目录下的图片
//                oks.setImageUrl("http://file.bmob.cn/M02/ED/63/oYYBAFZqxbKAdyBnAADe8t1LkxI683.jpg ");
//                oks.setVenueName("无忧帮帮");
//                oks.setVenueDescription("This is a beautiful place!");
//
//
//                oks.setUrl("http://51bangbang.com.cn"); // 微信不绕过审核分享链接
//
//                Bitmap enableLogo = BitmapFactory.decodeResource(
//                        context.getResources(), R.drawable.ic_launcher);
//                Bitmap disableLogo = BitmapFactory.decodeResource(
//                        context.getResources(), R.drawable.ic_launcher);
//                String label = "ShareSDK";
//                OnClickListener listener = new OnClickListener() {
//                    public void onClick(View v) {
//
//                    }
//                };
//                oks.setCustomerLogo(enableLogo, disableLogo, label, listener);
//
//                // 为EditPage设置一个背景的View
//                // oks.setEditPageBackground(getPage());
//                // 隐藏九宫格中的新浪微博
//                // oks.addHiddenPlatform(SinaWeibo.NAME);
//
//                // String[] AVATARS = {
//                // "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
//                // "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
//                // "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
//                // "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
//                // "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
//                // "h
//
//                oks.show(context);


//                WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
//                sp.setTitle("测试分享的标题");
//                sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
//                sp.setText("测试分享的文本");
//                sp.setImageUrl("http://www.someserver.com/测试图片网络地址.jpg");
//                sp.setSite("发布分享的网站名称");
//                sp.setSiteUrl("发布分享网站的地址");
//
//                Platform qzone = ShareSDK.getPlatform(WechatMoments.NAME);
////                qzone. setPlatformActionListener (paListener); // 设置分享事件回调
//                // 执行图文分享
//                qzone.share(sp);

            }
        });
        qq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                QQ.ShareParams sp = new QQ.ShareParams();
                sp.setTitle(businessName);
//                sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                sp.setText(storeDesc);
                sp.setImageUrl(businessStoreImages);
//                sp.setSite("发布分享的网站名称");
                sp.setSiteUrl(url);

                Platform qzone = ShareSDK.getPlatform(QQ.NAME);
//                qzone. setPlatformActionListener (paListener); // 设置分享事件回调
                // 执行图文分享
                qzone.share(sp);
                view.setVisibility(GONE);
            }
        });


    }

    public void setData(String businessStoreId, StoreInfo storeInfo) {
        this.businessStoreId = businessStoreId;
        businessName = storeInfo.businessName;
        storeDesc = storeInfo.storeDesc;
        businessStoreImages = storeInfo.getBusinessStoreImages().get(0);
        String tmp = "https://test.51ujf.cn/mobile/commodityDetail.html?id=%s&productId=%s";
        url = String.format(tmp, businessStoreId, storeInfo.id);

    }


}
