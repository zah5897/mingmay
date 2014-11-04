package com.mingmay.cc.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.widget.RadioGroup;

import com.mingmay.cc.R;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.ui.fragment.CreatorFragment;

public class FriendTabPage extends FragmentActivity {
	private FragmentManager fragmentManager;
	private RadioGroup radioGroup;

	private FriendCirclePage friendCircle;
	private ChatListpage chat;
	private FriendPage friend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tab_friend);
		DisplayMetrics d = getResources().getDisplayMetrics();
		CCApplication.screenWidth = d.widthPixels;
		CCApplication.screenHeight = d.heightPixels;
		CCApplication.density = d.density;
		fragmentManager = getFragmentManager();
		radioGroup = (RadioGroup) findViewById(R.id.friend_tab);

		friendCircle = new FriendCirclePage();
		replaceFragment(friendCircle);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						Fragment fragment = getInstanceByIndex(checkedId);
						replaceFragment(fragment);
					}
				});
	}

	private void replaceFragment(Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}

	public Fragment getInstanceByIndex(int index) {
		switch (index) {
		case R.id.friend_circle:
			if (friendCircle == null) {
				friendCircle = new FriendCirclePage();
			}
			return friendCircle;
		case R.id.chat:
			if (chat == null) {
				chat = new ChatListpage();
			}
			return chat;
		default:
			if (friend == null) {
				friend = new FriendPage();
			}
			return friend;
		}
	}
}
