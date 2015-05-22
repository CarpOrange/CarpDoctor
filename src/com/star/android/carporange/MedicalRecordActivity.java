package com.star.android.carporange;

import java.io.ObjectOutputStream.PutField;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MedicalRecordActivity extends Activity implements OnClickListener {
	 
	private String mUsername;
	
	private Button mNewRecordButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_medical);
		buildView();
		initEvents();
	}

	private void buildView() {
		Intent i = getIntent();
		mUsername = i.getStringExtra("username");
		mNewRecordButton = (Button) findViewById(R.id.button_record_new);
		
	}

	private void initEvents() {
		mNewRecordButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.button_record_new:
			Intent i = new Intent();
			i.putExtra("username", mUsername);
			i.setClass(MedicalRecordActivity.this, NewRecordActivity.class);
			startActivity(i);
			break;
		}
	}

}
