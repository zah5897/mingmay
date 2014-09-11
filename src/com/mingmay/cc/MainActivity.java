package com.mingmay.cc;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mingmay.cc.adapter.MyFragmentPagerAdapter;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.fragment.main.PlanFragment;
import com.mingmay.cc.fragment.main.WeatherFragment;
import com.mingmay.cc.ui.LoginPage;
import com.mingmay.cc.ui.chat.FriendsCircle;
import com.mingmay.cc.view.PointView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private List<Fragment> fragmentList;
	private LinearLayout flowLayout;

	private LinearLayout fristLine,secondLine;
	private int marginTen;
	
	private PointView indexView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_main);
		DisplayMetrics d = getResources().getDisplayMetrics();
		CCApplication.screenWidth = d.widthPixels;
		CCApplication.screenHeight = d.heightPixels;
		CCApplication.density = d.density;
		marginTen=(int) (10*CCApplication.density);
		findViewById(R.id.left_btn).setOnClickListener(this);
		findViewById(R.id.right_btn).setOnClickListener(this);
		indexView=(PointView) findViewById(R.id.pageIndexView);
		InitViewPager();
		initFlow();
	}

	public void InitViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.top_view_pager);
		fragmentList = new ArrayList<Fragment>();
		Fragment weather = new WeatherFragment();
		PlanFragment plan = new PlanFragment();
		fragmentList.add(weather);
		fragmentList.add(plan);

		// 
		mViewPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentList));
		mViewPager.setCurrentItem(0); 
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());//  
	}

	@SuppressLint("NewApi")
	private void initFlow() {
		flowLayout=(LinearLayout) findViewById(R.id.flowLayout);
		initWidth();
		addFristLine();
		addSecondLine();
	}

	float itemWidth;
	int leftWidth,rightWidth;
	float percent;
	private void initWidth(){
		itemWidth=(CCApplication.screenWidth-30*CCApplication.density);
		percent=itemWidth/(369+225);
		leftWidth=(int) (369*percent);
		rightWidth=(int) (225*percent);
	}
	
	private void addFristLine(){
		fristLine=new LinearLayout(this);
		
		ImageView jactes=new ImageView(this);
		jactes.setId(R.id.id_jacket);
		jactes.setOnClickListener(this);
		jactes.setImageResource(R.drawable.jacket_selector);
		fristLine.addView(jactes,leftWidth,(int)(leftWidth/2.2919f));
		
		ImageView acc=new ImageView(this);
		acc.setId(R.id.id_accessory);
		acc.setOnClickListener(this);
		acc.setImageResource(R.drawable.accessory_selector);
		LinearLayout.LayoutParams accParam=new LinearLayout.LayoutParams(rightWidth,(int)(rightWidth/1.3975f));
		accParam.setMargins(marginTen, 0, 0, 0);
		fristLine.addView(acc,accParam);
		flowLayout.addView(fristLine);
	}
	
	
	private void addSecondLine(){
		secondLine=new LinearLayout(this);
		int blockHeight=(int)(rightWidth/0.70754f);
		int pantsHeight=(int)(leftWidth/2.2919f);
		int twinsHeight=blockHeight-pantsHeight-marginTen;
		int twinsWidth=(leftWidth-marginTen)/2;
		
		
		LinearLayout secondLineLeftBlock=new LinearLayout(this); 
		secondLineLeftBlock.setOrientation(LinearLayout.VERTICAL);
		 ImageView pants=new ImageView(this);
		  pants.setId(R.id.id_pants);
		  pants.setImageResource(R.drawable.pants_selector);
		  pants.setOnClickListener(this);
		  secondLineLeftBlock.addView(pants, leftWidth,pantsHeight);
		
		  LinearLayout twinsLayout=new LinearLayout(this);
		  ImageView bag=new ImageView(this);
		  bag.setId(R.id.id_bag);
		  bag.setImageResource(R.drawable.bag_selector);
		  bag.setOnClickListener(this);
		  twinsLayout.addView(bag, twinsWidth,twinsHeight);
		
		  ImageView shoes=new ImageView(this);
		  shoes.setId(R.id.id_shoes);
		  shoes.setImageResource(R.drawable.shoes_selector);
		  shoes.setOnClickListener(this);
		  LinearLayout.LayoutParams shoesParam=new LinearLayout.LayoutParams(twinsWidth,twinsHeight);
		  shoesParam.setMargins(marginTen, 0, 0, 0);
		  twinsLayout.addView(shoes, shoesParam);
		  
		  LinearLayout.LayoutParams twinsParam=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		  twinsParam.setMargins(0, marginTen, 0, 0);
		  secondLineLeftBlock.addView(twinsLayout,twinsParam);
		
		secondLine.addView(secondLineLeftBlock);

		//right
		ImageView skirts=new ImageView(this);
		skirts.setId(R.id.id_skirt);
		skirts.setImageResource(R.drawable.skirt_selector);
		skirts.setOnClickListener(this);
		
		LinearLayout.LayoutParams skirtsParam=new LinearLayout.LayoutParams(rightWidth, blockHeight);
		skirtsParam.setMargins(marginTen,0, 0, 0);
		secondLine.addView(skirts,skirtsParam);
		
	    LinearLayout.LayoutParams secondLineParam=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    secondLineParam.setMargins(0, marginTen, 0, 0);
		flowLayout.addView(secondLine,secondLineParam);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this,LoginPage.class);
		startActivity(i);
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onPageSelected(int arg0) {
			indexView.setCurrentPosition(arg0);
		}
	}
}
