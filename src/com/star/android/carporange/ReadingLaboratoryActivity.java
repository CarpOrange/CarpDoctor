package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class ReadingLaboratoryActivity extends Activity {
	
	private TextView mReadingLaboratoryText;
	private TextView mPossibleIllnessText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laboratory_reading); 
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mReadingLaboratoryText = (TextView) findViewById(R.id.tv_laboratory_reading);
		mPossibleIllnessText = (TextView) findViewById(R.id.tv_illness_possible);
		
		Intent i = getIntent();
		String readingLaboratory = i.getStringExtra("readingLaboratory");
		String possbileIllness = i.getStringExtra("possibleIllness");
		mReadingLaboratoryText.setText(readingLaboratory);
		mPossibleIllnessText.setText(possbileIllness);
	}

	private void initEvents() {
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
