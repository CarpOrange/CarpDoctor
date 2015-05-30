package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.star.android.carporange.widget.SwitchButton;

public class KidneyActivity extends Activity {
	
	private EditText mAgeEt;
	private EditText mBunEt;
	private EditText mSrcEt;
	private EditText mBuaEt;
	
	private RadioButton mMaleRb;
	private RadioButton mFemaleRb;
	
	private TextView mProText;
	private SwitchButton mProSwitchButton;
	
	private Button mSaveButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kidney);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mAgeEt = (EditText) findViewById(R.id.et_age);
		mBunEt = (EditText) findViewById(R.id.et_bun);
		mSrcEt = (EditText) findViewById(R.id.et_src);
		mBuaEt = (EditText) findViewById(R.id.et_bua);
		
		mMaleRb = (RadioButton) findViewById(R.id.radiobutton_male);
		mFemaleRb = (RadioButton) findViewById(R.id.radiobutton_female);
		
		mProText = (TextView) findViewById(R.id.tv_pro);
		mProSwitchButton = (SwitchButton) findViewById(R.id.switchbutton_pro);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
	}

	private void initEvents() {
		
		mProSwitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					mProText.setText("����");
				} else {
					mProText.setText("����");
				}
			}
		});
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				StringBuilder result = new StringBuilder("");
				StringBuilder result1 = new StringBuilder("");
				
				String agee = mAgeEt.getText().toString();
				String bun = mBunEt.getText().toString();
				String src = mSrcEt.getText().toString();
				String bua = mBuaEt.getText().toString();
				
				String sex = null;
				
				boolean isProChecked = mProSwitchButton.isChecked();
				
				if(mMaleRb.isChecked()) {
					sex = "��";
				} else if(mFemaleRb.isChecked()) {
					sex = "Ů";
				}
				if(sex == null ) {
					Toast.makeText(KidneyActivity.this, "��ѡ���Ա�", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(agee) ) {
					Toast.makeText(KidneyActivity.this, "����д����", Toast.LENGTH_SHORT).show();
					return;
				}
				int age = Integer.parseInt(agee);
				result.append("\n");
				if(!TextUtils.isEmpty(bun)) {
					Float fBun = Float.parseFloat(bun);
					result.append("BUN  Ѫ���ص�     ");
					if(fBun < 1.8) {
						result.append(fBun).append("  mmol/L  ƫ��");
						result1.append("BUNƫ�͡����͵�����ʳ���μ�����ʹ��ֵ���ͣ���ʱ�ɳ�Ϊ�͵���Ѫ֢.\n\n");
					} else if (fBun > 6.8) {
						result.append(fBun).append("  mmol/L  ƫ��");
						result1.append("BUNƫ�ߡ������������ס���֢�������ס�����ԭ�����µļ������������ϰ�����˥���ݿˡ������ڳ�Ѫ�����ˡ�ʧˮ��������Ƥ�ʹ��ܼ���֢��ǰ���ٷʴ�������·����ȡ�\n\n");
					} else {
						result.append("           ����");
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(src)) {
					result.append("Src  Ѫ����     ");
					Float fSrc = Float.parseFloat(src);
					if(age < 18) {
						if(fSrc < 26.5) {
							result.append(fSrc).append("  ��mol/L  ƫ��");
							result1.append("Ѫ����ƫ�͡��������Լ�ή������Ѫ����ƶѪ�ȡ�\n\n");
						} else if (fSrc > 62.0) {
							result.append(fSrc).append("  ��mol/L  ƫ��");
							result1.append("Ѫ����ƫ�ߡ�����˥����֢����˥������֢��֫�˷ʴ�֢��ˮ�����������Ƶȡ�\n\n");
						} else {
							result.append("         ����");
						}
					} else if( sex.equals("��") ) {
						if(fSrc < 79.6) {
							result.append(fSrc).append("  ��mol/L  ƫ��");
							result1.append("Ѫ����ƫ�͡��������Լ�ή������Ѫ����ƶѪ�ȡ�\n\n");
						} else if (fSrc > 132.6) {
							result.append(fSrc).append("  ��mol/L  ƫ��");
							result1.append("Ѫ����ƫ�ߡ�����˥����֢����˥������֢��֫�˷ʴ�֢��ˮ�����������Ƶȡ�\n\n");
						} else {
							result.append("         ����");
						}
					} else if( sex.equals("Ů")) {
						if(fSrc < 70.7) {
							result.append(fSrc).append("  ��mol/L  ƫ��");
							result1.append("Ѫ����ƫ�͡��������Լ�ή������Ѫ����ƶѪ�ȡ�\n\n");
						} else if (fSrc > 106.1) {
							result.append(fSrc).append("  ��mol/L  ƫ��");
							result1.append("Ѫ����ƫ�ߡ�����˥����֢����˥������֢��֫�˷ʴ�֢��ˮ�����������Ƶȡ�\n\n");
						} else {
							result.append("         ����");
						}
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(bua)) {
					result.append("BUA  Ѫ����     ");
					Float fBua = Float.parseFloat(bua);
					if(age <= 60) {
						if( sex.equals("��") ) {
							if(fBua < 149) {
								result.append(fBua).append("  ��mol/L  ƫ��");
							} else if (fBua > 417) {
								result.append(fBua).append("  ��mol/L  ƫ��");
								result1.append("Ѫ����ƫ�ߡ���ʹ�硢�����԰�Ѫ�����෢�Թ�����������ƶѪ����˥����˥����ϸ������֢�����ﷴӦ�����һ����֬���ͺ�ȡ�\n\n");
							} else {
								result.append("         ����");
							}
						} else if( sex.equals("Ů")) {
							if(fBua < 89) {
								result.append(fBua).append("  ��mol/L  ƫ��");
							} else if (fBua > 357) {
								result.append(fBua).append("  ��mol/L  ƫ��");
								result1.append("Ѫ����ƫ�ߡ���ʹ�硢�����԰�Ѫ�����෢�Թ�����������ƶѪ����˥����˥����ϸ������֢�����ﷴӦ�����һ����֬���ͺ�ȡ�\n\n");
							} else {
								result.append("         ����");
							}
						}
					} else {
						if( sex.equals("��") ) {
							if(fBua < 250) {
								result.append(fBua).append("  ��mol/L  ƫ��");
							} else if (fBua > 476) {
								result.append(fBua).append("  ��mol/L  ƫ��");
								result1.append("Ѫ����ƫ�ߡ���ʹ�硢�����԰�Ѫ�����෢�Թ�����������ƶѪ����˥����˥����ϸ������֢�����ﷴӦ�����һ����֬���ͺ�ȡ�\n\n");
							} else {
								result.append("         ����");
							}
						} else if( sex.equals("Ů")) {
							if(fBua < 150) {
								result.append(fBua).append("  ��mol/L  ƫ��");
							} else if (fBua > 434) {
								result.append(fBua).append("  ��mol/L  ƫ��");
								result1.append("Ѫ����ƫ�ߡ���ʹ�硢�����԰�Ѫ�����෢�Թ�����������ƶѪ����˥����˥����ϸ������֢�����ﷴӦ�����һ����֬���ͺ�ȡ�\n\n");
							} else {
								result.append("         ����");
							}
						}
					}
					result.append("\n");
				}
				result.append("Pro  �򵰰�   ").append(isProChecked ? "       ����\n " : "         ����\n");
				if(isProChecked) {
					result1.append("�򵰰����ԡ�����λ�Ե������˶��Ե����򡢷��ȡ�����������������ȵ�����ȡ�");
				}
				
				Intent i = new Intent(KidneyActivity.this, ReadingLaboratoryActivity.class);
				i.putExtra("readingLaboratory", result.toString());
				i.putExtra("possibleIllness", result1.toString());
				startActivity(i);
			}
		});
	}
	
}
