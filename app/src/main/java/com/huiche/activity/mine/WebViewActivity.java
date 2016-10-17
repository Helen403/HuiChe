package com.huiche.activity.mine;

import android.content.Intent;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15.
 * 积分抽奖
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    private String url;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_integral_draw);
        webView = (WebView) findViewById(R.id.myWebView);
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        TitleUtils.setInfo(WebViewActivity.this, title);
        //CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setBlockNetworkImage(false);
        //
        PersistentCookieStore myCookieStore = new PersistentCookieStore(WebViewActivity.this);
        List<Cookie> cookies = myCookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            String cookieString = cookies.get(i).getName() + "=" + cookies.get(i).getValue() +
                    ";domain=" + cookies.get(i).getDomain() + ";path=" + cookies.get(i).getPath();
            cookieManager.setCookie(cookies.get(i).getDomain(), cookieString);
        }

    }


    @Override
    public void initData() {
        webView.loadUrl(url);
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(WebViewActivity.this);
        bufferCircleDialog.show("正在加载", true, null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                bufferCircleDialog.dialogcancel();
            }
        });


    }

    @Override
    public void setListeners() {


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
