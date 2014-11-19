package com.mingmay.cc.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Tag {
 public int id;
 public String name;
 
 public static Tag jsonToTag(JSONObject obj){
	 Tag tag=new Tag();
	 try {
		tag.id=obj.getInt("id");
		tag.name=obj.getString("name");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return tag;
 }
}
