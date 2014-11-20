package com.mingmay.cc.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;

import com.mingmay.cc.MainActivity;
import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.model.User;
import com.mingmay.cc.ui.FriendTabPage;
import com.mingmay.cc.ui.LoginPage;
import com.mingmay.cc.util.ProgressDialogUtil;
import com.mingmay.cc.util.PropertyUtil;
import com.mingmay.cc.util.ToastUtil;
import com.mingmay.cc.util.http.HttpProxy;

public class LoginTask extends AsyncTask<String, String, Integer> {

	private LoginPage loginPage;
    private String userInfo;
	public LoginTask(LoginPage loginPage) {
		this.loginPage = loginPage;
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String loginUrl = CCApplication.HTTPSERVER;
		loginUrl += "/m_login!login.action";
		try {
			return Http(loginUrl, arg0[0], arg0[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		if (result == 1) {
			ToastUtil.showToast(loginPage, "login success!");
			Intent i = new Intent(loginPage, MainActivity.class);
			loginPage.startActivity(i);
			loginPage.finish();
			PropertyUtil.putValue(loginPage, "user_info", userInfo);
		}else{
			ToastUtil.showToast(loginPage, "登陆失败");
		}
		ProgressDialogUtil.dismiss();
		super.onPostExecute(result);
	}

	private int Http(String url, String username, String password)
			throws JSONException, ClientProtocolException, IOException {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.addAll(CCApplication.header);
		param.add(new BasicNameValuePair("cellPhone", username));
		param.add(new BasicNameValuePair("password", password));
		HttpResponse response = new HttpProxy().post(url, param);
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
			JSONObject obj = new JSONObject(rev);
			JSONObject userJson = obj.getJSONObject("body").getJSONObject(
					"userInfo");
			userInfo=userJson.toString();
			CCApplication.loginUser = User.jsonToUser(userJson);
			return 1;
		} else {
			return 0;
		}
	}
}
