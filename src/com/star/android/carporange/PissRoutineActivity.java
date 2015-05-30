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
					result.append("��Ѫ                                     ����\n");
					result1.append("��Ѫ���ԡ�������ϵ��ʯ��������С�����ס����������ס��������ס�����ϵ��������ˡ��������ס������ס��Ǵ������ס����������\n\n");
				} else {
					result.append("��Ѫ                                     ����\n");
				}
				if(mWbcSwitchButton.isChecked()) {
					result.append("��ϸ��                                ����\n");
					result1.append("��ϸ�����ԡ�����·��Ⱦ\n\n");
				} else {
					result.append("��ϸ��                                ����\n");
				}
				if(mProSwitchButton.isChecked()) {
					result.append("�򵰰�                                ����\n");
					result1.append("�򵰰����ԡ���������С�����ס��������Բ���\n\n");
				} else {
					result.append("�򵰰�                                ����\n");
				}
				if(mGluSwitchButton.isChecked()) {
					result.append("����                                     ����\n");
					result1.append("�������ԡ������򲡡��׿���֫�˷ʴ�֢��\n\n");
				} else {
					result.append("����                                     ����\n");
				}
				if(mBilSwitchButton.isChecked()) {
					result.append("������                                 ����");
					result1.append("���������ԡ�����ϸ���Ի������Ի���");
				} else {
					result.append("������                                  ����");
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
				mBloText.setText("����");
			} else {
				mBloText.setText("����");
			}
			break;
		case R.id.switchbutton_wbc:
			if(isChecked) {
				mWbcText.setText("����");
			} else {
				mWbcText.setText("����");
			}
			break;
		case R.id.switchbutton_pro:
			if(isChecked) {
				mProText.setText("����");
			} else {
				mProText.setText("����");
			}
			break;
		case R.id.switchbutton_glu:
			if(isChecked) {
				mGluText.setText("����");
			} else {
				mGluText.setText("����");
			}
			break;
		case R.id.switchbutton_bil:
			if(isChecked) {
				mBilText.setText("����");
			} else {
				mBilText.setText("����");
			}
			break;
		}
	}
	
}
