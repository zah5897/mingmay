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

import com.mingmay.cc.R;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.util.ToastUtil;
import com.mingmay.cc.util.http.HttpProxy;
import com.mingmay.cc.view.widget.AlertDialog;

public class RegistPage extends Activity implements OnClickListener {
	private View fristStepView, secondStepView;
	private EditText mobileView, codeView;
	// password, valicateCode;
	private String mobileStr;
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
		mobileView = (EditText) fristStepView.findViewById(R.id.mobile);
		codeView = (EditText) fristStepView.findViewById(R.id.validate_code);
		fristStepView.findViewById(R.id.regist_back).setOnClickListener(this);
		fristStepView.findViewById(R.id.next_step).setOnClickListener(this);
		fristStepView.findViewById(R.id.get_validate_code).setOnClickListener(
				this);
	}

	private void initSecondStep() {
		isSecondStep = true;
		fristStepView.setVisibility(View.GONE);
		secondStepView.setVisibility(View.VISIBLE);
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
		if (TextUtils.isEmpty(mobileStr)) {
			ToastUtil.showToast(this, "手机号不能为空");
			return;
		}
		new Thread() {
			public void run() {
				String url = CCApplication.HTTPSERVER;
				url += "/m_phonecode!sendVerificationCodeService.action";
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.addAll(CCApplication.header);
				param.add(new BasicNameValuePair("cellPhone", mobileStr));
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
		mobileStr = mobileView.getText().toString();
		if (TextUtils.isEmpty(mobileStr)) {
			ToastUtil.showToast(this, "手机号不能为空");
			return;
		}
		new Thread() {
			public void run() {
				String url = CCApplication.HTTPSERVER;
				url += "/m_phonecode!sendVerificationCodeService.action";
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.addAll(CCApplication.header);
				param.add(new BasicNameValuePair("cellPhone", mobileStr));
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