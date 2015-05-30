package com.star.android.carporange;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.javabean.User;

public class LoginActivity extends Activity implements OnClickListener {

	private TextView mNameText;
	private TextView mPasswordText;
	private TextView mRegisterText;
	private Button mLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		buildView();
	}

	private void buildView() {
		mNameText = (TextView) findViewById(R.id.userName);
		mPasswordText = (TextView) findViewById(R.id.userPassword);
		mLoginButton = (Button) findViewById(R.id.login_button);
		mLoginButton.setOnClickListener(this);
	}
	 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 2) {
			finish();
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login_button:
			final String name = mNameText.getText().toString();
			final String password = mPasswordText.getText().toString();
			Log.i("bb", name + "--" + password);
			BmobQuery<User> query = new BmobQuery<User>();
			query.addWhereEqualTo("username", name);
			query.addWhereEqualTo("password", password);
			query.findObjects(this, new FindListener<User>() {

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(LoginActivity.this, "��¼ʧ�ܣ������Դ�������",
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onSuccess(List<User> list) {
					if (list.size() > 0) {
						SharedPreferences sp = PreferenceManager
								.getDefaultSharedPreferences(LoginActivity.this);
						Editor editor = sp.edit();
						editor.putString("username", name);
						editor.putString("password", password);
						editor.commit();
						finish();
					} else {
						Toast.makeText(LoginActivity.this, "�����û�������������",
								Toast.LENGTH_LONG).show();
					}
				}
			});
			break;

		default:
			break;
		}
	}
}
