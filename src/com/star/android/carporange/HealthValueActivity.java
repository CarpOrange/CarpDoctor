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
					if(sex.equals("男")) {
						habitus = (h*h/10000.0*22 - w)/(h*h/10000.0*22);
					} else {
						habitus = (h*h/10000.0*20 - w)/(h*h/10000.0*20);
					}	
					if(habitus > 0.1 && habitus < 0.2) {
						mHabitusValue = "偏瘦";
					} else if(habitus > 0.2) {
						mHabitusValue = "火柴棍";
					} else if(habitus < -0.1 && habitus > -0.2) {
						mHabitusValue = "微胖";
					} else if(habitus < -0.2) {
						mHabitusValue = "胖虎";
					} else {
						mHabitusValue = "标准";
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
				mNameTextView.setText(mXingming+",你的体质是：");
				mHabitusTextView.setText(mHabitusValue);
				updateSuggestionText();
				break;
			}
			
		}

		private void updateSuggestionText() {
			String suggestion = null;
			if(mHabitusValue.equals("偏瘦")) {
				suggestion = "哎呦，有点瘦哟，多喝点牛奶";
			} else if(mHabitusValue.equals("标准")) {
				suggestion = "羡慕你，完美身材";
			} else if(mHabitusValue.equals("微胖")) {
				suggestion = "你是一个有点肉的骚年";
			} else if(mHabitusValue.equals("胖虎")) {
				suggestion = "今年的相扑比赛你报名了吗";
			} else if(mHabitusValue.equals("火柴棍")) {
				suggestion = "火柴棍也学会用手机了吗？";
			}
			mSuggestionTextView.setText(suggestion);
		}
	}
	
}
