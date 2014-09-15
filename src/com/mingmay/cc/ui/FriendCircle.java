package com.mingmay.cc.ui;

import java.util.ArrayList;

import com.mingmay.cc.R;
import com.mingmay.cc.adapter.FriendCircleAdapter;
import com.mingmay.cc.model.Clothesinfo;
import com.mingmay.cc.task.FriendCircleTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FriendCircle extends Activity implements OnClickListener {

	private ListView listView;
	private ProgressBar progress;
	private FriendCircleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_friend_circle);
		initView();
		loadData();
	}

	private void initView() {
		((TextView)findViewById(R.id.title)).setText("朋友圈");
		listView = (ListView) findViewById(R.id.friend_circle_listview);
		progress = (ProgressBar) findViewById(R.id.loading);
		findViewById(R.id.left_btn).setOnClickListener(this);
	}
	
	
	private void loadData(){
		FriendCircleTask task=new FriendCircleTask(this);
		task.execute();
	}
	public void loadDataCallBack(ArrayList<Clothesinfo> data){
		if(adapter==null&&data!=null){
			adapter=new FriendCircleAdapter(this, data);
			listView.setAdapter(adapter);
		} 
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.left_btn:
			this.finish();
			break;

		default:
			break;
		}
	}

}
