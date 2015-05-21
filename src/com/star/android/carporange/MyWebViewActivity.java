package com.star.android.carporange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled") 
public class MyWebViewActivity extends Activity {
	
	private WebView mWebView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mywebview);
		buildView();
	}

	private void buildView() {
		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		Intent i = getIntent();
		if(i != null) {
			String keyword = i.getStringExtra("keyword");
			loadUrl(keyword);
		}
	}
	private void loadUrl(String keyword) {
		mWebView.loadUrl("http://zhidao.baidu.com/search?word="+keyword);
	}
}
