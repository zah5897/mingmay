package com.mingmay.cc.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mingmay.cc.app.CCApplication;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.model.User;
import com.mingmay.cc.ui.LoginPage;
import com.mingmay.cc.ui.RegistPage;
import com.mingmay.cc.util.ToastUtil;
import com.mingmay.cc.util.http.HttpProxy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;

public class RegistTask extends AsyncTask<String, String, String> {

	RegistPage page;
	ProgressDialog progressDialog;

	public RegistTask(RegistPage page) {
		this.page = page;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(page);
		progressDialog.setMessage("正在注册...");
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				cancel(true);
			}
		});
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub

		String URL = CCApplication.HTTPSERVER + "/m_user!registration.action";

		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.addAll(CCApplication.header);
		// param.add(new BasicNameValuePair("loginName", "zah5897"));
		param.add(new BasicNameValuePair("password1", arg0[0]));
		param.add(new BasicNameValuePair("password2", arg0[1]));
		// param.add(new BasicNameValuePair("firstName", "zhan"));
		param.add(new BasicNameValuePair("gender", arg0[2]));
		// param.add(new BasicNameValuePair("cellPhone", "13262510792"));
		// param.add(new BasicNameValuePair("emailAddress",
		// "zah5897@gmail.com"));
		try {
			HttpResponse response = new HttpProxy().post(URL, param);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
				// {"head":{"st":0,"msg":"消息成功返回.","cd":"g4lIP6pOMY+xYiNfD4wpKw=="},"body":{"userInfo":"","cstatus":"2"}}

				JSONObject obj = new JSONObject(rev);
				 String loginName=obj.getJSONObject("body").getJSONObject("userInfo").getString("loginName");
				return loginName;
			} else {
				return null;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

		if (result != null) {
			ToastUtil.showToast(page, "注册成功");
			Intent i = new Intent(page, LoginPage.class);
			i.putExtra("login_name", result);
			page.startActivity(i);
		}

		super.onPostExecute(result);
	}

}
