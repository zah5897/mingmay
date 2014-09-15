package com.mingmay.cc.ui;

import java.util.ArrayList;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class LatestChatFriendPage extends Activity implements OnClickListener {
	private ListView mListView;
	private ArrayList<Friend> mData;
	private FriendAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_latest_chat);
		//startLoginChat();
		initView();
		loadData();
	}
	private void initView() {
		ImageView back = (ImageView) findViewById(R.id.left_btn);
		back.setImageResource(R.drawable.back_selector);
		back.setOnClickListener(this);
        findViewById(R.id.friend_ship_title).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.list_view);
	}
	private void loadData() {
		 LatestChatFriendTask task=new LatestChatFriendTask(this);
		 task.execute();
	}

	public void loadDataBallBack(ArrayList<Friend> data){
		if(adapter==null&&data!=null){
			adapter=new FriendAdapter(this, data);
			mListView.setAdapter(adapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
	             Friend f=(Friend) adapter.getItem(arg2);
	             toChat(f);
				}
			});
		}else if(adapter!=null){
			adapter.clear();
		} 
		
		if (adapter.getCount() > 0) {
			findViewById(R.id.loading).setVisibility(View.GONE);
		} else {
			findViewById(R.id.loading).setVisibility(View.VISIBLE);
		}
	}

	
	public void toChat(final Friend friend){
         final String USERID = CCApplication.loginUser.loginName;
		 final String PWD = CCApplication.loginUser.ccukey;
		 Thread t=new Thread(new Runnable() {				
				public void run() {
					try {
						XmppTool.getConnection().login(USERID, PWD);
//						Log.i("XMPPClient", "Logged in as " + XmppTool.getConnection().getUser());
						Presence presence = new Presence(Presence.Type.available);
						XmppTool.getConnection().sendPacket(presence);
						
						Intent intent = new Intent();
						intent.setClass(LatestChatFriendPage.this, FormClient.class);
						intent.putExtra("USERID", USERID);
						intent.putExtra("friend", friend);
						startActivity(intent);
					}
					catch (XMPPException e) 
					{
						XmppTool.closeConnection();
					}					
				}
			});
			t.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.friend_ship_title:
			 Intent i=new Intent(LatestChatFriendPage.this,FriendCircle.class);
             startActivity(i);
			break;
		default:
			break;
		}
	}
}
