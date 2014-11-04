package com.mingmay.cc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.ui.FriendTabPage;
import com.mingmay.cc.ui.LoginPage;
import com.mingmay.cc.ui.fragment.CreatorFragment;
import com.mingmay.cc.ui.fragment.MineFragment;
import com.mingmay.cc.ui.fragment.TopicFragment;
import com.mingmay.cc.ui.fragment.TypeFragment;

public class MainActivity extends Activity {
	private FragmentManager fragmentManager;
	private RadioGroup radioGroup;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		DisplayMetrics d = getResources().getDisplayMetrics();
		CCApplication.screenWidth = d.widthPixels;
		CCApplication.screenHeight = d.heightPixels;
		CCApplication.density = d.density;
		fragmentManager = getFragmentManager();
		radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (checkedId == R.id.friends) {
							radioGroup.check(R.id.topic);
							toFriendPage();
						} else {
							FragmentTransaction transaction = fragmentManager
									.beginTransaction();
							Fragment fragment = getInstanceByIndex(checkedId);
							transaction.replace(R.id.content, fragment);
							transaction.commit();
						}

					}
				});
	}

	private void toFriendPage() {
		Intent intent;
		if (CCApplication.loginUser == null) {
			intent = new Intent(MainActivity.this, LoginPage.class);
		} else {
			intent = new Intent(MainActivity.this, FriendTabPage.class);
		}
		startActivity(intent);
	}

	public Fragment getInstanceByIndex(int index) {
		Fragment fragment = null;
		switch (index) {
		case R.id.topic:
			fragment = new TopicFragment();
			break;
		case R.id.type:
			fragment = new TypeFragment();
			break;
		case R.id.creator:
			fragment = new CreatorFragment();
			break;
		case R.id.mine:
			fragment = new MineFragment();
			break;
		}
		return fragment;
	}
}
