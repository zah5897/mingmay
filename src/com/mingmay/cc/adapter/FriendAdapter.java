package com.mingmay.cc.adapter;

import java.util.ArrayList;

import com.mingmay.cc.R;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.ui.chat.FriendsCircle;
import com.mingmay.cc.util.TimeUtil;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendAdapter extends BaseAdapter {
	private FriendsCircle activity;

	public FriendAdapter(FriendsCircle activity, ArrayList<Friend> friends) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.friends = friends;
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
		ViewHolder viewHold;
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.layout_item_friend, null);
			viewHold=new ViewHolder();
			viewHold.icon=(ImageView) convertView.findViewById(R.id.head_icon);
			viewHold.name=(TextView) convertView.findViewById(R.id.name);
			viewHold.lastmsg=(TextView) convertView.findViewById(R.id.last_msg);
			viewHold.time=(TextView) convertView.findViewById(R.id.time);
			convertView.setTag(viewHold);
		} else {
			viewHold = (ViewHolder) convertView.getTag();
		}
		viewHold.icon.setImageResource(R.drawable.ic_launcher);
		viewHold.name.setText("了走天涯");
		viewHold.lastmsg.setText("null");
		viewHold.time.setText(TimeUtil.currentLocalTimeString());
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView name, lastmsg, time;
	}

}
