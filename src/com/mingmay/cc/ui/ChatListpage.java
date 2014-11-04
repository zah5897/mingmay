package com.mingmay.cc.ui;

import java.util.ArrayList;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.mingmay.cc.R;
import com.mingmay.cc.adapter.FriendAdapter;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.task.LatestChatFriendTask;
import com.xmpp.client.FormClient;
import com.xmpp.client.util.XmppTool;

public class ChatListpage extends Fragment implements OnClickListener {
	private ListView mListView;
	private ArrayList<Friend> mData;
	private FriendAdapter adapter;

	private View layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		layout = inflater.inflate(R.layout.layout_latest_chat, null);
		initView();
		loadData();
		return layout;
	}

	private void initView() {
		ImageView back = (ImageView) layout.findViewById(R.id.left_btn);
		back.setImageResource(R.drawable.back_selector);
		back.setOnClickListener(this);
		mListView = (ListView) layout.findViewById(R.id.list_view);

		if (adapter != null) {
			mListView.setAdapter(adapter);
		}
	}

	private void loadData() {
		LatestChatFriendTask task = new LatestChatFriendTask(this);
		task.execute();
	}

	public void loadDataBallBack(ArrayList<Friend> data) {
		if (adapter == null && data != null) {
			adapter = new FriendAdapter(this.getActivity(), data);
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Friend f = (Friend) adapter.getItem(arg2);
					toChat(f);
				}
			});
		} else if (adapter != null) {
			adapter.clear();
		}

		if (adapter != null && adapter.getCount() > 0) {
			layout.findViewById(R.id.loading).setVisibility(View.GONE);
		} else {
			layout.findViewById(R.id.loading).setVisibility(View.VISIBLE);
		}
	}

	public void toChat(final Friend friend) {
		final String USERID = CCApplication.loginUser.loginName;
		final String PWD = CCApplication.loginUser.ccukey;
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					XmppTool.getConnection().login(USERID, PWD);
					// Log.i("XMPPClient", "Logged in as " +
					// XmppTool.getConnection().getUser());
					Presence presence = new Presence(Presence.Type.available);
					XmppTool.getConnection().sendPacket(presence);

					Intent intent = new Intent();
					intent.setClass(ChatListpage.this.getActivity(),
							FormClient.class);
					intent.putExtra("USERID", USERID);
					intent.putExtra("friend", friend);
					startActivity(intent);
				} catch (XMPPException e) {
					XmppTool.closeConnection();
				}
			}
		});
		t.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}
}
