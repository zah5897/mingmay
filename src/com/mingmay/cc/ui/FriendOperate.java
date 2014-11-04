package com.mingmay.cc.ui;

import java.util.ArrayList;

import com.mingmay.cc.R;
import com.mingmay.cc.adapter.FriendAdapter;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.model.User;
import com.mingmay.cc.task.SearchFriendTask;
import com.mingmay.cc.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FriendOperate extends Activity implements OnClickListener {
	private EditText keyWoldInput;
	private FriendAdapter adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search_friend);
		initView();
	}

	private void initView() {
		findViewById(R.id.left_btn).setOnClickListener(this);
		((TextView) findViewById(R.id.title)).setText("添加");
		listView=(ListView) findViewById(R.id.friend_listview);
		keyWoldInput=(EditText) findViewById(R.id.search);
		keyWoldInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		keyWoldInput.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				String keyWold=keyWoldInput.getText().toString().trim();
				if("".equals(keyWold)){
					ToastUtil.showToast(FriendOperate.this, "请输入关键字");
				    return true;
				}
				SearchFriendTask task=new SearchFriendTask(FriendOperate.this);
				task.execute();
				return false;
			}
		});
	}

	public void callBack(ArrayList<Friend> users){
		if(users!=null){
			adapter=new FriendAdapter(this, users);
			listView.setAdapter(adapter);
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.left_btn:
			finish();
			break;
		default:
			break;
		}
	}
}
