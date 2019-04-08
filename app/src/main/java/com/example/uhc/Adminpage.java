package com.example.uhc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Adminpage extends AppCompatActivity {
    private WebView AwebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);






        AwebView = (WebView) findViewById(R.id.AwebView);
        WebSettings webSettings = AwebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        AwebView.loadUrl("https://tinyurl.com/y9myf2oz");
        AwebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(AwebView.canGoBack())
        {
            AwebView.goBack();
        }else {
            super.onBackPressed();
        }

    }
}
