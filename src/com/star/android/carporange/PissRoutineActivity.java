package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.star.android.carporange.widget.SwitchButton;

public class PissRoutineActivity extends Activity implements OnCheckedChangeListener{
	
	private TextView mBloText;
	private TextView mWbcText;
	private TextView mProText;
	private TextView mGluText;
	private TextView mBilText;
	
	private SwitchButton mBloSwitchButton;
	private SwitchButton mWbcSwitchButton;
	private SwitchButton mProSwitchButton;
	private SwitchButton mGluSwitchButton;
	private SwitchButton mBilSwitchButton;
	
	private Button mSaveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routine_piss);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mBloText = (TextView) findViewById(R.id.tv_blo);
		mWbcText = (TextView) findViewById(R.id.tv_wbc);
		mProText = (TextView) findViewById(R.id.tv_pro);
		mGluText = (TextView) findViewById(R.id.tv_glu);
		mBilText = (TextView) findViewById(R.id.tv_bil);
		
		mBloSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_blo);
		mWbcSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_wbc);
		mProSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_pro);
		mGluSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_glu);
		mBilSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_bil);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
		
	} 

	private void initEvents() {
		
		mBloSwitchButton.setOnCheckedChangeListener(this);
		mWbcSwitchButton.setOnCheckedChangeListener(this);
		mProSwitchButton.setOnCheckedChangeListener(this);
		mGluSwitchButton.setOnCheckedChangeListener(this);
		mBilSwitchButton.setOnCheckedChangeListener(this);
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				StringBuilder result = new StringBuilder("");
				StringBuilder result1 = new StringBuilder("");
				if(mBloSwitchButton.isChecked()) {
					result.append("ÒşÑª                                     ÑôĞÔ\n");
					result1.append("ÒşÑªÑôĞÔ¡ª¡ªÃÚÄòÏµ½áÊ¯¡¢¼±ĞÔÉöĞ¡ÇòÉöÑ×¡¢¼±½øĞÔÉöÑ×¡¢ÂıĞÔÉöÑ×¡¢ÃÚÄòÏµÖ×Áö¡¢½áºË¡¢ÉöÓÛÉöÑ×¡¢°òë×Ñ×¡¢ÀÇ´¯ĞÔÉöÑ×¡¢×Ïñ°ĞÔÉöÑ×\n\n");
				} else {
					result.append("ÒşÑª                                     ÒõĞÔ\n");
				}
				if(mWbcSwitchButton.isChecked()) {
					result.append("°×Ï¸°û                                ÑôĞÔ\n");
					result1.append("°×Ï¸°ûÑôĞÔ¡ª¡ªÄòÂ·¸ĞÈ¾\n\n");
				} else {
					result.append("°×Ï¸°û                                ÒõĞÔ\n");
				}
				if(mProSwitchButton.isChecked()) {
					result.append("Äòµ°°×                                ÑôĞÔ\n");
					result1.append("Äòµ°°×ÑôĞÔ¡ª¡ª¼±ĞÔÉöĞ¡ÇòÉöÑ×¡¢ÌÇÄò²¡ÉöĞÔ²¡±ä\n\n");
				} else {
					result.append("Äòµ°°×                                ÒõĞÔ\n");
				}
				if(mGluSwitchButton.isChecked()) {
					result.append("ÄòÌÇ                                     ÑôĞÔ\n");
					result1.append("ÄòÌÇÑôĞÔ¡ª¡ªÌÇÄò²¡¡¢¼×¿º¡¢Ö«¶Ë·Ê´óÖ¢µÈ\n\n");
				} else {
					result.append("ÄòÌÇ                                     ÒõĞÔ\n");
				}
				if(mBilSwitchButton.isChecked()) {
					result.append("µ¨ºìËØ                                 ÑôĞÔ");
					result1.append("µ¨ºìËØÑôĞÔ¡ª¡ª¸ÎÏ¸°ûĞÔ»ò×èÈûĞÔ»Æğã");
				} else {
					result.append("µ¨ºìËØ                                  ÒõĞÔ");
				}
				Intent i = new Intent(PissRoutineActivity.this, ReadingLaboratoryActivity.class);
				i.putExtra("readingLaboratory", result.toString());
				i.putExtra("possibleIllness", result1.toString());
				startActivity(i);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		switch(buttonView.getId()) {
		case R.id.switchbutton_blo:
			if(isChecked) {
				mBloText.setText("ÑôĞÔ");
			} else {
				mBloText.setText("ÒõĞÔ");
			}
			break;
		case R.id.switchbutton_wbc:
			if(isChecked) {
				mWbcText.setText("ÑôĞÔ");
			} else {
				mWbcText.setText("ÒõĞÔ");
			}
			break;
		case R.id.switchbutton_pro:
			if(isChecked) {
				mProText.setText("ÑôĞÔ");
			} else {
				mProText.setText("ÒõĞÔ");
			}
			break;
		case R.id.switchbutton_glu:
			if(isChecked) {
				mGluText.setText("ÑôĞÔ");
			} else {
				mGluText.setText("ÒõĞÔ");
			}
			break;
		case R.id.switchbutton_bil:
			if(isChecked) {
				mBilText.setText("ÑôĞÔ");
			} else {
				mBilText.setText("ÒõĞÔ");
			}
			break;
		}
	}
	
}
