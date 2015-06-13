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
					result.append("����ϸ��                                ����\n");
					result1.append("����ϸ�����ԡ���������֢(��᳦�ס�������)��\n\n");
				} else {
					result.append("����ϸ��                                ����\n");
				}
				if(mRbcSwitchButton.isChecked()) {
					result.append("����ϸ��                                 ����\n");
					result1.append("����ϸ�����ԡ������ڳ����¶���֢���Ѫ(���������᳦�����̳�Ѫ��)��\n\n");
				} else {
					result.append("����ϸ��                                ����\n");
				}
				if(mBldSwitchButton.isChecked()) {
					result.append("���ǱѪ                                    ����\n");
					result1.append("���ǱѪ�������������������񣬶�����������˲����������˺���θ������Ѫ���࣬��Ӧ��ǿ������ɫ��Ӧ��ǿ�����ɷ�Ϊ�ļ����Է�Ӧ��\n\n");
				} else {
					result.append("���ǱѪ                                     ����\n");
				}
				if(mBilSwitchButton.isChecked()) {
					result.append("��㵨����                                 ����");
					result1.append("��㵨�������ԡ���������Ѫ�Ի���͸��Ի��㣬�����Ի��㣬�����Է���Ѫ�Ի��㡣");
				} else {
					result.append("��㵨����                                  ����");
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
				mWbcText.setText("����");
			} else {
				mWbcText.setText("����");
			}
			break;
		case R.id.switchbutton_rbc:
			if(isChecked) {
				mRbcText.setText("����");
			} else {
				mRbcText.setText("����");
			}
			break;
		case R.id.switchbutton_bld:
			if(isChecked) {
				mBldText.setText("����");
			} else {
				mBldText.setText("����");
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
