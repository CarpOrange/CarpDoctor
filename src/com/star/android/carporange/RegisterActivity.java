package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

import com.star.android.carporange.javabean.User;

public class RegisterActivity extends Activity {
	
	private EditText mUsernameText;
	private EditText mPasswordText;
	private EditText mRepeatPasswordText;
	private Button mRegisterButton;
	private String mUsername;
	private String mPassword;
	private String mRepeatPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		buildView();
	}

	private void buildView() {
		mUsernameText = (EditText) findViewById(R.id.username);
		mPasswordText = (EditText) findViewById(R.id.password);
		mRepeatPasswordText = (EditText) findViewById(R.id.repeat_password);
		mRegisterButton = (Button) findViewById(R.id.register_button);
		mRegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mUsername = mUsernameText.getText().toString();
				mPassword = mPasswordText.getText().toString();
				mRepeatPassword = mRepeatPasswordText.getText().toString();
				if(!TextUtils.isEmpty(mUsername) && !TextUtils.isEmpty(mPassword) && !TextUtils.isEmpty(mRepeatPassword)) {
					if(mRepeatPassword.equals(mPassword)) {
						User u = new User();
						u.setUsername(mUsername);
						u.setPassword(mPassword);
						u.save(RegisterActivity.this,new SaveListener() {
							
							@Override
							public void onSuccess() {
								Toast.makeText(RegisterActivity.this, "注册成功^_^", Toast.LENGTH_LONG).show();
								SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
								Editor editor = sp.edit();
								editor.putString("username", mUsername);
								editor.putString("password", mPassword);
								editor.commit();
								Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
								RegisterActivity.this.setResult(2, i);
								RegisterActivity.this.finish();
							}
							
							@Override
							public void onFailure(int arg0, String arg1) {
								Toast.makeText(RegisterActivity.this, "注册失败啦/(ㄒoㄒ)/~~", Toast.LENGTH_LONG).show();
							}
						});
					} else {
						Toast.makeText(RegisterActivity.this, "两次输入的密码不一致哦~", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "输入框不能为空", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
