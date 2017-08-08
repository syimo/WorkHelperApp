package com.king.applib.base.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.king.applib.log.Logger;
import com.king.applib.util.ExtendUtil;

/**
 * @author huoguangxu
 * @since 2017/4/14.
 */

public class BaseWebViewClient extends WebViewClient {
    public static final String PREFIX_JS_PROTOCOL = "jsbridge://";
    private final Context mContext;

    public BaseWebViewClient(Context context) {
        mContext = context;
    }

    //js2java 1. 注意：单独打开一个网页不执行该方法，只有点击该网页内的链接时才执行.
    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.i("shouldOverrideUrlLoading(<LOLLIPOP).url: " + url);
        if (url.contains(PREFIX_JS_PROTOCOL)) {
            return true;
        }
        if (ExtendUtil.isTelUri(url)) {//处理"tel:"协议
            ExtendUtil.makeCall(mContext, url);
            return true;
        }
        if (url.startsWith("https:") || url.startsWith("http:")) {//只处理基本的http请求
            view.loadUrl(url);
            return true;
        } else {//其它自定义协议(bdvideo: baiduboxapp: tmast:)一般是下载交给系统处理
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Logger.i("shouldOverrideUrlLoading(>=LOLLIPOP)");
        return true;
    }

    //回调不准确
    @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Logger.i("finished url: " + url);
//        mWebProgress.setVisibility(View.GONE);
    }

    @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Logger.i("started url: " + url);
//        mWebProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse response = null;
        /*if (url.endsWith("123")) {
            try {
                InputStream inputStream = getAssets().open("aaa.png");
                response = new WebResourceResponse("image/png", "UTF-8", inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return response;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Logger.e("onReceivedError(>=Build.VERSION_CODES.M)");
//        mSimpleWebView.showErrorView2();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Logger.e("onReceivedError. " + "errorCode: " + errorCode + ";description: " + description + ";failingUrl: " + failingUrl);
        //用javascript隐藏系统定义的404页面信息
//        String data = "Page NO FOUND！";
//        view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}
