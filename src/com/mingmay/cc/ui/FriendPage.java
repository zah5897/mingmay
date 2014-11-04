package com.mingmay.cc.ui;

import java.util.ArrayList;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.mingmay.cc.R;
import com.mingmay.cc.adapter.FriendPageAdapter;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.model.User;
import com.mingmay.cc.task.FriendTask;
import com.xmpp.client.FormClient;
import com.xmpp.client.util.XmppTool;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FriendPage extends Fragment implements OnClickListener {
	private View layout;
	private ProgressBar progress;
	private ListView mList;
	private FriendPageAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		layout = inflater.inflate(R.layout.layout_friend, null);
		initView();
		loadData();
		return layout;
	}

	private void initView() {
		((TextView) layout.findViewById(R.id.title)).setText("好友");
		ImageView left = (ImageView) layout.findViewById(R.id.left_btn);
		left.setImageResource(R.drawable.back_selector);
		left.setOnClickListener(this);
		layout.findViewById(R.id.right_btn).setOnClickListener(this);
		mList = (ListView) layout.findViewById(R.id.friend_listview);
		progress = (ProgressBar) layout.findViewById(R.id.loading);
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				User u = (User) adapter.getItem(arg2);
				toChat(u);
			}
		});
	}

	private void loadData() {
		FriendTask task = new FriendTask(this);
		task.execute();
	}

	public void callBack(ArrayList<User> data) {
		if (data != null) {
			if (adapter == null) {
				adapter = new FriendPageAdapter(this, data);
				mList.setAdapter(adapter);
				progress.setVisibility(View.GONE);
			}
		}
	}

	private void showRightMenu(View v) {
		final PopupWindow popupWindow = new PopupWindow(null,
				CCApplication.screenWidth / 3 + 20, LayoutParams.WRAP_CONTENT);
		ListView listView = new ListView(getActivity());

		String[] menu = { "扫一扫加好友", "账号添加好友", "添加微博好友", "添加微信好友" };
		ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, menu);
		listView.setAdapter(menuAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					popupWindow.dismiss();
					break;
				case 1:
					Intent toSearch = new Intent(getActivity(),
							FriendOperate.class);
					startActivity(toSearch);
					popupWindow.dismiss();
					break;
				case 2:
					popupWindow.dismiss();
					break;
				case 3:
					popupWindow.dismiss();
					break;
				}
			}
		});
		popupWindow.setContentView(listView);
		// popupWindow.setFocusable(false);
		popupWindow.setFocusable(true);
		// popupWindow.showAsDropDown（View view）弹出对话框，位置在紧挨着view组件
		popupWindow.showAsDropDown(v);// 弹出对话框，位置在紧挨着view组件，x y
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.left_btn:
			getActivity().finish();
			break;
		case R.id.right_btn:
			// Intent i=new Intent(getActivity(),FriendOperate.class);
			// startActivity(i);
			showRightMenu(arg0);
			break;
		default:
			break;
		}
	}

	public void toChat(final User friend) {
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
					intent.setClass(getActivity(), FormClient.class);
					intent.putExtra("USERID", USERID);
					intent.putExtra("user", friend);
					startActivity(intent);
				} catch (XMPPException e) {
					XmppTool.closeConnection();
				}
			}
		});
		t.start();
	}
}
