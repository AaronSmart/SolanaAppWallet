package com.econet.app.gamefi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import com.econet.app.solwallet.R;

public class H5hybridActivity extends Activity {
    WebView wv_test;
    Button btn_makeSentence;
    Button btn_webAdd;

    private static final String JSOBJECT = "MyJS";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5hybrid);
        wv_test = findViewById(R.id.wv_test);
        btn_makeSentence = findViewById(R.id.btn_makeSentence);
        btn_webAdd = findViewById(R.id.btn_webAdd);

        btn_makeSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv_test.loadUrl(NativeObject.makeSentence("潇洒", "放荡"));
            }
        });

        btn_webAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv_test.loadUrl(NativeObject.add(5, 10));
            }
        });

        // 设置webview属性
        WebSettings settings = wv_test.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        wv_test.setWebChromeClient(new WebChromeClient());
        // 这里需要注意,JSObject里面提供的方法需要添加@JavascriptInterface注释,
        // 否则会报"they will not be visible in API 17"错误
        wv_test.addJavascriptInterface(new JSObject(this), JSOBJECT);
        // 加载网页,若非本地页面,则把下面的加载地址换在页面url
        wv_test.loadUrl("file:///android_asset/2048/hybrid.html");
    }
}
