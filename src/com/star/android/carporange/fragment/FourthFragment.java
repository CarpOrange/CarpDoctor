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

import com.star.android.carporange.LoginActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.R;

public class FourthFragment extends Fragment implements OnClickListener {

	private View mContentView;
	private Button mLoginButton;
	private Button mLogoutButton;
	private MainActivity mActivity;
	private String mUsername;
	private String mPassword;

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
	}

	private void buildView() {
		mActivity = (MainActivity) getActivity();
		mLoginButton = (Button) mContentView.findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(this);
		mLogoutButton = (Button) mContentView.findViewById(R.id.logout_button);
		mLogoutButton.setOnClickListener(this);
		isLoginUser();
	}

	private void isLoginUser() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(mActivity);
		mUsername = sp.getString("username", "");
		mPassword = sp.getString("password", "");
		Log.i("bb",mUsername+"-"+mPassword);
		if (!mUsername.equals("") && !mPassword.equals("")) {
			Log.i("xx","xx");
			mLoginButton.setVisibility(View.GONE);
			mLogoutButton.setVisibility(View.VISIBLE);
		} else {
			mLoginButton.setVisibility(View.VISIBLE);
			mLogoutButton.setVisibility(View.GONE);
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

		case R.id.logout_button:
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(mActivity);
			Editor editor = sp.edit();
			editor.putString("password", "");
			editor.commit();
			mLoginButton.setVisibility(View.VISIBLE);
			mLogoutButton.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		isLoginUser();
	}
}
