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
				
				result.append(isBsagChecked ? "HBsAg  乙肝表面抗原             阳性\n " : "HBsAg  乙肝表面膜抗原             阴性\n")
					.append(isBsabChecked ? "HBsAb  乙肝表面抗体             阳性\n " : "HBsAb  乙肝表面膜抗体             阴性\n")
					.append(isBeagChecked ? "HBeAg  乙肝e抗原             阳性 \n" : "HBeAg  乙肝e抗原             阴性\n")
					.append(isBeabChecked ? "HBeAb  乙肝e膜抗体             阳性\n " : "HBeAb  乙肝e抗体             阴性\n")
					.append(isBcabChecked ? "HBcAb  乙肝核心抗体             阳性 " : "HBcAb  乙肝核心抗体             阴性");
				if(isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("急性病毒感染的潜伏期后期。");
				} else if(isBsagChecked && !isBsabChecked && isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("俗称乙肝大三阳，说明是急、慢性乙肝，传染性相对较强。");
				} else if(isBsagChecked && !isBsabChecked && !isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("俗称乙肝小三阳，说明是急、慢性乙肝，传染性相对较弱。");
				} else if(isBsagChecked && !isBsabChecked && isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("急性乙肝的早期。");
				} else if(isBsagChecked && !isBsabChecked && isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("急性乙肝感染趋向恢复，或者为慢性乙肝病毒携带者。");
				} else if(isBsagChecked && !isBsabChecked && !isBeagChecked && isBeabChecked && !isBcabChecked) {
					result1.append("慢性乙肝表面抗原携带者易转阴，或者是急性感染趋向恢复。");
				} else if(isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("急、慢性乙肝，即：①急性HBV感染;②慢性HBsAg携带者;③传染性弱。");
				} else if(!isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("①既往感染未能测出抗-HBs；②恢复期HBsAg已消，抗-HBs尚未出现；③无症状HBsAg携带者。");
				} else if(!isBsagChecked && isBsabChecked && !isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("乙肝的恢复期，已有免疫力。");
				} else if(!isBsagChecked && isBsabChecked && !isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("①曾经注射过乙肝疫苗并产生了抗体，有免疫力；②曾经有过乙肝病毒的感染，并且有一定的免疫力；③假阳性。");
				} else if(!isBsagChecked && isBsabChecked && !isBeagChecked && !isBeabChecked && isBcabChecked) {
					result1.append("接种了乙肝疫苗以后，或是乙肝病毒感染后已康复，已有免疫力。");
				} else if(!isBsagChecked && !isBsabChecked && !isBeagChecked && isBeabChecked && isBcabChecked) {
					result1.append("急性乙肝病毒感染的恢复期，或曾经感染过病毒。");
				} else if(!isBsagChecked && !isBsabChecked && !isBeagChecked && !isBeabChecked && !isBcabChecked) {
					result1.append("未感染过HBV，但目前没有保护性抗体。");
				} else {
					result1.append("您的症状较为少见，建议咨询医生。");
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
				mBsagText.setText("阳性");
			} else {
				mBsagText.setText("阴性");
			}
			break;
		case R.id.switchbutton_hbsab:
			if(isChecked) {
				mBsabText.setText("阳性");
			} else {
				mBsabText.setText("阴性");
			}
			break;
		case R.id.switchbutton_hbeag:
			if(isChecked) {
				mBeagText.setText("阳性");
			} else {
				mBeagText.setText("阴性");
			}
			break;
		case R.id.switchbutton_hbeab:
			if(isChecked) {
				mBeabText.setText("阳性");
			} else {
				mBeabText.setText("阴性");
			}
			break;
		case R.id.switchbutton_hbcab:
			if(isChecked) {
				mBcabText.setText("阳性");
			} else {
				mBcabText.setText("阴性");
			}
			break;
		}
		
	}
}
