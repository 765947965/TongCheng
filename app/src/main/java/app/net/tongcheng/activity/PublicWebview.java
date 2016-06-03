package app.net.tongcheng.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import app.net.tongcheng.R;
import app.net.tongcheng.model.ConnectResult;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/13 14:03
 */
public class PublicWebview extends BaseActivity {
    private WebView webview;
    private String title;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_webview);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
        }
        initView();
        setTitle(title);
    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setDefaultTextEncodingName("utf-8");
        webview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setSupportZoom(true);
        if (android.os.Build.VERSION.SDK_INT < 21) {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //低版本系统兼容性 不留空白页
        }
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(wvc);
    }

    WebViewClient wvc = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith("http")) {
                return false;
            } else {
                view.loadUrl(url);
                return true;
            }
        }
    };


    @Override
    public void loadData() {
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
    }

    @Override
    public void mHandDoSomeThing(Message msg) {

    }

    @Override
    public void BusinessOnSuccess(int mLoding_Type, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoding_Type) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        webview.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        webview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webview.onResume();
        super.onResume();
    }
}
