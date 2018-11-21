package com.wss.module.market.ui.goods.detail.fragment.child;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wss.common.base.BaseFragment;
import com.wss.module.market.R;
import com.wss.module.market.R2;

import butterknife.BindView;

/**
 * Describe：商品详情 - 图文详情 Fragment
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsInfoWebFragment extends BaseFragment {
    @BindView(R2.id.hwv_detail)
    public WebView webView;
    private WebSettings webSettings;


    @Override
    protected int getLayoutId() {
        return R.layout.market_fragment_goods_info_web;
    }

    @Override
    protected void initView() {
        initWebView();
    }

    public void initWebView() {
        String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        webView.setFocusable(false);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new GoodsDetailWebViewClient());
    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
