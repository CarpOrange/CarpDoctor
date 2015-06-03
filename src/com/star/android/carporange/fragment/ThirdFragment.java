package com.star.android.carporange.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.star.android.carporange.BPListActivity;
import com.star.android.carporange.GluListActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.R;
import com.star.android.carporange.WeightListActivity;

public class ThirdFragment extends Fragment implements OnClickListener{
	
	private MainActivity mActivity;
	private View mContentView;
	
	private RelativeLayout mBPRlyt;
	private RelativeLayout mHRRlyt;
	private RelativeLayout mGluRlyt;
	private RelativeLayout mWeightRlyt;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_third,null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mActivity = (MainActivity) getActivity();
		
		mContentView = getView();
		
		mBPRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_bp);
		mHRRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_hr);
		mGluRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_glu);
		mWeightRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_weight);
		
	}

	private void initEvents() {
		
		mBPRlyt.setOnClickListener(this);
		mHRRlyt.setOnClickListener(this);
		mGluRlyt.setOnClickListener(this);
		mWeightRlyt.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.rlyt_bp:
			Intent i = new Intent(mActivity, BPListActivity.class);
			i.putExtra("action", "bp");
			startActivity(i);
			break;
		case R.id.rlyt_hr:
			i = new Intent(mActivity, BPListActivity.class);
			i.putExtra("action", "hr");
			startActivity(i);
			break;
		case R.id.rlyt_glu:
			i = new Intent(mActivity, GluListActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_weight:
			i = new Intent(mActivity, WeightListActivity.class);
			startActivity(i);
			break;
		}
	}
}
