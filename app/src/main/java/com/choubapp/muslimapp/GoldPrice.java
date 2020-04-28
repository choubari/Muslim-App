package com.choubapp.muslimapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class GoldPrice extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_price);
        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            webView = (WebView) findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://goldpricez.com/calculator/gold-rates");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfirst), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}