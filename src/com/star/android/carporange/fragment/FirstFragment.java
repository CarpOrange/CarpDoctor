package com.star.android.carporange.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
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
	
	private MainActivity mActivity; 
	private View mContentView;
	private ViewPager mViewPager;
	private List<HealthInfo> mHealthInfo;
	private List<View> mList = new ArrayList<View>();
	private CoreService.LoadBinder mLoadBinder;
	private LayoutInflater mLayoutInflater;
	
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
		new MyAsyncTask().execute();
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
	
	class MyAsyncTask extends AsyncTask<Void, Integer, List<HealthInfo>> {
		
		@Override
		protected List<HealthInfo> doInBackground(Void... arg0) {
			BmobQuery<HealthInfo> query = new BmobQuery<HealthInfo>();
			query.addWhereGreaterThan("num", -1);
			query.addWhereLessThan("num", 18);
			query.order("num");
			query.findObjects(mActivity, new FindListener<HealthInfo>() {
				
				@Override
				public void onSuccess(List<HealthInfo> healthInfo) {
					Log.i("bb",	"qq132");
					mHealthInfo = healthInfo;
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					Log.i("bb",	"qq123");
				}
			});
			return mHealthInfo;
		}
		
		@Override
		protected void onPostExecute(List<HealthInfo> result) {
			super.onPostExecute(result);
			for(HealthInfo h:result) {
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
		}
	}
}
