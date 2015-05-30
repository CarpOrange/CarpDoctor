package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class LaboratoryActivity extends Activity implements OnClickListener{
	
	private RelativeLayout mFirstRlyt;
	private RelativeLayout mBloodRlyt;
	private RelativeLayout mPissRlyt;
	private RelativeLayout mShitRlyt;
	
	private RelativeLayout mSecondRlyt;
	private RelativeLayout mHepatitisRlyt;
	
	private RelativeLayout mThirdRlyt;
	private RelativeLayout mKidneyRlyt;
	
	private boolean mIsFirstOpen = false;
	private boolean mIsSecondOpen = false;
	private boolean mIsThirdOpen = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_laboratory);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mFirstRlyt = (RelativeLayout) findViewById(R.id.rlyt_first);
		mBloodRlyt = (RelativeLayout) findViewById(R.id.rlyt_blood);
		mPissRlyt = (RelativeLayout) findViewById(R.id.rlyt_piss);
		mShitRlyt = (RelativeLayout) findViewById(R.id.rlyt_shit);
		
		mSecondRlyt = (RelativeLayout) findViewById(R.id.rlyt_second);
		mHepatitisRlyt = (RelativeLayout) findViewById(R.id.rlyt_hepatitis);
		
		mThirdRlyt = (RelativeLayout) findViewById(R.id.rlyt_third);
		mKidneyRlyt = (RelativeLayout) findViewById(R.id.rlyt_kidney);
		
	}

	private void initEvents() {
		
		mFirstRlyt.setOnClickListener(this);
		mBloodRlyt.setOnClickListener(this);
		mPissRlyt.setOnClickListener(this);
		mShitRlyt.setOnClickListener(this);
		
		mSecondRlyt.setOnClickListener(this);
		mHepatitisRlyt.setOnClickListener(this);
		
		mThirdRlyt.setOnClickListener(this);
		mKidneyRlyt.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		
		case R.id.rlyt_first:
			if(mIsFirstOpen) {
				mBloodRlyt.setVisibility(View.GONE);
				mPissRlyt.setVisibility(View.GONE);
				mShitRlyt.setVisibility(View.GONE);
			} else {
				mBloodRlyt.setVisibility(View.VISIBLE);
				mPissRlyt.setVisibility(View.VISIBLE);
				mShitRlyt.setVisibility(View.VISIBLE);
			}
			mIsFirstOpen = !mIsFirstOpen;
			break;
		case R.id.rlyt_second:
			if(mIsSecondOpen) {
				mHepatitisRlyt.setVisibility(View.GONE);
			} else {
				mHepatitisRlyt.setVisibility(View.VISIBLE);
			}
			mIsSecondOpen = !mIsSecondOpen;
			break;
		case R.id.rlyt_third:
			if(mIsThirdOpen) {
				mKidneyRlyt.setVisibility(View.GONE);
			} else {
				mKidneyRlyt.setVisibility(View.VISIBLE);
			}
			mIsThirdOpen = !mIsThirdOpen;
			break;
			
		case R.id.rlyt_blood:
			Intent i = new Intent(this, BloodRoutineActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_piss:
			i = new Intent(this, PissRoutineActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_shit:
			i = new Intent(this, ShitRoutineActivity.class);
			startActivity(i);
			break;
			
		case R.id.rlyt_hepatitis:
			i = new Intent(this, HbvActivity.class);
			startActivity(i);
			break;
			
		case R.id.rlyt_kidney:
			i = new Intent(this, KidneyActivity.class);
			startActivity(i);
			break;
		}
	}
}
