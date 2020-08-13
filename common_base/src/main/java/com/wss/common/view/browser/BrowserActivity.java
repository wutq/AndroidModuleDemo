package com.wss.common.view.browser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wss.common.base.BaseMvpActivity;
import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.constants.Constants;
import com.wss.common.constants.Dic;
import com.wss.common.utils.NetworkUtil;
import com.wss.common.utils.ValidUtils;
import com.wss.common.view.browser.mvp.BrowserPresenter;
import com.wss.common.view.browser.mvp.contract.BrowserContract;
import com.wss.common.widget.dialog.AppDialog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：浏览器页面-WebView
 * Created by 吴天强 on 2020/5/18.
 */
public class BrowserActivity extends BaseMvpActivity<BrowserPresenter> implements BrowserContract.View {

    @BindView(R2.id.tv_title)
    TextView tvTitle;

    @BindView(R2.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R2.id.web_view)
    WebView webView;

    @BindView(R2.id.iv_empty)
    ImageView ivEmpty;

    @BindView(R2.id.tv_empty)
    TextView tvEmpty;

    @BindView(R2.id.ll_empty_view)
    LinearLayout llEmptyView;

    @BindView(R2.id.img_back)
    ImageView ivBack;
    @BindView(R2.id.tv_back)

    TextView tvBack;
    @BindView(R2.id.tv_close_right)
    TextView tvCloseRight;

