package com.star.android.carporange.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.HealthInfoActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.R;
import com.star.android.carporange.javabean.HealthInfo;
import com.star.android.carporange.service.CoreService;
import com.star.android.carporange.widget.ChildViewPager;
import com.star.android.carporange.widget.ListViewForScrollView;

public class FirstFragment extends Fragment {
	
	private static final int UPDATE_UI = 1;
	private static final int SCROLL_WHAT = 2;
	
	private int mCurrentIndex = 1;
	
	private MainActivity mActivity; 
	private View mContentView;
	private ChildViewPager mViewPager;
	private ListViewForScrollView mListView;
	private ScrollView mScrollView;
	
	private List<HealthInfo> mHealthInfo;
	private List<View> mList = new ArrayList<View>();
	private CoreService.LoadBinder mLoadBinder;
	private LayoutInflater mLayoutInflater;
	
	private TextView mTodayNewsTextViews[];
	private ImageView mTodayNewsImageViews[];
	
	private ImageView mDotViews[];
	private Bitmap mBitmaps[];
	private Handler mHandler = new MyHandler();
	
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
		initEvents();
	}
	
	@Override
	public void onDestroyView() {
		Log.i("FirstFragment", "onDestroyView()");
		super.onDestroyView();
	}
	private void initEvents() {
		
		mCurrentIndex = 1;
		final Drawable dot1 = getResources().getDrawable(R.drawable.dot_s);
		final Drawable dot2 = getResources().getDrawable(R.drawable.dot_n);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int index) {
				mDotViews[index % 3].setImageDrawable(dot1);
				mDotViews[mCurrentIndex % 3].setImageDrawable(dot2);
				mCurrentIndex = index;
				sendScrollMessage();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});		
		
	}

	private void buildView() {
		mListView = (ListViewForScrollView) mContentView.findViewById(R.id.listView);
		
		mScrollView = (ScrollView) mContentView.findViewById(R.id.scrollView);
		
		mTodayNewsTextViews = new TextView[]{ (TextView) mContentView.findViewById(R.id.tv_today_news), 
				(TextView) mContentView.findViewById(R.id.tv_today_news1), 
				(TextView) mContentView.findViewById(R.id.tv_today_news2),
				(TextView) mContentView.findViewById(R.id.tv_today_news3)
				};
		mTodayNewsImageViews = new ImageView[]{ (ImageView) mContentView.findViewById(R.id.iv_today_news),
				(ImageView) mContentView.findViewById(R.id.iv_today_news1),
				(ImageView) mContentView.findViewById(R.id.iv_today_news2),
				(ImageView) mContentView.findViewById(R.id.iv_today_news3)
				};
		mViewPager = (ChildViewPager) mContentView.findViewById(R.id.viewPager);
		mDotViews = new ImageView[]{ (ImageView)mContentView.findViewById(R.id.dot_1),
				(ImageView)mContentView.findViewById(R.id.dot_2), (ImageView)mContentView.findViewById(R.id.dot_3)};
		
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
	
	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case SCROLL_WHAT:

				scrollOnce();
				break;

			case UPDATE_UI:
				for (int i = 0; i < 3; i++) {
					final HealthInfo h = mHealthInfo.get(i);
					View view = mLayoutInflater.inflate(
							R.layout.item_viewpager_fragment_first, null);
					ImageView imageView = (ImageView) view
							.findViewById(R.id.imageView);
					TextView textView = (TextView) view
							.findViewById(R.id.textView);
					h.getImage().loadImage(mActivity, imageView);
					textView.setText(h.getMainTitle());
					view.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent i = new Intent(mActivity,
									HealthInfoActivity.class);
							i.putExtra("mainTitle", h.getMainTitle());
							i.putExtra("content", h.getContent());
							startActivity(i);
						}
					});
					mList.add(view);
				}
				mViewPager.setAdapter(new PagerAdapter() {

					@Override
					public boolean isViewFromObject(View arg0, Object arg1) {
						return arg0 == arg1;
					}

					@Override
					public int getCount() {
						return Integer.MAX_VALUE;
					}

					@Override
					public Object instantiateItem(ViewGroup container,
							int position) {
						try {
							((ChildViewPager) container).addView(mList
									.get(position % 3));
						} catch (Exception e) {
						}
						return mList.get(position % 3);
					}

					@Override
					public void destroyItem(ViewGroup container, int position,
							Object object) {
						// ((ChildViewPager)
						// container).removeView(mList.get(position%mList.size()));
					}
				});
				mViewPager.setCurrentItem(300);

				mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, 1000 * 4);
				for (int i = 3; i < 7; i++) {
					final HealthInfo h = mHealthInfo.get(i);
					h.getImage().loadImage(mActivity,
							mTodayNewsImageViews[i - 3], 300, 300);
					mTodayNewsTextViews[i - 3].setText(h.getMainTitle());
					mTodayNewsImageViews[i - 3]
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									Intent i = new Intent(mActivity,
											HealthInfoActivity.class);
									i.putExtra("mainTitle", h.getMainTitle());
									i.putExtra("content", h.getContent());
									startActivity(i);
								}
							});
				}
				MyListViewApdater listViewAdapter = new MyListViewApdater(
						mActivity, R.layout.item_list_fragment_first,
						mHealthInfo);
				mListView.setAdapter(listViewAdapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent i = new Intent(mActivity,
								HealthInfoActivity.class);
						i.putExtra("mainTitle", mHealthInfo.get(position + 7)
								.getMainTitle());
						i.putExtra("content", mHealthInfo.get(position + 7)
								.getContent());
						startActivity(i);
					}

				});
				break;
			}
		}

	}

	private void sendScrollMessage() {
		mHandler.removeMessages(SCROLL_WHAT);
		mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, 1000 * 4);
	}

	private void scrollOnce() {
		mViewPager.setCurrentItem(mCurrentIndex + 1);
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

	class MyListViewApdater extends ArrayAdapter<HealthInfo> {

		private int resourceId;

		public MyListViewApdater(Context context, int resourceId,
				List<HealthInfo> objects) {
			super(context, resourceId, objects);
			this.resourceId = resourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HealthInfo h = getItem(position + 7);
			View view = LayoutInflater.from(mActivity)
					.inflate(resourceId, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
			TextView textView = (TextView) view.findViewById(R.id.tv_mainTitle);
			textView.setText(h.getMainTitle());
			h.getImage().loadImage(mActivity, imageView, 200, 150);
			return view;
		}

		@Override
		public int getCount() {
			return 10;
		}

	}
	
	@Override
	public void onDestroy() {
		for(ImageView iv :mTodayNewsImageViews) {
			if(iv.getDrawable() != null) {
				
			}
		}

		super.onDestroy();
	}
}
