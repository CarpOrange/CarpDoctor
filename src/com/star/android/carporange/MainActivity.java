package com.star.android.carporange;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.bmob.v3.Bmob;

import com.baidu.mapapi.SDKInitializer;
import com.star.android.carporange.fragment.FirstFragment;
import com.star.android.carporange.fragment.FourthFragment;
import com.star.android.carporange.fragment.SecondFragment;
import com.star.android.carporange.fragment.ThirdFragment;
import com.star.android.carporange.javabean.HealthInfo;
import com.star.android.carporange.service.CoreService;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private List<Fragment> mFragmentList;
	private int mCurrentIndex = 0;
	private LinearLayout[] mButtons;
	private ViewPager mViewPager;
	public CoreService.LoadBinder mLoadBinder;
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			mLoadBinder = (CoreService.LoadBinder) arg1;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bmob.initialize(this, "41c59442c6ffdc8b30fd071d273fdd38");
		SDKInitializer.initialize(getApplicationContext());
		Intent bindIntent = new Intent(this, CoreService.class);
		bindService(bindIntent, conn, BIND_AUTO_CREATE);
		buildView();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchMenu = (MenuItem) menu.findItem(R.id.action_search);
		MenuItem settingMenu = (MenuItem) menu.findItem(R.id.action_settings);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

	}

	public class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			mButtons[mCurrentIndex].setBackgroundColor(Color
					.parseColor("#353535"));
			mCurrentIndex = arg0;
			mButtons[arg0].setBackgroundColor(Color.parseColor("#000000"));
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.navigator_layout:
			
			mViewPager.setCurrentItem(0);
			break;
			
		case R.id.navigator_layout1:

			mViewPager.setCurrentItem(1);
			break;
			
		case R.id.navigator_layout2:

			mViewPager.setCurrentItem(2);
			break;
			
		case R.id.navigator_layout3:

			mViewPager.setCurrentItem(3);
			break;
			
		default:
			
			break;
		}
	}
	
	private void buildView() {
		
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(new FirstFragment());
		mFragmentList.add(new SecondFragment());
		mFragmentList.add(new ThirdFragment());
		mFragmentList.add(new FourthFragment());
		mViewPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		
		mViewPager.setOffscreenPageLimit(4);
		mButtons = new LinearLayout[] {
				(LinearLayout) findViewById(R.id.navigator_layout),
				(LinearLayout) findViewById(R.id.navigator_layout1),
				(LinearLayout) findViewById(R.id.navigator_layout2),
				(LinearLayout) findViewById(R.id.navigator_layout3) };
		
		for(LinearLayout l: mButtons) {
			l.setOnClickListener(this);
		}
		
	}
	
}
