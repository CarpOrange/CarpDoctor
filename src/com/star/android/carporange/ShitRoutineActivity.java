package com.star.android.carporange;

import com.star.android.carporange.widget.CarpActivity;
import com.star.android.carporange.widget.SwitchButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ShitRoutineActivity extends CarpActivity implements OnCheckedChangeListener{
	
	private TextView mWbcText;
	private TextView mRbcText;
	private TextView mBldText;
	private TextView mBilText;
	
	private SwitchButton mWbcSwitchButton;
	private SwitchButton mRbcSwitchButton;
	private SwitchButton mBldSwitchButton;
	private SwitchButton mBilSwitchButton;
	
	private Button mSaveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routine_shit);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mWbcText = (TextView) findViewById(R.id.tv_wbc);
		mRbcText = (TextView) findViewById(R.id.tv_rbc);
		mBldText = (TextView) findViewById(R.id.tv_bld);
		mBilText = (TextView) findViewById(R.id.tv_bil);
		
		mWbcSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_wbc);
		mRbcSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_rbc);
		mBldSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_bld);
		mBilSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_bil);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
	}

	private void initEvents() {
		mWbcSwitchButton.setOnCheckedChangeListener(this);
		mRbcSwitchButton.setOnCheckedChangeListener(this);
		mBldSwitchButton.setOnCheckedChangeListener(this);
		mBilSwitchButton.setOnCheckedChangeListener(this);
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				StringBuilder result = new StringBuilder("");
				StringBuilder result1 = new StringBuilder("");
				
				if(mWbcSwitchButton.isChecked()) {
					result.append("粪便白细胞                                阳性\n");
					result1.append("粪便白细胞阳性——肠道炎症(如结肠炎、菌痢等)。\n\n");
				} else {
					result.append("粪便白细胞                                阴性\n");
				}
				if(mRbcSwitchButton.isChecked()) {
					result.append("粪便红细胞                                 阳性\n");
					result1.append("粪便红细胞阳性——见于肠道下段炎症或出血(如痢疾、结肠癌、痔出血等)。\n\n");
				} else {
					result.append("粪便红细胞                                阴性\n");
				}
				if(mBldSwitchButton.isChecked()) {
					result.append("粪便潜血                                    阳性\n");
					result1.append("粪便潜血——常见于消化道溃疡，恶性肿瘤，结核病，痢疾，伤寒。胃肠道出血愈多，反应愈强。按显色反应的强弱，可分为四级阳性反应。\n\n");
				} else {
					result.append("粪便潜血                                     阴性\n");
				}
				if(mBilSwitchButton.isChecked()) {
					result.append("粪便胆红素                                 阳性");
					result1.append("粪便胆红素阳性——见于溶血性黄疸和肝性黄疸，阻塞性黄疸，先天性非溶血性黄疸。");
				} else {
					result.append("粪便胆红素                                  阴性");
				}
				Intent i = new Intent(ShitRoutineActivity.this, ReadingLaboratoryActivity.class);
				i.putExtra("readingLaboratory", result.toString());
				i.putExtra("possibleIllness", result1.toString());
				startActivity(i);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch(buttonView.getId()) {
		case R.id.switchbutton_wbc:
			if(isChecked) {
				mWbcText.setText("阳性");
			} else {
				mWbcText.setText("阴性");
			}
			break;
		case R.id.switchbutton_rbc:
			if(isChecked) {
				mRbcText.setText("阳性");
			} else {
				mRbcText.setText("阴性");
			}
			break;
		case R.id.switchbutton_bld:
			if(isChecked) {
				mBldText.setText("阳性");
			} else {
				mBldText.setText("阴性");
			}
			break;
		case R.id.switchbutton_bil:
			if(isChecked) {
				mBilText.setText("阳性");
			} else {
				mBilText.setText("阴性");
			}
			break;
		}
	}
}
