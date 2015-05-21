package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class HealthInfoActivity extends Activity {
	
	private TextView mMainTitleTextView;
	private TextView mContentTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthinfo);
		buildView();
	}

	private void buildView() {
		
		mMainTitleTextView = (TextView) findViewById(R.id.textView_mainTitle);
		mContentTextView = (TextView) findViewById(R.id.textView_content);
		Intent i = getIntent();
		String mainTitle = i.getStringExtra("mainTitle");
		mMainTitleTextView.setText(mainTitle);
		String content = i.getStringExtra("content");
		mContentTextView.setText(Html.fromHtml(content));
	}
	
}
