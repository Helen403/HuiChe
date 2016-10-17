package com.huiche.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.ViewPageAdaper_PhotoAlbum;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class PhotoAlbumActivity extends Activity implements View.OnClickListener {
    private TextView tv_albumCount;
    private ViewPager vp_photoAlbum;
    private TextView tv_photoName;
    private ImageView iv_photoAlbum_download;
    private List<String> urlList = new ArrayList<>();
    private Context mContext;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageView img_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        dealLogicBeforeFindView();
        findViews();
        initData();
        setListeners();
    }

    public void dealLogicBeforeFindView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void findViews() {
        setContentView(R.layout.activity_photo_album);
        tv_albumCount = (TextView) findViewById(R.id.tv_albumCount);
        tv_photoName = (TextView) findViewById(R.id.tv_photoName);
        vp_photoAlbum = (ViewPager) findViewById(R.id.vp_photoAlbum);
        iv_photoAlbum_download = (ImageView) findViewById(R.id.iv_photoAlbum_download);
        img_finish = (ImageView) findViewById(R.id.img_finish);
    }

    public void initData() {
        urlList = getIntent().getStringArrayListExtra("img_list");
        if (urlList.size() > 0) {
            initImageLoader();
            initViewPager();
            tv_albumCount.setText("1/" + urlList.size());
        }
    }

    private void initViewPager() {
        ViewPageAdaper_PhotoAlbum adapter = new ViewPageAdaper_PhotoAlbum(mContext, urlList, imageLoader, options);
        vp_photoAlbum.setAdapter(adapter);
    }

    public void setListeners() {
        iv_photoAlbum_download.setOnClickListener(this);
        vp_photoAlbum.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_albumCount.setText(position + 1 + "/" + urlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        img_finish.setOnClickListener(this);
        //单击图片退出页面

        vp_photoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        imageLoader = ImageLoader.getInstance();
        options =
                new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.shangj_beij11)
                                // 加载中要显示图片
                        .showImageForEmptyUri(R.drawable.shangj_beij11)
                                // url为空要显示图片
                        .showImageOnFail(R.drawable.shangj_beij11)

                        .cacheInMemory(true)
                                // 加载失败要显示图片
                        .cacheOnDisk(true).considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .displayer(new FadeInBitmapDisplayer(388)).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photoAlbum_download:
                //下载图片
                downloadPicture();
                break;
            case R.id.img_finish:
                finish();
                break;
        }
    }

    private void downloadPicture() {
        String url = urlList.get(vp_photoAlbum.getCurrentItem());
//        Log.i("ss", url);
        AsyncHttpClient client = new AsyncHttpClient();
        // 指定文件类型
        String[] allowedContentTypes = new String[]{".*"};
//        String[] allowedContentTypes = new String[]{"image/png", "image/jpeg"};
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", false, null);
        client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                dialog.dialogcancel();
//                Log.i("文件大小", bytes.length + "");
                //保存图片的文件夹路径
                File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "YouJiFen");
                if (!appDir.exists())//文件夹不存在则创建
                    appDir.mkdirs();
//                String path = Environment.getExternalStorageDirectory().getPath() +"/"+ System.currentTimeMillis() + ".jpg";
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                //使用BitmapFactory创建图片
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //压缩格式
                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                //压缩质量/比例
                int quality = 100;
                if (file.exists())
                    file.delete();
                try {
                    file.createNewFile();
                    OutputStream stream = new FileOutputStream(file);
                    //输出\写入
                    bitmap.compress(format, quality, stream);
                    stream.close();
                    //通知广播在系统相册显示图片
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    sendBroadcast(intent);
                    ToastUtils.ToastShowShort(mContext, "下载成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                dialog.dialogcancel();
                ToastUtils.ToastShowShort(mContext, "下载失败");
            }
        });
    }
}
