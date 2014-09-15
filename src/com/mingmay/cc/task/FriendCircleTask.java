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
import com.mingmay.cc.model.Clothesinfo;
import com.mingmay.cc.model.Friend;
import com.mingmay.cc.ui.FriendCircle;
import com.mingmay.cc.util.http.HttpProxy;

import android.os.AsyncTask;

public class FriendCircleTask extends AsyncTask<String, String, Integer> {
	private FriendCircle activity;
	ArrayList<Clothesinfo> data;
	public FriendCircleTask(FriendCircle activity) {
		this.activity = activity;
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String URL = CCApplication.HTTPSERVER+"/m_clothes!getFriendsClothes.action";
		 

		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.addAll(CCApplication.header);
		param.add(new BasicNameValuePair("userId", String.valueOf(CCApplication.loginUser.ID)));
		param.add(new BasicNameValuePair("keyWord", ""));
		param.add(new BasicNameValuePair("curPage", "1"));
		param.add(new BasicNameValuePair("pageSize", "20"));
		try {
			HttpResponse response=new HttpProxy().post(URL, param);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
				//{"head":{"st":0,"msg":"消息成功返回.","cd":"g4lIP6pOMY+xYiNfD4wpKw=="},"body":{"userInfo":"","cstatus":"2"}}
				try {
					JSONObject obj=new JSONObject(rev);
					JSONObject body=obj.getJSONObject("body");
					int status=body.getInt("cstatus");
					if(status==0){
						JSONArray clothesinfos=body.getJSONArray("clothesInfo");
						int len=clothesinfos.length();
						if(len>0){
							data=new ArrayList<Clothesinfo>();
							for(int i=0;i<len;i++){
								Clothesinfo info=Clothesinfo.jsonToClothesinfo(clothesinfos.getJSONObject(i));
								data.add(info);
							}
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 1;
			} else {
				return 0;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		activity.loadDataCallBack(data);
		super.onPostExecute(result);
	}

}
