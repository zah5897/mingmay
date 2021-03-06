package com.mingmay.cc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mingmay.cc.MainActivity;
import com.mingmay.cc.R;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.task.LoginTask;
import com.mingmay.cc.util.ProgressDialogUtil;
import com.mingmay.cc.util.ToastUtil;

public class LoginPage extends Activity implements OnClickListener {
	private EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		if(CCApplication.loginUser!=null){
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			return;
		}
		
		setContentView(R.layout.layout_login);
		findViewById(R.id.layout).setOnClickListener(this);
		initView();
	}

	private void initView() {
		username = (EditText) findViewById(R.id.mobile);
		password = (EditText) findViewById(R.id.password);

		String loginName = getIntent().getStringExtra("login_name");
		if (loginName != null) {
			username.setText(loginName);
			password.setText("");
		}

		findViewById(R.id.to_login).setOnClickListener(this);
		findViewById(R.id.forget_password).setOnClickListener(this);
		findViewById(R.id.to_regist).setOnClickListener(this);
	}

	private void login() {
		String name = username.getText().toString();
		String pwd = password.getText().toString();
		LoginTask task = new LoginTask(this);
		task.execute(name, pwd);
		ProgressDialogUtil.showProgress(this, "正在登陆...");
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.to_login:
			login();
			break;
		case R.id.to_regist:
			Intent toRegist = new Intent(this, RegistPage.class);
			startActivity(toRegist);
			break;
		case R.id.forget_password:
			Intent forgetPassword = new Intent(this, RegistPage.class);
			startActivity(forgetPassword);
			break;
		case R.id.layout:
			        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),                                InputMethodManager.HIDE_NOT_ALWAYS);   
			break;
		default:
			break;
		}
	}
}
