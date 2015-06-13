package com.star.android.carporange.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.star.android.carporange.AmbitusActivity;
import com.star.android.carporange.IllnessActivity;
import com.star.android.carporange.LaboratoryActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.MedicineActivity;
import com.star.android.carporange.MyWebViewActivity;
import com.star.android.carporange.R;

public class SecondFragment extends Fragment implements OnClickListener {
	
	private MainActivity mActivity;
	private View mContentView;
	
	private EditText mSearchET;
	private Button mSearchButton;
	
	private RelativeLayout mLaboratoryRlyt;
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
		
		mSearchET = (EditText) mContentView.findViewById(R.id.et_search);
		mSearchButton = (Button) mContentView.findViewById(R.id.button_search);
		
		mLaboratoryRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_laboratory);
		mIllnessRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_illness);
		mMedicineRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_medicine);
		mAmbitusRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_ambitus);
		
	}
	
	private void initEvents() {
		
		mSearchButton.setOnClickListener(this);
		
		mLaboratoryRlyt.setOnClickListener(this);
		mIllnessRlyt.setOnClickListener(this);
		mMedicineRlyt.setOnClickListener(this);
		mAmbitusRlyt.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		
		case R.id.button_search:
			Editable illnessName = mSearchET.getText();
			if(TextUtils.isEmpty(illnessName))
				return;
			Intent i = new Intent (mActivity, MyWebViewActivity.class);
			i.putExtra("keyword", illnessName.toString());
			startActivity(i);
			break;
		case R.id.rlyt_laboratory:
			i = new Intent(mActivity, LaboratoryActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_illness: 
			i = new Intent(mActivity, IllnessActivity.class);
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
