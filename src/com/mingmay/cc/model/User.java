package com.mingmay.cc.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
     public int ID;
     public String loginName;
     public String createDate;
     public String emailAddress;
     public String cellPhone;
     public String lastFailedLoginDate;
     public String firstName;
     public Gender gender;
     public String cc_accessToken;
     public String cc_accessTokenType;
     public String ccukey;
     public String userImg;
     public String address;
     public String signature;
     
     
     public static User jsonToUser(JSONObject json) throws JSONException{
    	 User user=new User();
    	 user.ID=json.getInt("id");
    	 user.loginName=json.getString("loginName");
    	 user.createDate=json.getString("createDate");
    	 user.emailAddress=json.getString("emailAddress");
    	 user.lastFailedLoginDate=json.getString("lastFailedLoginDate");
    	 user.firstName=json.getString("firstName");
    	 int gender=json.getInt("gender");
    	 user.gender=Gender.values()[gender];
    	 user.cc_accessToken=json.getString("cc_accessToken");
    	 user.cc_accessTokenType=json.getString("cc_accessTokenType");
    	 user.ccukey=json.getString("ccukey");
    	 user.userImg=json.getString("userImg");
    	 user.address=json.getString("address");
    	 user.signature=json.getString("signature");
    	 return user;
     }
}

enum Gender{
	Male,Female;
}