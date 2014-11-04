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
import com.mingmay.cc.model.User;
import com.mingmay.cc.ui.FriendOperate;
import com.mingmay.cc.ui.FriendPage;
import com.mingmay.cc.util.http.HttpProxy;

import android.os.AsyncTask;

public class SearchFriendTask extends AsyncTask<String, String, Integer> {
    private ArrayList<Friend> data;
    private FriendOperate page;
    public SearchFriendTask(FriendOperate page){
    	this.page=page;
    }
	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String URL=CCApplication.HTTPSERVER+"/m_user!searchUser.action";
		
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.addAll(CCApplication.header);
		param.add(new BasicNameValuePair("userId", String.valueOf(CCApplication.loginUser.ID)));
		param.add(new BasicNameValuePair("keyWord", "5996957"));
		param.add(new BasicNameValuePair("curPage", "1"));
		param.add(new BasicNameValuePair("pageSize", "20"));
		try {
			HttpResponse response=new HttpProxy().post(URL, param);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
				//{"head":{"st":0,"msg":"消息成功返回.","cd":"g4lIP6pOMY+xYiNfD4wpKw=="},"body":{"userInfo":"","cstatus":"2"}}
				 
					JSONObject obj=new JSONObject(rev);
					JSONArray friends=obj.getJSONObject("body").getJSONArray("userInfo");
					int len=friends.length();
					data=new ArrayList<Friend>();
					for(int i=0;i<len;i++){
						Friend f=Friend.jsonToSearchFriend(friends.getJSONObject(i));
						data.add(f);
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		page.callBack(data);
		super.onPostExecute(result);
	}
}
