package com.wss.common.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.ProgressWebView;

import butterknife.BindView;

/**
 * Describe：WevView Activity
 * Created by 吴天强 on 2018/10/19.
 */

public class WebViewActivity extends ActionBarActivity {

    @BindView(R2.id.pw_view)
    ProgressWebView webView;


    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("URL", url);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        webView.loadUrl(getIntent().getStringExtra("URL"));
        webView.setWebViewClient(new MyWebClient());
    }


    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受所有网站的证书
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            setTitleText(view.getTitle());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showFinish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    private void showFinish() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

}
