package com.mingmay.cc.ui;

import java.util.ArrayList;

import com.mingmay.cc.R;
import com.mingmay.cc.adapter.FriendCircleAdapter;
import com.mingmay.cc.model.Clothesinfo;
import com.mingmay.cc.task.FriendCircleTask;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FriendCirclePage extends Fragment implements OnClickListener {

	private ListView listView;
	private ProgressBar progress;
	private FriendCircleAdapter adapter;
	private View layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		layout = inflater.inflate(R.layout.layout_friend_circle, null);
		initView();
		return layout;
	}

	private void initView() {
		((TextView) layout.findViewById(R.id.title)).setText("朋友圈");
		listView = (ListView) layout.findViewById(R.id.friend_circle_listview);
		progress = (ProgressBar) layout.findViewById(R.id.loading);
		layout.findViewById(R.id.left_btn).setOnClickListener(this);
		
		if(adapter!=null){
			listView.setAdapter(adapter);
		}
	}

	private void loadData() {
		FriendCircleTask task = new FriendCircleTask(this);
		task.execute();
	}

	public void loadDataCallBack(ArrayList<Clothesinfo> data) {
		if (adapter == null && data != null) {
			adapter = new FriendCircleAdapter(this, data);
			if(listView!=null){
				listView.setAdapter(adapter);
			}
		}
		if (adapter != null && adapter.getCount() > 0&&progress!=null) {
			progress.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.left_btn:
			this.getActivity().finish();
			break;

		default:
			break;
		}
	}

}
