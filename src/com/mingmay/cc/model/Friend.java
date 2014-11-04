package com.mingmay.cc.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend implements Serializable{
	public int id;
	public String loginName;
	public String firstName;
	public String userImg;
	public int chatMessageCount;
	public String lastChatMessage;
	public String chatMessageDate;

	public String cellPhone;
	public int isFan;
	public static Friend jsonToFriend(JSONObject json) throws JSONException {
		Friend f=new Friend();
		f.id=json.getInt("id");
		f.loginName=json.getString("loginName");
		f.firstName=json.getString("firstName");
		f.userImg=json.getString("userImg");
		f.chatMessageCount=json.getInt("chatMessageCount");
		f.lastChatMessage=json.getString("lastChatMessage");
		f.chatMessageDate=json.getString("chatMessageDate");
		return f;
	}

	public static Friend jsonToSearchFriend(JSONObject json) throws JSONException {
		Friend f=new Friend();
		f.id=json.getInt("id");
		f.loginName=json.getString("loginName");
		f.firstName=json.getString("firstName");
		f.cellPhone=json.getString("cellPhone");
		f.userImg=json.getString("userImg");
		f.isFan=json.getInt("isFan");
		return f;
	}
}
