package com.econet.app.homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.econet.app.solwallet.R;
public class WebViewActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Uri data = getIntent().getData();
        WebView view = (WebView) findViewById(R.id.webview);
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setDomStorageEnabled(true);
        view.getSettings().setSupportMultipleWindows(true);
        view.loadUrl("https://sollet.io");
        //view.loadUrl("https://raydium.io/swap/");
        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        view.setWebViewClient(new MyClient());

    }


    private class MyClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // handler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理

            AlertDialog.Builder builder =new AlertDialog.Builder(WebViewActivity.this);

            builder.setMessage("SSL cert invalid");

            builder.setPositiveButton("continue", (dialog, which) ->handler.proceed());

            builder.setNegativeButton("cancel", (dialog, which) ->handler.cancel());

            AlertDialog dialog = builder.create();

            dialog.show();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
