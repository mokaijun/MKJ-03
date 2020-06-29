package com.jcy.mokaijun.lect10Net;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;




public class WebActivity extends AppCompatActivity {
    public  static  final String WEB_URL="webUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String webUrl = getIntent().getStringExtra(WEB_URL);
        WebView webView=findViewById(R.id.web_content);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
    }
}
