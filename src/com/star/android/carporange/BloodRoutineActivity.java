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
					sex = "男";
				} else if(mFemaleRb.isChecked()) {
					sex = "女";
				}
				if(sex == null ) {
					Toast.makeText(BloodRoutineActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(age) ) {
					Toast.makeText(BloodRoutineActivity.this, "请填写年龄", Toast.LENGTH_SHORT).show();
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
					result.append("WBC  白细胞计数  ");
					Float intWbc = Float.parseFloat(wbc);
					if(age == 1) {
						if(intWbc < 15) {
							result.append(wbc).append("  10^9L  ").append("偏低");
							result1.append("白细胞偏低——病毒感染、伤寒 、副伤寒、黑热病、疟疾、再障、极度严重感染、X线及镭照射、肿瘤化疗、非白血性白血病、粒细胞缺乏症。\n\n");
						} else if (intWbc > 20) {
							result.append(wbc).append("  10^9L  ").append("偏高");
							result1.append("白细胞偏高——生理性:新生儿、妊娠末期、分娩期、经期、饭后、剧烈运动后、冷水浴及极度恐惧与疼痛等。病理性：大部分化脓性细菌引起的炎症、尿毒症、严重烧伤、传染性单核细胞增多症。\n\n");
						} else {
							result.append("         正常");
						}
					} else if( age > 1 && age < 18) {
						if(intWbc < 5) {
							result.append(wbc).append("  10^9L  ").append("偏低");
							result1.append("白细胞偏低——病毒感染、伤寒 、副伤寒、黑热病、疟疾、再障、极度严重感染、X线及镭照射、肿瘤化疗、非白血性白血病、粒细胞缺乏症。\n\n");
						} else if (intWbc > 12) {
							result.append(wbc).append("  10^9L  ").append("偏高");
							result1.append("白细胞偏高——生理性:新生儿、妊娠末期、分娩期、经期、饭后、剧烈运动后、冷水浴及极度恐惧与疼痛等。病理性：大部分化脓性细菌引起的炎症、尿毒症、严重烧伤、传染性单核细胞增多症。\n\n");
						} else {
							result.append("         正常");
						}
					} else if( age > 18) {
						if(intWbc < 4) {
							result.append(wbc).append("  10^9L  ").append("偏低");
							result1.append("白细胞偏低——病毒感染、伤寒 、副伤寒、黑热病、疟疾、再障、极度严重感染、X线及镭照射、肿瘤化疗、非白血性白血病、粒细胞缺乏症。\n\n");
						} else if (intWbc > 10) {
							result.append(wbc).append("  10^9L  ").append("偏高");
							result1.append("白细胞偏高——生理性:新生儿、妊娠末期、分娩期、经期、饭后、剧烈运动后、冷水浴及极度恐惧与疼痛等。病理性：大部分化脓性细菌引起的炎症、尿毒症、严重烧伤、传染性单核细胞增多症。\n\n");
						} else {
							result.append("         正常");
						}
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(rbc)) {
					result.append("RBC  红细胞计数  ");
					Float intRbc = Float.parseFloat(rbc);
					if(age == 1) {
						if(intRbc < 6) {
							result.append(rbc).append("  10^12L  ").append("偏低");
							result1.append("红细胞偏低——各种贫血、白血病、急慢性失血。\n\n");
						} else if (intRbc > 7) {
							result.append(rbc).append("  10^12L  ").append("偏高");
							result1.append("红细胞偏高——生理性:高原居住者病理性:真性红细胞增多症、代偿性红细胞增多症。\n\n");
						} else {
							result.append("         正常");
						}
					} else if( sex.equals("男") ) {
						if(intRbc < 4.5) {
							result.append(rbc).append("  10^12L  ").append("偏低");
							result1.append("红细胞偏低——各种贫血、白血病、急慢性失血。\n\n");
						} else if (intRbc > 5) {
							result.append(rbc).append("  10^12L  ").append("偏高");
							result1.append("红细胞偏高——生理性:高原居住者病理性:真性红细胞增多症、代偿性红细胞增多症。\n\n");
						} else {
							result.append("         正常");
						}
					} else if( sex.equals("女")) {
						if(intRbc < 3.5) {
							result.append(rbc).append("  10^12L  ").append("偏低");
							result1.append("红细胞偏低——各种贫血、白血病、急慢性失血。\n\n");
						} else if (intRbc > 5) {
							result.append(rbc).append("  10^12L  ").append("偏高");
							result1.append("红细胞偏高——生理性:高原居住者病理性:真性红细胞增多症、代偿性红细胞增多症。\n\n");
						} else {
							result.append("         正常");
						}
					}
					result.append("\n");
				}
				
				if(!TextUtils.isEmpty(plt)) {
					result.append("PLT  血小板计数  ");
					Float intPlt = Float.parseFloat(plt);
					if(intPlt < 100) {
						result.append(plt).append("  10^9L  ").append("偏低");
						result1.append("血小板偏低——1.血小板生成障碍：如再生障碍性贫血、急性白血病、急性放射病、强烈化疗后;2.血小板破坏增多：原发性血小板减少性紫癜脾功能亢进、体外循环、系统性红斑狼疮;3.血小板消耗过多：DIC、血栓性血小板减少性紫癜等;家族性血小板减少;4.巨大血小板综合征、灰色血小板综合征异常等。\n\n");
					} else if (intPlt > 300) {
						result.append(plt).append("  10^9L  ").append("偏高");
						result1.append("血小板偏高——1.骨髓增殖性疾病：原发性出血性血小板增多症、真性红细胞增多症、慢性粒细胞白血病慢性期、骨髓纤维化早期; 2.急性反应：急性大出血、急性溶血、感染、脾切除术后。\n\n");
					} else {
						result.append("         正常");
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(ly)) {
					result.append("LY  淋巴细胞计数  ");
					Float intLy = Float.parseFloat(ly);
					if(intLy < 20) {
						result.append(intLy).append("    %    ").append("偏低");
					} else if (intLy > 40) {
						result.append(intLy).append("    %    ").append("偏高");
						result1.append("淋巴细胞偏高——百日咳、传染性单核细胞增多症、腮腺炎、结核、肝炎。\n\n");
					} else {
						result.append("         正常");
					}
					result.append("\n");
				}
				mReadingLaboratory = result.toString();
				mPossibleIllness = result1.toString();
			}
		});
	}
	
}