    @BindView(R2.id.rl_browser_header)
    RelativeLayout relHeader;
    /**
     * 本地缓存目录
     */
    private String appCacheDir = Constants.WebView.CACHE_DIR;
    private boolean isFullScreen = false;
    private String url;
    private String title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser;
    }

    public void hiddenHeader() {
        relHeader.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        //沉浸式状态栏
        setImmersionBarColor(R.color.white);
        url = getIntent().getStringExtra(Dic.URL);
        title = getIntent().getStringExtra(Dic.TITLE_TEXT);
        tvCloseRight.setVisibility(View.GONE);
        tvBack.setVisibility(View.GONE);
        tvTitle.setText(title);
        initWebView();
        refreshPage();
    }

    @OnClick({
            R2.id.img_back,
            R2.id.tv_back,
            R2.id.tv_close_right,
    })
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            finish();
        } else if (id == R.id.tv_back) {
            finish();
        } else if (id == R.id.tv_close_right) {
            finish();
        }
    }

    /**
     * 获取一个WebView实例
     *
     * @return
     */
    public WebView getWebView() {
        return webView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setDatabasePath(appCacheDir);
        webSettings.setAppCacheMaxSize(100 * 1024 * 1024);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        // 添加新参数
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        setPageCacheCapacity(webSettings);
        webSettings.enableSmoothTransition();
        // 是否显示缩放按钮
        webSettings.setDisplayZoomControls(false);

        //设置HTTP HTTPS 混合模式加载页面
        webSettings.setMixedContentMode(2);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(chromeClient);
    }


    private class JavaScriptApi {

    }

    @Override
    protected BrowserPresenter createPresenter() {
        return new BrowserPresenter();
    }


    /**
     * WebView调用JS
     *
     * @param method 方法名
     * @param data   参数多参已逗号分隔
     */
    private void loadJsMethod(String method, String data) {
        if (ValidUtils.isValid(data)) {
            // 传递参数调用
            String format = String.format("javascript:%s('%s')", method, data);
            webView.loadUrl(format);
        } else {
            // 无参数调用
            webView.loadUrl(String.format("javascript:%s()", method));
        }
    }


    /**
     * 刷新页面 显示网页
     */
    private void refreshPage() {
        webView.clearCache(true);
        if (NetworkUtil.isNetworkEnabled(context)) {
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            webView.loadUrl(url);
//            webView.loadUrl("file:///android_asset/demo.html");
        } else {
            shoeErrorPage(true);
        }
    }

    /**
     * WebView client
     */
    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }

            Logger.e("shouldOverrideUrlLoading: " + url);
            //拦截tel:拨打电话。
            if (url.startsWith("tel:")) {
                try {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().startsWith("http:") || request.getUrl().toString().startsWith("https:")) {
                return false;
            }
            return true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Logger.e("onPageStarted: " + url);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logger.e("onPageFinished: " + url);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    };
    /**
     * 显示到选择进度条的进度，根据返回的title获取到当前的标题
     * 如果传过来的参数中带有标题,则显示该标题，不再根据WebChromeClient返回的title获取到当前的标题
     */
    private WebChromeClient chromeClient = new WebChromeClient() {
        private View myView = null;
        private int mTempProgress = 0;

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, @NotNull JsResult result) {
            showDialog(message);
            result.confirm();
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // 进度加载到100时，就不再重新进行加载，防止页面嵌套页面时，进度条反复加载
            if (progressBar == null || mTempProgress == 100) {
                return;
            }
            mTempProgress = newProgress > 50 ? 100 : newProgress;
            if (progressBar.getVisibility() == View.GONE) {
                showProgressView();
            }
            progressBar.setProgress(mTempProgress);
            if (newProgress >= 80 && progressBar.getVisibility() == View.VISIBLE) {
                dismissProgressView();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (tvTitle != null && !ValidUtils.isValid(title)) {
                tvTitle.setText(title);
            }
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            isFullScreen = true;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ViewGroup parent = (ViewGroup) webView.getParent().getParent();
            parent.setVisibility(View.GONE);
            ((ViewGroup) parent.getParent()).addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER));
            myView = view;
            chromeClient = this;
        }

        @Override
        public void onHideCustomView() {
            isFullScreen = false;
            if (myView != null) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                if (myView != null && myView.getParent() != null) {
                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeView(myView);

                    if (webView.getParent().getParent() != null) {
                        ViewGroup parent2 = (ViewGroup) webView.getParent().getParent();
                        parent2.setVisibility(View.VISIBLE);
                    }
                }
                myView = null;
            }
        }
    };

    @OnClick({R2.id.img_back,
            R2.id.tv_back,
            R2.id.tv_close_right,
            R2.id.ll_empty_view})
    public void onViewClicked(@NotNull View view) {
        int id = view.getId();
        if (id == R.id.img_back || id == R.id.tv_back) {
            //返回
            goBack();
        } else if (id == R.id.tv_close_right) {
            //关闭
            finish();
        } else if (id == R.id.ll_empty_view) {
            //点击错误页
        }
    }

    /**
     * 显示进度条
     */
    private void showProgressView() {
        AlphaAnimation anim = new AlphaAnimation(0, 1.0f);
        anim.setDuration(200);
        progressBar.startAnimation(anim);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏进度条
     */
    protected void dismissProgressView() {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0);
        anim.setDuration(200);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画结束后再隐藏进度条
            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.GONE);
            }
        });
        progressBar.startAnimation(anim);
        // 动画结束前显示进度条
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 显示错误页面
     *
     * @param showError 显示
     */
    private void shoeErrorPage(boolean showError) {
        if (showError) {
            llEmptyView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        } else {
            llEmptyView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置WebView缓存容量
     *
     * @param webSettings settings
     */
    public void setPageCacheCapacity(WebSettings webSettings) {
        try {
            @SuppressLint("PrivateApi")
            Class<?> c = Class.forName("android.webkit.WebSettingsClassic");
            Method tt = c.getMethod("setPageCacheCapacity", int.class);
            tt.invoke(webSettings, 5);
        } catch (ClassNotFoundException e) {
            System.out.println("No such class: " + e);
        } catch (NoSuchMethodException | IllegalArgumentException |
                IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出对话框
     *
     * @param message 提示信息
     */
    private void showDialog(String message) {
        new AppDialog.Builder(context)
                .setContent(message)
                .create()
                .show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回上一步
     */
    private void goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
