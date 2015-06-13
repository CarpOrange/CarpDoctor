package com.star.android.carporange;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.star.android.carporange.widget.CarpActivity;

public class BloodRoutineActivity extends CarpActivity {
	
	String mReadingLaboratory = null;
	String mPossibleIllness = null;

	private EditText mAgeEt;
	private EditText mWBCEt;
	private EditText mRBCEt;
	private EditText mPLTEt;
	private EditText mLYEt;
	
	private RadioButton mMaleRb;
	private RadioButton mFemaleRb;
	
	private Button mSaveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routine_blood);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mAgeEt = (EditText) findViewById(R.id.et_age);
		mWBCEt = (EditText) findViewById(R.id.et_wbc);
		mRBCEt = (EditText) findViewById(R.id.et_rbc);
		mPLTEt = (EditText) findViewById(R.id.et_plt);
		mLYEt = (EditText) findViewById(R.id.et_ly);
		
		mMaleRb = (RadioButton) findViewById(R.id.radiobutton_male);
		mFemaleRb = (RadioButton) findViewById(R.id.radiobutton_female);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
	}

	private void initEvents() {
		
		mSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				String age = mAgeEt.getText().toString();
				String wbc = mWBCEt.getText().toString();
				String rbc = mRBCEt.getText().toString();
				String plt = mPLTEt.getText().toString();
				String ly = mLYEt.getText().toString();
				String sex = null;
				if(mMaleRb.isChecked()) {
					sex = "��";
				} else if(mFemaleRb.isChecked()) {
					sex = "Ů";
				}
				if(sex == null ) {
					Toast.makeText(BloodRoutineActivity.this, "��ѡ���Ա�", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(age) ) {
					Toast.makeText(BloodRoutineActivity.this, "����д����", Toast.LENGTH_SHORT).show();
					return;
				}
				readLaboratory(age, sex, wbc, rbc, plt, ly);
				Intent i = new Intent(BloodRoutineActivity.this, ReadingLaboratoryActivity.class);
				i.putExtra("readingLaboratory", mReadingLaboratory);
				i.putExtra("possibleIllness", mPossibleIllness);
				startActivity(i);
			}

			private void readLaboratory(String agee, String sex, String wbc, String rbc, String plt, String ly) {
				Integer age = Integer.parseInt(agee);
				StringBuilder result = new StringBuilder("");
				StringBuilder result1 = new StringBuilder("");
				if(!TextUtils.isEmpty(wbc)) {
					result.append("WBC  ��ϸ������  ");
					Float intWbc = Float.parseFloat(wbc);
					if(age == 1) {
						if(intWbc < 15) {
							result.append(wbc).append("  10^9L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�͡���������Ⱦ���˺� �����˺������Ȳ���ű�������ϡ��������ظ�Ⱦ��X�߼������䡢�������ơ��ǰ�Ѫ�԰�Ѫ������ϸ��ȱ��֢��\n\n");
						} else if (intWbc > 20) {
							result.append(wbc).append("  10^9L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�ߡ���������:������������ĩ�ڡ������ڡ����ڡ����󡢾����˶�����ˮԡ�����ȿ־�����ʹ�ȡ������ԣ��󲿷ֻ�ŧ��ϸ���������֢����֢���������ˡ���Ⱦ�Ե���ϸ������֢��\n\n");
						} else {
							result.append("         ����");
						}
					} else if( age > 1 && age < 18) {
						if(intWbc < 5) {
							result.append(wbc).append("  10^9L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�͡���������Ⱦ���˺� �����˺������Ȳ���ű�������ϡ��������ظ�Ⱦ��X�߼������䡢�������ơ��ǰ�Ѫ�԰�Ѫ������ϸ��ȱ��֢��\n\n");
						} else if (intWbc > 12) {
							result.append(wbc).append("  10^9L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�ߡ���������:������������ĩ�ڡ������ڡ����ڡ����󡢾����˶�����ˮԡ�����ȿ־�����ʹ�ȡ������ԣ��󲿷ֻ�ŧ��ϸ���������֢����֢���������ˡ���Ⱦ�Ե���ϸ������֢��\n\n");
						} else {
							result.append("         ����");
						}
					} else if( age > 18) {
						if(intWbc < 4) {
							result.append(wbc).append("  10^9L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�͡���������Ⱦ���˺� �����˺������Ȳ���ű�������ϡ��������ظ�Ⱦ��X�߼������䡢�������ơ��ǰ�Ѫ�԰�Ѫ������ϸ��ȱ��֢��\n\n");
						} else if (intWbc > 10) {
							result.append(wbc).append("  10^9L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�ߡ���������:������������ĩ�ڡ������ڡ����ڡ����󡢾����˶�����ˮԡ�����ȿ־�����ʹ�ȡ������ԣ��󲿷ֻ�ŧ��ϸ���������֢����֢���������ˡ���Ⱦ�Ե���ϸ������֢��\n\n");
						} else {
							result.append("         ����");
						}
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(rbc)) {
					result.append("RBC  ��ϸ������  ");
					Float intRbc = Float.parseFloat(rbc);
					if(age == 1) {
						if(intRbc < 6) {
							result.append(rbc).append("  10^12L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�͡�������ƶѪ����Ѫ����������ʧѪ��\n\n");
						} else if (intRbc > 7) {
							result.append(rbc).append("  10^12L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�ߡ���������:��ԭ��ס�߲�����:���Ժ�ϸ������֢�������Ժ�ϸ������֢��\n\n");
						} else {
							result.append("         ����");
						}
					} else if( sex.equals("��") ) {
						if(intRbc < 4.5) {
							result.append(rbc).append("  10^12L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�͡�������ƶѪ����Ѫ����������ʧѪ��\n\n");
						} else if (intRbc > 5) {
							result.append(rbc).append("  10^12L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�ߡ���������:��ԭ��ס�߲�����:���Ժ�ϸ������֢�������Ժ�ϸ������֢��\n\n");
						} else {
							result.append("         ����");
						}
					} else if( sex.equals("Ů")) {
						if(intRbc < 3.5) {
							result.append(rbc).append("  10^12L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�͡�������ƶѪ����Ѫ����������ʧѪ��\n\n");
						} else if (intRbc > 5) {
							result.append(rbc).append("  10^12L  ").append("ƫ��");
							result1.append("��ϸ��ƫ�ߡ���������:��ԭ��ס�߲�����:���Ժ�ϸ������֢�������Ժ�ϸ������֢��\n\n");
						} else {
							result.append("         ����");
						}
					}
					result.append("\n");
				}
				
				if(!TextUtils.isEmpty(plt)) {
					result.append("PLT  ѪС�����  ");
					Float intPlt = Float.parseFloat(plt);
					if(intPlt < 100) {
						result.append(plt).append("  10^9L  ").append("ƫ��");
						result1.append("ѪС��ƫ�͡���1.ѪС�������ϰ����������ϰ���ƶѪ�����԰�Ѫ�������Է��䲡��ǿ�һ��ƺ�;2.ѪС���ƻ����ࣺԭ����ѪС����������Ƣ���ܿ���������ѭ����ϵͳ�Ժ���Ǵ�;3.ѪС�����Ĺ��ࣺDIC��Ѫ˨��ѪС�����������;������ѪС�����;4.�޴�ѪС���ۺ�������ɫѪС���ۺ����쳣�ȡ�\n\n");
					} else if (intPlt > 300) {
						result.append(plt).append("  10^9L  ").append("ƫ��");
						result1.append("ѪС��ƫ�ߡ���1.������ֳ�Լ�����ԭ���Գ�Ѫ��ѪС������֢�����Ժ�ϸ������֢��������ϸ����Ѫ�������ڡ�������ά������; 2.���Է�Ӧ�����Դ��Ѫ��������Ѫ����Ⱦ��Ƣ�г�����\n\n");
					} else {
						result.append("         ����");
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(ly)) {
					result.append("LY  �ܰ�ϸ������  ");
					Float intLy = Float.parseFloat(ly);
					if(intLy < 20) {
						result.append(intLy).append("    %    ").append("ƫ��");
					} else if (intLy > 40) {
						result.append(intLy).append("    %    ").append("ƫ��");
						result1.append("�ܰ�ϸ��ƫ�ߡ������տȡ���Ⱦ�Ե���ϸ������֢�������ס���ˡ����ס�\n\n");
					} else {
						result.append("         ����");
					}
					result.append("\n");
				}
				mReadingLaboratory = result.toString();
				mPossibleIllness = result1.toString();
			}
		});
	}
	
}
