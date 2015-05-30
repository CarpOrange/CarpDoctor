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
					mProText.setText("阳性");
				} else {
					mProText.setText("阴性");
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
					sex = "男";
				} else if(mFemaleRb.isChecked()) {
					sex = "女";
				}
				if(sex == null ) {
					Toast.makeText(KidneyActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
					return;
				}
				if(TextUtils.isEmpty(agee) ) {
					Toast.makeText(KidneyActivity.this, "请填写年龄", Toast.LENGTH_SHORT).show();
					return;
				}
				int age = Integer.parseInt(agee);
				result.append("\n");
				if(!TextUtils.isEmpty(bun)) {
					Float fBun = Float.parseFloat(bun);
					result.append("BUN  血尿素氮     ");
					if(fBun < 1.8) {
						result.append(fBun).append("  mmol/L  偏低");
						result1.append("BUN偏低——低蛋白饮食，肝疾病常使比值降低，此时可称为低氮质血症.\n\n");
					} else if (fBun > 6.8) {
						result.append(fBun).append("  mmol/L  偏高");
						result1.append("BUN偏高——急慢性肾炎、重症肾盂肾炎、各种原因所致的急慢性肾功能障碍，心衰、休克、大量内出血、烧伤、失水、肾上腺皮质功能减退症、前列腺肥大、慢性尿路梗阻等。\n\n");
					} else {
						result.append("           正常");
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(src)) {
					result.append("Src  血肌酐     ");
					Float fSrc = Float.parseFloat(src);
					if(age < 18) {
						if(fSrc < 26.5) {
							result.append(fSrc).append("  μmol/L  偏低");
							result1.append("血肌酐偏低——进行性肌萎缩，白血病，贫血等。\n\n");
						} else if (fSrc > 62.0) {
							result.append(fSrc).append("  μmol/L  偏高");
							result1.append("血肌酐偏高——肾衰、尿毒症、心衰、巨人症、肢端肥大症、水杨酸盐类治疗等。\n\n");
						} else {
							result.append("         正常");
						}
					} else if( sex.equals("男") ) {
						if(fSrc < 79.6) {
							result.append(fSrc).append("  μmol/L  偏低");
							result1.append("血肌酐偏低——进行性肌萎缩，白血病，贫血等。\n\n");
						} else if (fSrc > 132.6) {
							result.append(fSrc).append("  μmol/L  偏高");
							result1.append("血肌酐偏高——肾衰、尿毒症、心衰、巨人症、肢端肥大症、水杨酸盐类治疗等。\n\n");
						} else {
							result.append("         正常");
						}
					} else if( sex.equals("女")) {
						if(fSrc < 70.7) {
							result.append(fSrc).append("  μmol/L  偏低");
							result1.append("血肌酐偏低——进行性肌萎缩，白血病，贫血等。\n\n");
						} else if (fSrc > 106.1) {
							result.append(fSrc).append("  μmol/L  偏高");
							result1.append("血肌酐偏高——肾衰、尿毒症、心衰、巨人症、肢端肥大症、水杨酸盐类治疗等。\n\n");
						} else {
							result.append("         正常");
						}
					}
					result.append("\n");
				}
				if(!TextUtils.isEmpty(bua)) {
					result.append("BUA  血尿酸     ");
					Float fBua = Float.parseFloat(bua);
					if(age <= 60) {
						if( sex.equals("男") ) {
							if(fBua < 149) {
								result.append(fBua).append("  μmol/L  偏低");
							} else if (fBua > 417) {
								result.append(fBua).append("  μmol/L  偏高");
								result1.append("血尿酸偏高——痛风、急慢性白血病、多发性骨髓瘤、恶性贫血、肾衰、肝衰、红细胞增多症、妊娠反应、剧烈活动及高脂肪餐后等。\n\n");
							} else {
								result.append("         正常");
							}
						} else if( sex.equals("女")) {
							if(fBua < 89) {
								result.append(fBua).append("  μmol/L  偏低");
							} else if (fBua > 357) {
								result.append(fBua).append("  μmol/L  偏高");
								result1.append("血尿酸偏高——痛风、急慢性白血病、多发性骨髓瘤、恶性贫血、肾衰、肝衰、红细胞增多症、妊娠反应、剧烈活动及高脂肪餐后等。\n\n");
							} else {
								result.append("         正常");
							}
						}
					} else {
						if( sex.equals("男") ) {
							if(fBua < 250) {
								result.append(fBua).append("  μmol/L  偏低");
							} else if (fBua > 476) {
								result.append(fBua).append("  μmol/L  偏高");
								result1.append("血尿酸偏高——痛风、急慢性白血病、多发性骨髓瘤、恶性贫血、肾衰、肝衰、红细胞增多症、妊娠反应、剧烈活动及高脂肪餐后等。\n\n");
							} else {
								result.append("         正常");
							}
						} else if( sex.equals("女")) {
							if(fBua < 150) {
								result.append(fBua).append("  μmol/L  偏低");
							} else if (fBua > 434) {
								result.append(fBua).append("  μmol/L  偏高");
								result1.append("血尿酸偏高——痛风、急慢性白血病、多发性骨髓瘤、恶性贫血、肾衰、肝衰、红细胞增多症、妊娠反应、剧烈活动及高脂肪餐后等。\n\n");
							} else {
								result.append("         正常");
							}
						}
					}
					result.append("\n");
				}
				result.append("Pro  尿蛋白   ").append(isProChecked ? "       阳性\n " : "         阴性\n");
				if(isProChecked) {
					result1.append("尿蛋白阳性——体位性蛋白尿、运动性蛋白尿、发热、情绪激动、过冷过热的气候等。");
				}
				
				Intent i = new Intent(KidneyActivity.this, ReadingLaboratoryActivity.class);
				i.putExtra("readingLaboratory", result.toString());
				i.putExtra("possibleIllness", result1.toString());
				startActivity(i);
			}
		});
	}
	
}
