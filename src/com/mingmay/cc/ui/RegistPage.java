package com.mingmay.cc.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.mingmay.cc.R;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.util.ToastUtil;
import com.mingmay.cc.util.http.HttpProxy;
import com.mingmay.cc.view.widget.AlertDialog;

public class RegistPage extends Activity implements OnClickListener {
	private View fristStepView, secondStepView;
	private EditText mobileView, codeView,nickNameView,passwordView;
	private TextView loadCodeView;
	private String mobileStr,code,nick,password;
	private boolean isSecondStep = false;
	private boolean fristStepSuccess = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_regist);
		fristStepView = findViewById(R.id.frist_step);
		secondStepView = findViewById(R.id.second_step);
		initFristStep();
	}

	private void initFristStep() {
		isSecondStep = false;
		fristStepView.setVisibility(View.VISIBLE);
		secondStepView.setVisibility(View.GONE);
		loadCodeView=(TextView) fristStepView.findViewById(R.id.get_validate_code);
		loadCodeView.setOnClickListener(this);
		mobileView = (EditText) fristStepView.findViewById(R.id.mobile);
		codeView = (EditText) fristStepView.findViewById(R.id.validate_code);
		fristStepView.findViewById(R.id.regist_back).setOnClickListener(this);
		fristStepView.findViewById(R.id.next_step).setOnClickListener(this);
		fristStepView.findViewById(R.id.get_validate_code).setOnClickListener(
				this);
	}

	private void initSecondStep() {
		mobileStr=mobileView.getText().toString().trim();
		code=codeView.getText().toString().trim();
		if(TextUtils.isEmpty(mobileStr)||mobileStr.length()!=11){
			ToastUtil.showToast(this, "请输入手机号码");
			return;
		}
		
		if(TextUtils.isEmpty(code)){
			ToastUtil.showToast(this, "请输入验证码");
			return;
		}
		
		
		isSecondStep = true;
		fristStepView.setVisibility(View.GONE);
		secondStepView.setVisibility(View.VISIBLE);
		findViewById(R.id.regist_back).setOnClickListener(this);
		findViewById(R.id.regist_btn).setOnClickListener(this);
		nickNameView=(EditText) secondStepView.findViewById(R.id.nick_name);
		passwordView=(EditText) secondStepView.findViewById(R.id.password);
	}
	private int timeUp = 0;
	private void timer() {
		timeUp=60;
		loadCodeView.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				while (timeUp >= 0) {
					handler.sendEmptyMessage(100);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeUp--;
				}
				handler.sendEmptyMessage(100);
			}
		}.start();

	}
 

	@Override
	public void onBackPressed() {
		if (isSecondStep) {
			initFristStep();
			return;
		}
		super.onBackPressed();
	}

	private void getCode() {
		mobileStr = mobileView.getText().toString();
		if (TextUtils.isEmpty(mobileStr)||mobileStr.length()!=11) {
			ToastUtil.showToast(this, "手机号不能为空");
			return;
		}
		
		timer();
		new Thread() {
			public void run() {
				String url = CCApplication.HTTPSERVER;
				url += "/m_phonecode!sendVerificationCodeService.action";
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.addAll(CCApplication.header);
				param.add(new BasicNameValuePair("cellPhone", mobileStr));
				param.add(new BasicNameValuePair("type", "1"));
				try {
					HttpResponse response = new HttpProxy().post(url, param);
					int code = response.getStatusLine().getStatusCode();
					if (code == 200) {
						String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
						JSONObject obj = new JSONObject(rev);
						int cstatus = obj.getJSONObject("body").getInt(
								"cstatus");
						if (cstatus == 0) {
							handler.sendEmptyMessage(0);
						}
					} else {
						handler.sendEmptyMessage(-1);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(-1);
				}
			}
		}.start();

	}

	private void regist() {
		nick = nickNameView.getText().toString().trim();
		password = passwordView.getText().toString().trim();
		if (TextUtils.isEmpty(nick)) {
			ToastUtil.showToast(this, "请输入昵称");
			return;
		}
		if (nick.length()>8) {
			ToastUtil.showToast(this, "不要超过8位");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			ToastUtil.showToast(this, "请输入密码");
			return;
		}
		if (password.length()<6||password.length()>12) {
			ToastUtil.showToast(this, "密码在6-12位之间");
			return;
		}
		new Thread() {
			public void run() {
				String url = CCApplication.HTTPSERVER;
				url += "/m_user!reg.action";
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.addAll(CCApplication.header);
				param.add(new BasicNameValuePair("cellPhone", mobileStr));
				param.add(new BasicNameValuePair("verificationCode", code));
				param.add(new BasicNameValuePair("firstName", nick));
				param.add(new BasicNameValuePair("gender", "0"));
				param.add(new BasicNameValuePair("password", password));
				try {
					HttpResponse response = new HttpProxy().post(url, param);
					int code = response.getStatusLine().getStatusCode();
					if (code == 200) {
						String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
						JSONObject obj = new JSONObject(rev);
						int cstatus = obj.getJSONObject("body").getInt(
								"cstatus");
						if (cstatus == 0) {
							Bundle data = new Bundle();
							data.putInt("code", cstatus);
							Message msg = handler.obtainMessage();
							msg.setData(data);
							handler.sendMessage(msg);
						}
					} else {
						handler.sendEmptyMessage(-1);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(-1);
				}
			}
		}.start();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.regist_back:
			onBackPressed();
			break;
		case R.id.next_step:
			initSecondStep();
			break;
		case R.id.get_validate_code:
			getCode();
			break;
		case R.id.regist_btn:
			regist();
			break;
		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				loadCodeView.setText("重新获取验证码(" + timeUp + ")");
				if (timeUp < 0) {
					loadCodeView.setText("获取验证码");
					loadCodeView.setEnabled(true);
				}
				break;
			case 0:
				Bundle data = msg.getData();
				int code = data.getInt("code");
				if (code == 0) {
					ToastUtil.showToast(RegistPage.this, "获取验证码失败");
					fristStepSuccess = true;
				} else if (code == 2) {
					ToastUtil.showToast(RegistPage.this, "获取验证码失败");
					fristStepSuccess = false;
				} else if (code == 5) {
					ToastUtil.showToast(RegistPage.this, "尝试次数过多次");
				} else if (code == 7) {
					ToastUtil.showToast(RegistPage.this, "该手机号已经注册");
					fristStepSuccess = true;
					reset();
				}
				break;
			case -1:
				ToastUtil.showToast(RegistPage.this, "获取验证码失败");
				break;
			default:
				break;
			}
		};
	};

	private void reset() {
		new AlertDialog(this).builder().setTitle("提示")
				.setMsg("该手机号码已经注册过了！是否找回密码?")
				.setPositiveButton("去找回密码", new OnClickListener() {
					@Override
					public void onClick(View v) {
						ToastUtil.showToast(getBaseContext(), "去找回密码...");
					}
				}).setNegativeButton("我再想想", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();
	}
}