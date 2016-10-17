package com.huiche.utils;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public final class CookieUtil {

    private static List<Cookie> cookies;

    public static List<Cookie> getCookies() {
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    public static void setCookies(List<Cookie> cookies) {
        CookieUtil.cookies = cookies;
    }
    /**
     * 获取标准 Cookie
     */
    public static String getCookieText(Context context) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = myCookieStore.getCookies();
//        Log.d("cookies.size() =",cookies.size()+"");
        CookieUtil.setCookies(cookies);
        for (Cookie cookie : cookies) {
//            Log.d("cookie", cookie.getName() + " = " + cookie.getValue());
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
//        Log.d("cookie", sb.toString());
        return sb.toString();
    }


}
