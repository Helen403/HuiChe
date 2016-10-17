package com.huiche.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseFragment;
import com.huiche.constant.HttpConstant;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.cookie.Cookie;

import java.io.File;
import java.util.List;

/**
 * 积分商城fragment
 *
 * @author Administrator
 */
public class IntegralShopFragment extends BaseFragment {

    private View convertView;
    private ImageButton imb_titleBack;
    private TextView tv_titleText;
    private WebView webView_integralShop;
//    private Context mContext;
//    private LinearLayout ll_content_fragmentIntegral;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_integral_shop,
                container, false);
//        mContext = getActivity();
        return convertView;
    }

    @Override
    public void findViews() {

        imb_titleBack = (ImageButton) convertView.findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) convertView.findViewById(R.id.tv_titleText);
//        ll_content_fragmentIntegral = (LinearLayout) convertView.findViewById(R.id.ll_content_fragmentIntegral);
        webView_integralShop = (WebView) convertView.findViewById(R.id.webView_integralShop);
    }

    @Override
    public void initData() {
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(getActivity());
        //删除之前的缓存
//		clearCacheFolder(getActivity().getCacheDir(), System.currentTimeMillis());
        //优先使用缓存：
        //webView_integralShop.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//不使用缓存：
        webView_integralShop.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        tv_titleText.setText("积分商城");
        imb_titleBack.setVisibility(View.VISIBLE);


        String url = HttpConstant.HTTP_HEAD + "mobile/commodityOnline.html?v=1000";
//        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 获取当前界面的高度

        webView_integralShop.loadUrl(url);
        webView_integralShop.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getActivity());
        List<Cookie> cookies = myCookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            String cookieString = cookies.get(i).getName() + "=" + cookies.get(i).getValue() +
                    ";domain=" + cookies.get(i).getDomain() + ";path=" + cookies.get(i).getPath();
            cookieManager.setCookie(cookies.get(i).getDomain(), cookieString);
        }
        WebSettings settings = webView_integralShop.getSettings();
        //webView_integralShop.getSettings().setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        bufferCircleDialog.show("", true, null);
        webView_integralShop.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                bufferCircleDialog.dialogcancel();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });


    }


    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        imb_titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView_integralShop.canGoBack()) {
                    webView_integralShop.goBack();
                }
            }
        });

    }

    @Override
    public void initViews() {


    }

    /**
     * 监听返回键返回上一个页面
     */
    public boolean goBack() {
        boolean result = false;
        if (webView_integralShop.canGoBack()) {
            //imb_titleBack.setVisibility(View.VISIBLE);
            webView_integralShop.goBack();
//			Log.e("back_url", webView_integralShop.getUrl());
            result = true;
        } else {
            //imb_titleBack.setVisibility(View.GONE);
        }
        return result;
    }

    @Override
    public void onPause() {
        super.onPause();
        webView_integralShop.reload();
    }

    @Override
    public void onStop() {
        super.onStop();
        webView_integralShop.reload();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView_integralShop.reload();

    }

    //再次进来时清楚缓存
    private int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }
}
