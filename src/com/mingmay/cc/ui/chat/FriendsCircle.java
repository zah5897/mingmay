package com.mingmay.cc.ui.chat;


import com.mingmay.cc.R;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.service.CCService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class FriendsCircle extends Activity implements OnClickListener {
    private ListView mListView;
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
	private void startLoginChat(){
		Intent service=new Intent(this,CCService.class);
		service.setAction(CCService.Action_LOGIN);
		service.putExtra("USERID",  CCApplication.loginUser.loginName);
		service.putExtra("PWD", CCApplication.loginUser.ccukey);
		startService(service);
	}
	
	private void loadData() {
		// TODO Auto-generated method stub
		
	}
	private void initView(){
		ImageView back=(ImageView) findViewById(R.id.left_btn);
		back.setImageResource(R.drawable.back_selector);
		back.setOnClickListener(this);
		
		mListView=(ListView) findViewById(R.id.list_view);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}
