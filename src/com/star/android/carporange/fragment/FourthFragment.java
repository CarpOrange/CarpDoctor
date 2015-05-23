package com.star.android.carporange.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.star.android.carporange.HealthValueActivity;
import com.star.android.carporange.LoginActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.MedicalRecordActivity;
import com.star.android.carporange.MedicineRemindActivity;
import com.star.android.carporange.MyInfoActivity;
import com.star.android.carporange.R;
import com.star.android.carporange.RegisterActivity;

public class FourthFragment extends Fragment implements OnClickListener {

	private View mContentView;
	
	private Button mLoginButton;
	private Button mRegisterButton;
	private Button mLogoutButton;
	
	private TextView mUsernameText;
	private TextView mLoginText;
	private LinearLayout mLoginLlyt;
	private RelativeLayout mUsernameRlyt;
	
	private RelativeLayout mMedicalRecordRlyt;
	private RelativeLayout mMedicineRemindRlyt;
	private RelativeLayout mHealthValueRlyt;
	private RelativeLayout mOtherRlyt;
	
	private MainActivity mActivity;
	private String mUsername;
	private String mPassword;
	private String mObjectId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_fourth, null);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		buildView();
		initEvents();
	}

	private void initEvents() {
		
		mLoginButton.setOnClickListener(this);
		mRegisterButton.setOnClickListener(this);
		mLogoutButton.setOnClickListener(this);
		
		mUsernameRlyt.setOnClickListener(this);
		mHealthValueRlyt.setOnClickListener(this);
		mMedicalRecordRlyt.setOnClickListener(this);
		mMedicineRemindRlyt.setOnClickListener(this);
	}

	private void buildView() {
		mActivity = (MainActivity) getActivity();
		
		mUsernameText = (TextView) mContentView.findViewById(R.id.tv_username);
		mLoginText = (TextView) mContentView.findViewById(R.id.tv_login);
		mLoginLlyt = (LinearLayout) mContentView.findViewById(R.id.llyt_login);
		mUsernameRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_username);
		
		mMedicalRecordRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_medical_record);
		mMedicineRemindRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_medicine_remind);
		mHealthValueRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_health_value);
		mOtherRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_other);
		
		mLoginButton = (Button) mContentView.findViewById(R.id.login_button);
		mRegisterButton = (Button) mContentView.findViewById(R.id.register_button);
		mLogoutButton = (Button) mContentView.findViewById(R.id.logout_button);
		
		
		isLoginUser();
	}

	private void isLoginUser() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(mActivity);
		mUsername = sp.getString("username", "");
		mPassword = sp.getString("password", "");
		
		if (!mUsername.equals("") && !mPassword.equals("")) {
			setVisible(true);
		} else {
			setVisible(false);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.login_button:
			Intent i = new Intent();
			i.setClass(mActivity, LoginActivity.class);
			startActivity(i);
			break;
		case R.id.register_button:
			i = new Intent(mActivity, RegisterActivity.class);
			startActivity(i);
			break;
		case R.id.logout_button:
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(mActivity);
			Editor editor = sp.edit();
			editor.putString("password", "");
			editor.commit();
			mUsername = null;
			setVisible(false);
			break;
		case R.id.rlyt_username:
			i = new Intent(mActivity, MyInfoActivity.class);
			i.putExtra("username", mUsernameText.getText().toString());
			startActivity(i);
			break;
		case R.id.rlyt_health_value:
			i = new Intent(mActivity, HealthValueActivity.class);
			i.putExtra("username", mUsernameText.getText().toString());
			startActivity(i);
			break;
		case R.id.rlyt_medical_record:
			i = new Intent(mActivity, MedicalRecordActivity.class);
			i.putExtra("username", mUsernameText.getText().toString());
			startActivity(i);
			break;
		case R.id.rlyt_medicine_remind:
			i = new Intent(mActivity,MedicineRemindActivity.class);
			i.putExtra("username", mUsernameText.getText().toString());
			startActivity(i);
		default:
			break;
		}
	}
	
	private void setVisible(boolean isVisible) {
		if(isVisible) {
			mUsernameText.setText(mUsername);
			mLoginText.setVisibility(View.GONE);
			mLoginLlyt.setVisibility(View.GONE);
			mUsernameRlyt.setVisibility(View.VISIBLE);
			mMedicalRecordRlyt.setVisibility(View.VISIBLE);
			mHealthValueRlyt.setVisibility(View.VISIBLE);
			mMedicineRemindRlyt.setVisibility(View.VISIBLE);
			mOtherRlyt.setVisibility(View.VISIBLE);
			mLogoutButton.setVisibility(View.VISIBLE);
		} else {
			mLoginText.setVisibility(View.VISIBLE);
			mLoginLlyt.setVisibility(View.VISIBLE);
			mUsernameRlyt.setVisibility(View.GONE);
			mMedicalRecordRlyt.setVisibility(View.GONE);
			mHealthValueRlyt.setVisibility(View.GONE);
			mMedicineRemindRlyt.setVisibility(View.GONE);
			mOtherRlyt.setVisibility(View.GONE);
			mLogoutButton.setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		isLoginUser();
	}
}
