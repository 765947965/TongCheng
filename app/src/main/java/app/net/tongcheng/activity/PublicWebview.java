package app.net.tongcheng.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
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
import app.net.tongcheng.util.ToastUtil;
import app.net.tongcheng.view.PublicWebViewMenuDialog;
import okhttp3.Response;


/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2016 Tuandai Inc. All rights reserved.
 * @date: 2016/4/13 14:03
 */
public class PublicWebview extends BaseActivity implements View.OnClickListener, PublicWebViewMenuDialog.MenuDialogListener {
    private WebView webview;
    private String title;
    private String url;
    private PublicWebViewMenuDialog mPublicWebViewMenuDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_webview);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
        }
        initView();
        setCheckLoad(false);
        setTitle(title);
    }

    private void initView() {
        getRightIV().setVisibility(View.VISIBLE);
        getRightIV().setImageResource(R.drawable.menu);
        getRightIV().setOnClickListener(this);
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
    public void BusinessOnSuccess(int mLoadType, ConnectResult mConnectResult) {

    }

    @Override
    public void BusinessOnFail(int mLoadType, Response response) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRight:
                if (mPublicWebViewMenuDialog == null) {
                    mPublicWebViewMenuDialog = new PublicWebViewMenuDialog(this, this);
                }
                mPublicWebViewMenuDialog.show();
                break;
        }
    }

    @Override
    public void onUpdateRemark() {
        Intent mIntent = new Intent("android.intent.action.VIEW", Uri.parse(webview.getUrl()));
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);
    }

    @Override
    public void deleteFriend() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData textCd = ClipData.newPlainText("num", webview.getUrl());
        clipboard.setPrimaryClip(textCd);
        ToastUtil.showToast("已复制");
    }
}
