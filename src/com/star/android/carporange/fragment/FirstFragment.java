package com.star.android.carporange.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.MainActivity;
import com.star.android.carporange.R;
import com.star.android.carporange.javabean.HealthInfo;
import com.star.android.carporange.service.CoreService;

public class FirstFragment extends Fragment {
	
	private static final int UPDATE_UI = 1;
	private MainActivity mActivity; 
	private View mContentView;
	private ViewPager mViewPager;
	private List<HealthInfo> mHealthInfo;
	private List<View> mList = new ArrayList<View>();
	private CoreService.LoadBinder mLoadBinder;
	private LayoutInflater mLayoutInflater;
	
	private TextView mTextViews[];
	private Handler mHandler = new Handler() {
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE_UI:
				for(int i = 0; i < 3; i ++) {
					HealthInfo h = mHealthInfo.get(i);
					View view = mLayoutInflater.inflate(R.layout.item_viewpager_fragment_first, null);
					ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
					TextView textView = (TextView) view.findViewById(R.id.textView);
					h.getBmobFile().loadImage(mActivity, imageView);
					textView.setText(h.getMainTitle());
					mList.add(view); 
				}
				mViewPager.setAdapter(new PagerAdapter() {
					
					@Override
					public boolean isViewFromObject(View arg0, Object arg1) {
						return arg0 == arg1;
					}
					
					@Override
					public int getCount() {
						return mList.size();
					}
					
					@Override
					public Object instantiateItem(ViewGroup container,
							int position) {
						container.addView(mList.get(position));
						return mList.get(position);
					}
					
					@Override
					public void destroyItem(ViewGroup container, int position,
							Object object) {
						container.removeView(mList.get(position));
					}
				});
				for(int i = 3; i < 17; i++) {
					
				}
				break;
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLayoutInflater = inflater;
		return inflater.inflate(R.layout.fragment_first,null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = (MainActivity) getActivity();
		mContentView = getView();
		mLoadBinder = mActivity.mLoadBinder;
		buildView();
	}

	private void buildView() {
		
		mViewPager = (ViewPager) mContentView.findViewById(R.id.viewPager);
		new MyThread().start();
//		BmobQuery<HealthInfo> query = new BmobQuery<HealthInfo>();
//		query.addWhereGreaterThan("num", -1);
//		query.addWhereLessThan("num", 18);
//		query.order("num");
//		query.findObjects(mActivity, new FindListener<HealthInfo>() {
//			
//			@Override
//			public void onSuccess(List<HealthInfo> healthInfo) {
//				mHealthInfo = healthInfo;
//				for(HealthInfo h:healthInfo) {
//					View view = mLayoutInflater.inflate(R.layout.item_viewpager_fragment_first, null);
//					ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//					TextView textView = (TextView) view.findViewById(R.id.textView);
//					h.getBmobFile().loadImage(mActivity, imageView);
//					textView.setText(h.getMainTitle());
//					mList.add(view); 
//				}
//				mViewPager.setAdapter(new PagerAdapter() {
//					
//					@Override
//					public boolean isViewFromObject(View arg0, Object arg1) {
//						return arg0 == arg1;
//					}
//					
//					@Override
//					public int getCount() {
//						return mList.size();
//					}
//					
//					@Override
//					public Object instantiateItem(ViewGroup container,
//							int position) {
//						container.addView(mList.get(position));
//						return mList.get(position);
//					}
//					
//					@Override
//					public void destroyItem(ViewGroup container, int position,
//							Object object) {
//						container.removeView(mList.get(position));
//					}
//				});
//			}
//			
//			@Override
//			public void onError(int arg0, String arg1) {
//				Log.i("bb",	"qq");
//			}
//		});
		
	}
	
	class MyThread extends Thread {
		@Override
		public void run() {
			super.run();
			BmobQuery<HealthInfo> query = new BmobQuery<HealthInfo>();
			query.addWhereGreaterThan("num", -1);
			query.addWhereLessThan("num", 18);
			query.order("num");
			query.findObjects(mActivity, new FindListener<HealthInfo>() {
				
				@Override
				public void onSuccess(List<HealthInfo> healthInfo) {
					mHealthInfo = healthInfo;
					Message message = new Message();
					message.what = UPDATE_UI;
					mHandler.sendMessage(message);
				}
				
				@Override
				public void onError(int arg0, String arg1) {
				}
			});
		}
	}
}
