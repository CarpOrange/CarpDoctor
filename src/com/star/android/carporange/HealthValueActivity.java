package com.star.android.carporange;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.javabean.User;

public class HealthValueActivity extends Activity {
	
	private static final int UPDATE_UI = 1;
	
	private TextView mNameTextView;
	private TextView mHabitusTextView;
	private TextView mSuggestionTextView;
	
	private String mHabitusValue ;
	private String mXingming;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthvalue);
		buildView();
		mHandler = new MyHandler();
	}

	private void buildView() {
		
		mNameTextView = (TextView) findViewById(R.id.tv_name);
		mHabitusTextView = (TextView) findViewById(R.id.tv_habitus);
		mSuggestionTextView = (TextView) findViewById(R.id.tv_suggestion);
		
		getHabitus();
		
	}
	
	private void getHabitus() {
		Intent i = getIntent();
		String userName = i.getStringExtra("username");
		BmobQuery<User> query = new BmobQuery<User>("User");
		query.addWhereEqualTo("username", userName);
		query.findObjects(HealthValueActivity.this, new FindListener<User>() {
			
			@Override
			public void onSuccess(List<User> list) {
				double habitus;
				mXingming = list.get(0).getXingming();
				Integer height = list.get(0).getHeight();
				Integer weight = list.get(0).getWeight();
				String sex = list.get(0).getSex();
				if(height != null && weight != null && !TextUtils.isEmpty(sex)) {
					int h = (int) height;
					int w = (int) weight;
					if(sex.equals("��")) {
						habitus = (h*h/10000.0*22 - w)/(h*h/10000.0*22);
					} else {
						habitus = (h*h/10000.0*20 - w)/(h*h/10000.0*20);
					}	
					if(habitus > 0.1 && habitus < 0.2) {
						mHabitusValue = "ƫ��";
					} else if(habitus > 0.2) {
						mHabitusValue = "����";
					} else if(habitus < -0.1 && habitus > -0.2) {
						mHabitusValue = "΢��";
					} else if(habitus < -0.2) {
						mHabitusValue = "�ֻ�";
					} else {
						mHabitusValue = "��׼";
					}
					
					mHandler.sendEmptyMessage(UPDATE_UI);
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				
			}
		});
	}

	class MyHandler extends Handler {
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_UI:
				mNameTextView.setText(mXingming+",��������ǣ�");
				mHabitusTextView.setText(mHabitusValue);
				updateSuggestionText();
				break;
			}
			
		}

		private void updateSuggestionText() {
			String suggestion = null;
			if(mHabitusValue.equals("ƫ��")) {
				suggestion = "���ϣ��е���Ӵ����ȵ�ţ��";
			} else if(mHabitusValue.equals("��׼")) {
				suggestion = "��Ľ�㣬�������";
			} else if(mHabitusValue.equals("΢��")) {
				suggestion = "����һ���е����ɧ��";
			} else if(mHabitusValue.equals("�ֻ�")) {
				suggestion = "��������˱����㱨������";
			} else if(mHabitusValue.equals("����")) {
				suggestion = "����Ҳѧ�����ֻ�����";
			}
			mSuggestionTextView.setText(suggestion);
		}
	}
	
}
