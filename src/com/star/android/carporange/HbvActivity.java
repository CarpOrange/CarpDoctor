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

public class HbvActivity extends Activity implements OnCheckedChangeListener{
	
	private TextView mBsagText;
	private TextView mBsabText;
	private TextView mBeagText;
	private TextView mBeabText;
	private TextView mBcabText;
	
	private SwitchButton mBsagSwitchButton;
	private SwitchButton mBsabSwitchButton;
	private SwitchButton mBeagSwitchButton;
	private SwitchButton mBeabSwitchButton;
	private SwitchButton mBcabSwitchButton;
	
	private Button mSaveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hbv);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mBsagText = (TextView) findViewById(R.id.tv_hbsag);
		mBsabText = (TextView) findViewById(R.id.tv_hbsab);
		mBeagText = (TextView) findViewById(R.id.tv_hbeag);
		mBeabText = (TextView) findViewById(R.id.tv_hbeab);
		mBcabText = (TextView) findViewById(R.id.tv_hbcab);
		
		mBsagSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_hbsag);
		mBsabSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_hbsab);
		mBeagSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_hbeag);
		mBeabSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_hbeab);
		mBcabSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_hbcab);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
		
	}

	private void initEvents() {
		
		mBsagSwitchButton.setOnCheckedChangeListener(this);
		mBsabSwitchButton.setOnCheckedChangeListener(this);
		mBeagSwitchButton.setOnCheckedChangeListener(this);
		mBeabSwitchButton.setOnCheckedChangeListener(this);
		mBcabSwitchButton.setOnCheckedChangeListener(this);
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				StringBuilder result = new StringBuilder("");
				StringBuilder result1 = new StringBuilder("");
				
				boolean isBsagChecked = mBsagSwitchButton.isChecked();
				boolean isBsabChecked = mBsabSwitchButton.isChecked();
				boolean isBeagChecked = mBeagSwitchButton.isChecked();
				boolean isBeabChecked = mBeabSwitchButton.isChecked();
				boolean isBcabChecked = mBcabSwitchButton.isChecked();
				
				result.append(isBsagChecked ? "HBsAg  �Ҹα��濹ԭ             ����\n " : "HBsAg  �Ҹα���Ĥ��ԭ             ����\n")
					.append(isBsabChecked ? "HBsAb  �Ҹα��濹��             ����\n " : "HBsAb  �Ҹα���Ĥ����             ����\n")
					.append(isBeagChecked ? "HBeAg  �Ҹ�e��ԭ             ���� \n" : "HBeAg  �Ҹ�e��ԭ             ����\n")
					.append(isBeabChecked ? "HBeAb  �Ҹ�eĤ����             ����\n " : "HBeAb  �Ҹ�e����             ����\n")
					.append(isBcabChecked ? "HBcAb  �Ҹκ��Ŀ���             ���� " : "HBcAb  �Ҹκ��Ŀ���             ����");
				if(isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("���Բ�����Ⱦ��Ǳ���ں��ڡ�");
				} else if(isBsagChecked && !isBsabChecked && isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("�׳��Ҹδ�������˵���Ǽ��������ҸΣ���Ⱦ����Խ�ǿ��");
				} else if(isBsagChecked && !isBsabChecked && !isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("�׳��Ҹ�С������˵���Ǽ��������ҸΣ���Ⱦ����Խ�����");
				} else if(isBsagChecked && !isBsabChecked && isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("�����Ҹε����ڡ�");
				} else if(isBsagChecked && !isBsabChecked && isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("�����Ҹθ�Ⱦ����ָ�������Ϊ�����Ҹβ���Я���ߡ�");
				} else if(isBsagChecked && !isBsabChecked && !isBeagChecked && isBeabChecked && !isBcabChecked) {
					result1.append("�����Ҹα��濹ԭЯ������ת���������Ǽ��Ը�Ⱦ����ָ���");
				} else if(isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("���������ҸΣ������ټ���HBV��Ⱦ;������HBsAgЯ����;�۴�Ⱦ������");
				} else if(!isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("�ټ�����Ⱦδ�ܲ����-HBs���ڻָ���HBsAg��������-HBs��δ���֣�����֢״HBsAgЯ���ߡ�");
				} else if(!isBsagChecked && isBsabChecked && !isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("�ҸεĻָ��ڣ�������������");
				} else if(!isBsagChecked && isBsabChecked && !isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("������ע����Ҹ����粢�����˿��壬�����������������й��Ҹβ����ĸ�Ⱦ��������һ�������������ۼ����ԡ�");
				} else if(!isBsagChecked && isBsabChecked && !isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("�������Ҹ������Ժ󣬻����Ҹβ�����Ⱦ���ѿ�����������������");
				} else if(!isBsagChecked && !isBsabChecked && !isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("�����Ҹβ�����Ⱦ�Ļָ��ڣ���������Ⱦ��������");
				} else if(!isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("δ��Ⱦ��HBV����Ŀǰû�б����Կ��塣");
				} else {
					result1.append("����֢״��Ϊ�ټ���������ѯҽ����");
				}
				
				Intent i = new Intent(HbvActivity.this, ReadingLaboratoryActivity.class);
				i.putExtra("readingLaboratory", result.toString());
				i.putExtra("possibleIllness", result1.toString());
				startActivity(i);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		switch(buttonView.getId()) {
		case R.id.switchbutton_hbsag:
			if(isChecked) {
				mBsagText.setText("����");
			} else {
				mBsagText.setText("����");
			}
			break;
		case R.id.switchbutton_hbsab:
			if(isChecked) {
				mBsabText.setText("����");
			} else {
				mBsabText.setText("����");
			}
			break;
		case R.id.switchbutton_hbeag:
			if(isChecked) {
				mBeagText.setText("����");
			} else {
				mBeagText.setText("����");
			}
			break;
		case R.id.switchbutton_hbeab:
			if(isChecked) {
				mBeabText.setText("����");
			} else {
				mBeabText.setText("����");
			}
			break;
		case R.id.switchbutton_hbcab:
			if(isChecked) {
				mBcabText.setText("����");
			} else {
				mBcabText.setText("����");
			}
			break;
		}
		
	}
}
