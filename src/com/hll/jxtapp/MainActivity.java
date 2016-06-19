package com.hll.jxtapp;

import java.util.ArrayList;
import java.util.List;

import com.hll.util.LocationInfoUtil;
import com.hll.util.NetworkInfoUtil;

import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;

	private LinearLayout mTabRcommend;
	private LinearLayout mTabQueue;
	private LinearLayout mTabSimulate;
	private LinearLayout mTabFriend;
	private LinearLayout mTabMoreInfo;

	private ImageButton mImgRcommend;
	private ImageButton mImgQueue;
	private ImageButton mImgSimulate;
	private ImageButton mImgFriend;
	private ImageButton mImgMoreInfo;

	private TextView mTvRecommend;
	private TextView mTvQueue;
	private TextView mTvSimulate;
	private TextView mTvFriend;
	private TextView mTvMoreInfo;
	private TextView mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题,全屏显示
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		setSelect(1);
		//查询当前网络状态
		NetworkInfoUtil.connectManager =  (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		//定位状态相关信息
		LocationInfoUtil.locationManager =  (LocationManager) getSystemService(LOCATION_SERVICE);
	}

	private void initEvent() {
		mTabRcommend.setOnClickListener(this);
		mTabQueue.setOnClickListener(this);
		mTabSimulate.setOnClickListener(this);
		mTabFriend.setOnClickListener(this);
		mTabMoreInfo.setOnClickListener(this);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		mTabRcommend = (LinearLayout) findViewById(R.id.id_tab_recommend);
		mTabQueue = (LinearLayout) findViewById(R.id.id_tab_queue);
		mTabSimulate = (LinearLayout) findViewById(R.id.id_tab_simulate);
		mTabFriend = (LinearLayout) findViewById(R.id.id_tab_friend);
		mTabMoreInfo = (LinearLayout) findViewById(R.id.id_tab_moreinfo);

		mImgRcommend = (ImageButton) findViewById(R.id.id_tab_recommend_img);
		mImgQueue = (ImageButton) findViewById(R.id.id_tab_queue_img);
		mImgSimulate = (ImageButton) findViewById(R.id.id_tab_simulate_img);
		mImgFriend = (ImageButton) findViewById(R.id.id_tab_friend_img);
		mImgMoreInfo = (ImageButton) findViewById(R.id.id_tab_moreinfo_img);

		mTvRecommend = (TextView) findViewById(R.id.id_tv_recommend);
		mTvQueue = (TextView) findViewById(R.id.id_tv_queue);
		mTvSimulate = (TextView) findViewById(R.id.id_tv_simulate);
		mTvFriend = (TextView) findViewById(R.id.id_tv_friend);
		mTvMoreInfo = (TextView) findViewById(R.id.id_tv_moreinfo);
		mTitle = (TextView) findViewById(R.id.id_title);

		mFragments = new ArrayList<Fragment>();
		Fragment mTab01 = new RecommendFragment();
		Fragment mTab02 = new QueueFragment();
		Fragment mTab03 = new SimulateFragment();
		Fragment mTab04 = new FriendFragment();
		Fragment mTab05 = new MoreInfoFragment();
		mFragments.add(mTab01);
		mFragments.add(mTab02);
		mFragments.add(mTab03);
		mFragments.add(mTab04);
		mFragments.add(mTab05);

		// FragmentPagerAdapter是PagerAdapter中的其中一种实现。它将每一个页面表示为一个 Fragment，
		// 并且每一个Fragment都将会保存到fragment manager当中
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				String title;
				resetTextView();
				switch (currentItem) {
				case 0:
					mTvRecommend.setTextColor(Color.parseColor("#00CC00"));
					title = mTvRecommend.getText().toString();
					mTitle.setText(title);
					break;
				case 1:
					mTvQueue.setTextColor(Color.parseColor("#00CC00"));
					title = mTvQueue.getText().toString();
					mTitle.setText(title);
					break;
				case 2:
					mTvSimulate.setTextColor(Color.parseColor("#00CC00"));
					title = mTvSimulate.getText().toString();
					mTitle.setText(title);
					break;
				case 3:
					mTvFriend.setTextColor(Color.parseColor("#00CC00"));
					title = mTvFriend.getText().toString();
					mTitle.setText(title);
					break;
				case 4:
					mTvMoreInfo.setTextColor(Color.parseColor("#00CC00"));
					title = mTvMoreInfo.getText().toString();
					mTitle.setText(title);
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	protected void resetTextView() {
		mTvRecommend.setTextColor(Color.parseColor("#000000"));
		mTvQueue.setTextColor(Color.parseColor("#000000"));
		mTvSimulate.setTextColor(Color.parseColor("#000000"));
		mTvFriend.setTextColor(Color.parseColor("#000000"));
		mTvMoreInfo.setTextColor(Color.parseColor("#000000"));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tab_recommend:
			setSelect(0);
			break;
		case R.id.id_tab_queue:
			setSelect(1);
			break;
		case R.id.id_tab_simulate:
			setSelect(2);
			break;
		case R.id.id_tab_friend:
			setSelect(3);
			break;
		case R.id.id_tab_moreinfo:
			setSelect(4);
			break;
		}
	}

	private void setSelect(int i) {
		mViewPager.setCurrentItem(i);
	}
}
