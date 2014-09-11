package com.mingmay.cc.ui.chat;

import java.util.ArrayList;

import com.mingmay.cc.R;
import com.mingmay.cc.adapter.FriendAdapter;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.service.CCService;
import com.tencent.weibo.sdk.android.model.Firend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class FriendsCircle extends Activity implements OnClickListener {
	private ListView mListView;
	private ArrayList<Friend> mData;
	private FriendAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_friendship);
		startLoginChat();
		initView();
		loadData();
	}

	private void startLoginChat() {
		Intent service = new Intent(this, CCService.class);
		service.setAction(CCService.Action_LOGIN);
		service.putExtra("USERID", CCApplication.loginUser.loginName);
		service.putExtra("PWD", CCApplication.loginUser.ccukey);
		startService(service);
	}

	private void loadData() {
		// TODO Auto-generated method stub
		mData = new ArrayList<Friend>();
		for (int i = 0; i < 10; i++) {
			Friend f = new Friend();
			f.ID = 1;
			f.age = 27;
			f.name = "了走天涯";
			mData.add(f);
		}
		adapter = new FriendAdapter(this, mData);
		if (adapter.getCount() > 0) {
			findViewById(R.id.loading).setVisibility(View.GONE);
		} else {
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
		}
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
             Friend f=(Friend) adapter.getItem(arg2);
             Intent i=new Intent(FriendsCircle.this,ChatPage.class);
             i.putExtra("friendName", f.name);
             startActivity(i);
			}
		});
	}

	private void initView() {
		ImageView back = (ImageView) findViewById(R.id.left_btn);
		back.setImageResource(R.drawable.back_selector);
		back.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.list_view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}
