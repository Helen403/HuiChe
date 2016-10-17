package com.huiche.utils;

import android.content.Context;

import com.huiche.base.MyApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.HttpEntity;

/**
 * @author
 * @version 类作用：网络连接类
 */

public final class AsyncHttp {
    private static AsyncHttpClient client = new AsyncHttpClient(); //

    static {

        client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
    }

    // 用一个完整url获取string对象
    public static void get(String urlString, AsyncHttpResponseHandler res) {

        client.get(urlString, res);

    }

    // 用一个完整url获取string对象
    public static void post(String urlString, AsyncHttpResponseHandler res) {

        client.post(urlString, res);

    }


    // url里面带参数
    public static void get(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {

        client.get(urlString, params, res);

    }

    // url里面带参数
    public static void post(String urlString, RequestParams params,
                            AsyncHttpResponseHandler res) {
//		params.put("token",MyApplication.token);
        client.post(urlString, params, res);
    }

    // url里面带参数
    public static void posts(String urlString, RequestParams params,
                             AsyncHttpResponseHandler res) {
        client.post(urlString, params, res);
    }

    // 不带参数，获取json对象或jsonarray
    public static void get(String urlString, JsonHttpResponseHandler res) {
        client.get(urlString, res);

    }

    // 不带参数，获取json对象或jsonarray
    public static void post(String urlString, JsonHttpResponseHandler res) {
        client.post(urlString, res);

    }

    // 不带参数，获取json对象或jsonarray
    public static void posts(String urlString, JsonHttpResponseHandler res) {
        client.post(urlString, res);
    }

    // 不带参数，获取json对象或jsonarray
    public static void get(String urlString, RequestParams params,
                           JsonHttpResponseHandler res) {
        client.get(urlString, params, res);

    }

    // 带参数，获取json对象或json array
    public static void post(String urlString, RequestParams params,
                            JsonHttpResponseHandler res) {
        params.put("token", MyApplication.token + "");
        client.post(urlString, params, res);


    }

    public static void posts(String urlString, RequestParams params,
                             JsonHttpResponseHandler res) {
        client.post(urlString, params, res);
    }

    // 下载数据使用，会返回byte数据
    public static void get(String uString, BinaryHttpResponseHandler bHandler) {

        client.get(uString, bHandler);


    }

    //防止乱码post
    public static void postAll(Context mcontext, String uString, HttpEntity httpEntity, String contentType, ResponseHandlerInterface res) {
        client.post(mcontext, uString, httpEntity, contentType, res);

    }

    public static AsyncHttpClient getClient() {
        return client;
    }
}
