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
import com.mingmay.cc.model.Tag;
import com.mingmay.cc.model.User;
import com.mingmay.cc.ui.FriendPage;
import com.mingmay.cc.ui.fragment.TypeFragment;
import com.mingmay.cc.util.http.HttpProxy;

import android.os.AsyncTask;

public class LoadTagTask extends AsyncTask<String, String, Integer> {
	TypeFragment typePage;
	public LoadTagTask(TypeFragment typePage){
		this.typePage=typePage;
	}
	
	private ArrayList<Tag> tags;
	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String URL=CCApplication.HTTPSERVER+"/m_tags!findTags.action";
		
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.addAll(CCApplication.header);
		User u=CCApplication.loginUser;
		param.add(new BasicNameValuePair("userId", String.valueOf(u.ID)));
		param.add(new BasicNameValuePair("ccukey", u.ccukey));
		param.add(new BasicNameValuePair("objectName", "com.bulan.user"));
		param.add(new BasicNameValuePair("objectAction", "com.bulan.user.ward"));
		try {
			HttpResponse response=new HttpProxy().post(URL, param);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				String rev = EntityUtils.toString(response.getEntity());// 返回json格式：
					JSONObject obj=new JSONObject(rev);
					JSONArray tagArray=obj.getJSONObject("body").getJSONArray("tagsInfo");
					if(tagArray!=null){
					   int len=tagArray.length();
					   if(len>0){
						   tags=new ArrayList<Tag>();
						   for(int i=0;i<len;i++){
							   tags.add(Tag.jsonToTag(tagArray.getJSONObject(i)));    
						   }
					   }
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
		typePage.callBackTag(tags);
		super.onPostExecute(result);
	}
}
