package com.star.android.carporange.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.star.android.carporange.AmbitusActivity;
import com.star.android.carporange.IllnessActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.MedicineActivity;
import com.star.android.carporange.R;

public class SecondFragment extends Fragment implements OnClickListener {
	
	private MainActivity mActivity;
	private View mContentView;
	
	private RelativeLayout mSymptomRlyt;
	private RelativeLayout mIllnessRlyt;
	private RelativeLayout mMedicineRlyt;
	private RelativeLayout mAmbitusRlyt;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_second,null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mContentView = (View)getView();
		mActivity = (MainActivity) getActivity();
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mSymptomRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_symptom);
		mIllnessRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_illness);
		mMedicineRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_medicine);
		mAmbitusRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_ambitus);
		
		
	}
	
	private void initEvents() {
		
		mIllnessRlyt.setOnClickListener(this);
		mMedicineRlyt.setOnClickListener(this);
		mAmbitusRlyt.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		
		case R.id.rlyt_illness: 
			Intent i = new Intent(mActivity, IllnessActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_medicine:
			i = new Intent(mActivity, MedicineActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_ambitus:
			i = new Intent(mActivity, AmbitusActivity.class);
			startActivity(i);
			break;
		}
	}
}
