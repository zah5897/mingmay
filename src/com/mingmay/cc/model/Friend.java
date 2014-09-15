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
	// public int sex;
	// public int age;
	// public String lastMsg;
	public String chatMessageDate;

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

}
