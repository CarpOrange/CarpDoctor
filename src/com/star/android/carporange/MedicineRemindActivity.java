package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MedicineRemindActivity extends Activity implements OnClickListener{
	
	private String mUsername;
	
	private Button mNewRemindButton;
	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind_medicine);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mUsername = getIntent().getStringExtra("username");
		mNewRemindButton = (Button) findViewById(R.id.button_remind_new);
		
	}
	
	private void initEvents() {
		mNewRemindButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.button_remind_new:
			Intent i = new Intent(MedicineRemindActivity.this, NewRemindActivity.class);
			i.putExtra("username", mUsername);
			startActivityForResult(i, 1);
			break;
		}
	}
}
