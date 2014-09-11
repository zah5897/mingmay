package com.mingmay.cc.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.model.User;
import com.mingmay.cc.ui.LoginPage;
import com.mingmay.cc.ui.chat.FriendsCircle;
import com.mingmay.cc.util.ToastUtil;
import com.tencent.weibo.sdk.android.api.UserAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.BaseVO;

public class LoginTask extends AsyncTask<String, String, Integer> {

	private LoginPage loginPage;

	public LoginTask(LoginPage loginPage) {
		this.loginPage = loginPage;
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String loginUrl = CCApplication.HTTPSERVER;
		if (loginPage.mLoginType == 1) {
			loginUrl += "/m_user!qq_login.action";
			String str = Util.getSharePersistent(loginPage, "ACCESS_TOKEN");
			str += "\n" + Util.getSharePersistent(loginPage, "EXPIRES_IN");
			str += "\n" + Util.getSharePersistent(loginPage, "OPEN_ID");
			str += "\n" + Util.getSharePersistent(loginPage, "OPEN_KEY");
			str += "\n" + Util.getSharePersistent(loginPage, "REFRESH_TOKEN");
			str += "\n" + Util.getSharePersistent(loginPage, "NAME");
			str += "\n" + Util.getSharePersistent(loginPage, "NICK");
			str += "\n" + Util.getSharePersistent(loginPage, "CLIENT_ID");
			Log.d("", str);
		} else if (loginPage.mLoginType == 2) {

		} else {
			loginUrl += "/m_login!login.action";
			try {
				return Http(loginUrl, arg0[0], arg0[1]);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		if(result!=0){
			ToastUtil.showToast(loginPage, "login success!");
			Intent i=new Intent(loginPage,FriendsCircle.class);
			loginPage.startActivity(i);
			loginPage.finish();
		}
		super.onPostExecute(result);
	}

	private int Http(String url, String username, String password)
			throws JSONException, ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		// 添加http头信息

		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.addAll(CCApplication.header);
		param.add(new BasicNameValuePair("loginName", username));
		param.add(new BasicNameValuePair("password", password));
		httppost.setEntity(new UrlEncodedFormEntity(param));
		HttpResponse response;
		response = httpclient.execute(httppost);
		// 检验状态码，如果成功接收数据
		int code = response.getStatusLine().getStatusCode();
		if (code == 200) {
			String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
																	// {"id":
																	// "27JpL~j4vsL0LX00E00005","version":
																	// "abc"}
			JSONObject obj = new JSONObject(rev);
			JSONObject userJson = obj.getJSONObject("body").getJSONObject(
					"userInfo");
			CCApplication.loginUser = User.jsonToUser(userJson);
			return 1;
		} else {
			return 0;
		}
	}
}
