package com.mingmay.cc.adapter;

import java.util.ArrayList;

import com.mingmay.cc.model.Friend;
import com.mingmay.cc.ui.chat.FriendsCircle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FriendAdapter extends BaseAdapter {
	private FriendsCircle activity;
	public FriendAdapter(FriendsCircle activity,ArrayList<Friend> friends) {
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.friends=friends;
	}
	private ArrayList<Friend> friends;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friends.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return friends.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
