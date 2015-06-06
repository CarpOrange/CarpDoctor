package com.star.android.carporange.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.star.android.carporange.BPListActivity;
import com.star.android.carporange.GluListActivity;
import com.star.android.carporange.MainActivity;
import com.star.android.carporange.R;
import com.star.android.carporange.WeightListActivity;
import com.star.android.carporange.utils.DayUtil;
import com.star.android.carporange.utils.MyDatabaseHelper;

public class ThirdFragment extends Fragment implements OnClickListener{
	
	private MainActivity mActivity;
	private View mContentView;
	
	private RelativeLayout mBPRlyt;
	private RelativeLayout mHRRlyt;
	private RelativeLayout mGluRlyt;
	private RelativeLayout mWeightRlyt;
	
	private LineChart mBPChart;
	private LineChart mHRChart;
	private LineChart mGluChart;
	private LineChart mWeightChart;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_third,null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mActivity = (MainActivity) getActivity();
		
		mContentView = getView();
		
		mBPRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_bp);
		mHRRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_hr);
		mGluRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_glu);
		mWeightRlyt = (RelativeLayout) mContentView.findViewById(R.id.rlyt_weight);
		
		mBPChart = (LineChart) mContentView.findViewById(R.id.chart_bp);
		mHRChart = (LineChart) mContentView.findViewById(R.id.chart_hr);
		mGluChart = (LineChart) mContentView.findViewById(R.id.chart_glu);
		mWeightChart = (LineChart)mContentView.findViewById(R.id.chart_weight);
		
		initAllChart();
        
	}

	private void initAllChart() {
		
		
		LineChart[] charts = new LineChart[] {mBPChart, mHRChart, mGluChart, mWeightChart};
		for(LineChart mChart: charts) {
			
			mChart.setDescription("");
	        mChart.setNoDataTextDescription("当月没有数据");

	        // enable value highlighting
	        mChart.setHighlightEnabled(true);

	        // enable touch gestures
	        mChart.setTouchEnabled(false);
	        
	        mChart.setDragDecelerationFrictionCoef(0.9f);

	        // enable scaling and dragging
	        mChart.setDragEnabled(true);
	        mChart.setScaleEnabled(true);
	        mChart.setDrawGridBackground(false);
	        mChart.setHighlightPerDragEnabled(true);

	        // if disabled, scaling can be done on x- and y-axis separately
	        mChart.setPinchZoom(true);

	        // set an alternative background color
	        mChart.setBackgroundColor(Color.LTGRAY);

	        mChart.animateX(2500);

	        Typeface tf = Typeface.createFromAsset(mActivity.getAssets(), "OpenSans-Regular.ttf");

	        // get the legend (only possible after setting data)
	        Legend l = mChart.getLegend();

	        // modify the legend ...
	        // l.setPosition(LegendPosition.LEFT_OF_CHART);
	        l.setForm(LegendForm.LINE);
	        l.setTypeface(tf);
	        l.setTextSize(11f);
	        l.setTextColor(Color.WHITE);
	        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
//	        l.setYOffset(11f);

	        XAxis xAxis = mChart.getXAxis();
	        xAxis.setTypeface(tf);
	        xAxis.setTextSize(12f);
	        xAxis.setTextColor(Color.WHITE);
	        xAxis.setDrawGridLines(false);
	        xAxis.setDrawAxisLine(false);
	        xAxis.setSpaceBetweenLabels(1);

	        YAxis leftAxis = mChart.getAxisLeft();
	        leftAxis.setTypeface(tf);
	        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
	        leftAxis.setDrawGridLines(true);
	        
	        YAxis rightAxis = mChart.getAxisRight();
	        rightAxis.setTypeface(tf);
	        rightAxis.setTextColor(Color.RED);
	        rightAxis.setStartAtZero(false);
	        rightAxis.setAxisMinValue(0);
	        rightAxis.setDrawGridLines(false);
	        
		}
		
		setBPData();
		setHRData();
		setGluData();
		setWeightData();
		
	}
	
	private void initEvents() {
		
		mBPRlyt.setOnClickListener(this);
		mHRRlyt.setOnClickListener(this);
		mGluRlyt.setOnClickListener(this);
		mWeightRlyt.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.rlyt_bp:
			Intent i = new Intent(mActivity, BPListActivity.class);
			i.putExtra("action", "bp");
			startActivity(i);
			break;
		case R.id.rlyt_hr:
			i = new Intent(mActivity, BPListActivity.class);
			i.putExtra("action", "hr");
			startActivity(i);
			break;
		case R.id.rlyt_glu:
			i = new Intent(mActivity, GluListActivity.class);
			startActivity(i);
			break;
		case R.id.rlyt_weight:
			i = new Intent(mActivity, WeightListActivity.class);
			startActivity(i);
			break;
		}
	}
	
	private void setBPData() {
		
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(mActivity, "CarpOrange",
				null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (!dbHelper.tableIsExist("BP", db)) {
			return;
		}
		
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder("").append(c.get(Calendar.YEAR)).append("年")
				.append(c.get(Calendar.MONTH)).append("月%");
		Cursor cursor = db.rawQuery("select sbp,dbp,date from BP where date like '" + sb.toString() + "%'", null);
		
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		
		int day = DayUtil.getDaysByYearMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
		for (int i = 1; i <= day; i++) {
            xVals.add((i) + "");
        } 
		String[] sbps = new String[day];
		String[] dbps = new String[day];
		if(cursor.moveToFirst()) {
			do {
				String sbp = cursor.getString(0);
				String dbp = cursor.getString(1);
				int theday = Integer.parseInt(cursor.getString(2).split("月|日")[1]);
				
				sbps[theday] = sbp;
				dbps[theday] = dbp;
				
			} while (cursor.moveToNext());
		}
		
		float max1 = 0;
		float max2 = 0;
		for (int i = 0; i < day; i++) {
			if(!TextUtils.isEmpty(sbps[i])) {
				yVals1.add(new Entry(Float.parseFloat(sbps[i]), i+1));
				if(Float.parseFloat(sbps[i]) > max1) {
					max1 = Float.parseFloat(sbps[i]);
				}
			}
			
			if(!TextUtils.isEmpty(dbps[i])) {
				yVals2.add(new Entry(Float.parseFloat(dbps[i]), i));
				if(Float.parseFloat(dbps[i]) > max2) {
					max2 = Float.parseFloat(dbps[i]);
				}
			}
		}
		
		YAxis leftAxis = mBPChart.getAxisLeft();
		leftAxis.setAxisMaxValue(max1+30);
		YAxis rightAxis = mBPChart.getAxisRight();
		rightAxis.setAxisMaxValue(max2+30); 
		 
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "收缩压");
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleSize(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
//        set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(yVals2, "舒张压");
        set2.setAxisDependency(AxisDependency.RIGHT);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.WHITE);
        set2.setLineWidth(2f);
        set2.setCircleSize(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mBPChart.setData(data);
    }
	
	private void setHRData() {
		
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(mActivity, "CarpOrange",
				null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (!dbHelper.tableIsExist("BP", db)) {
			return;
		}
		
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder("").append(c.get(Calendar.YEAR)).append("年")
				.append(c.get(Calendar.MONTH)).append("月%");
		Cursor cursor = db.rawQuery("select hr,date from BP where date like '" + sb.toString() + "%'", null);
		
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		
		int day = DayUtil.getDaysByYearMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
		for (int i = 1; i <= day; i++) {
            xVals.add((i) + "");
        } 
		String[] hrs = new String[day];
		if(cursor.moveToFirst()) {
			do {
				String hr = cursor.getString(0);
				int theday = Integer.parseInt(cursor.getString(1).split("月|日")[1]);
				
				hrs[theday] = hr;
				
			} while (cursor.moveToNext());
		}
		
		float max1 = 0;
		for (int i = 0; i < day; i++) {
			if(!TextUtils.isEmpty(hrs[i])) {
				yVals1.add(new Entry(Float.parseFloat(hrs[i]), i+1));
				if(Float.parseFloat(hrs[i]) > max1) {
					max1 = Float.parseFloat(hrs[i]);
				}
			}
			
		}
		
		YAxis leftAxis = mHRChart.getAxisLeft();
		leftAxis.setAxisMaxValue(max1+30);
		 
		YAxis rightAxis = mHRChart.getAxisRight();
		rightAxis.setEnabled(false);
		
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "心率");
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleSize(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
//        set1.setCircleHoleColor(Color.WHITE);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mHRChart.setData(data);
		
		
	}
	
	private void setGluData() {
		
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(mActivity, "CarpOrange",
				null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (!dbHelper.tableIsExist("Glu", db)) {
			return;
		}
		
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder("").append(c.get(Calendar.YEAR)).append("年")
				.append(c.get(Calendar.MONTH)).append("月%");
		Cursor cursor = db.rawQuery("select glu,date from Glu where date like '" + sb.toString() + "%'", null);
		
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		
		int day = DayUtil.getDaysByYearMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
		for (int i = 1; i <= day; i++) {
            xVals.add((i) + "");
        } 
		String[] hrs = new String[day];
		if(cursor.moveToFirst()) {
			do {
				String hr = cursor.getString(0);
				int theday = Integer.parseInt(cursor.getString(1).split("月|日")[1]);
				
				hrs[theday] = hr;
				
			} while (cursor.moveToNext());
		}
		
		float max1 = 0;
		for (int i = 0; i < day; i++) {
			if(!TextUtils.isEmpty(hrs[i])) {
				yVals1.add(new Entry(Float.parseFloat(hrs[i]), i+1));
				if(Float.parseFloat(hrs[i]) > max1) {
					max1 = Float.parseFloat(hrs[i]);
				}
			}
			
		}
		
		YAxis leftAxis = mGluChart.getAxisLeft();
		leftAxis.setAxisMaxValue(max1+30);
		
		YAxis rightAxis = mGluChart.getAxisRight();
		rightAxis.setEnabled(false);
		
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "血糖");
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleSize(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
//        set1.setCircleHoleColor(Color.WHITE);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mGluChart.setData(data);
		
	}
	
	private void setWeightData() {
		
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(mActivity, "CarpOrange",
				null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (!dbHelper.tableIsExist("Weight", db)) {
			return;
		}
		
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder("").append(c.get(Calendar.YEAR)).append("年")
				.append(c.get(Calendar.MONTH)).append("月%");
		Cursor cursor = db.rawQuery("select weight,date from Weight where date like '" + sb.toString() + "%'", null);
		
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		
		int day = DayUtil.getDaysByYearMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
		for (int i = 1; i <= day; i++) {
            xVals.add((i) + "");
        } 
		String[] hrs = new String[day];
		if(cursor.moveToFirst()) {
			do {
				String hr = cursor.getString(0);
				int theday = Integer.parseInt(cursor.getString(1).split("月|日")[1]);
				
				hrs[theday] = hr;
				
			} while (cursor.moveToNext());
		}
		
		float max1 = 0;
		for (int i = 0; i < day; i++) {
			if(!TextUtils.isEmpty(hrs[i])) {
				yVals1.add(new Entry(Float.parseFloat(hrs[i]), i+1));
				if(Float.parseFloat(hrs[i]) > max1) {
					max1 = Float.parseFloat(hrs[i]);
				}
			}
			
		}
		
		YAxis leftAxis = mWeightChart.getAxisLeft();
		leftAxis.setAxisMaxValue(max1+30);
		
		YAxis rightAxis = mWeightChart.getAxisRight();
		rightAxis.setEnabled(false);
		
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "体重");
        set1.setAxisDependency(AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleSize(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
//        set1.setCircleHoleColor(Color.WHITE);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mWeightChart.setData(data);
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setBPData();
	}
}
