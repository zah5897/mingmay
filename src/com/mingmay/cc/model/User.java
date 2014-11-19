package com.mingmay.cc.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
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
     
     public String planCover;
     public String verificationCode;
     public String userCover;
     
     public static User jsonToUser(JSONObject json) throws JSONException{
    	 User user=new User();
    	 user.ID=json.getInt("id");
    	 user.loginName=json.optString("loginName");
    	 user.createDate=json.optString("createDate");
    	 user.emailAddress=json.optString("emailAddress");
    	 user.lastFailedLoginDate=json.optString("lastFailedLoginDate");
    	 user.firstName=json.optString("firstName");
    	 int gender=json.optInt("gender");
    	 user.gender=Gender.values()[gender];
    	 user.cc_accessToken=json.optString("cc_accessToken");
    	 user.cc_accessTokenType=json.optString("cc_accessTokenType");
    	 user.ccukey=json.optString("ccukey");
    	 user.userImg=json.optString("userImg");
    	 user.address=json.optString("address");
    	 user.signature=json.optString("signature");
    	 return user;
     }
     
     public static User jsonToFriend(JSONObject json) throws JSONException{
    	 User user=new User();
    	 user.ID=json.getInt("id");
    	 user.loginName=json.getString("loginName");
    	 user.createDate=json.getString("createDate");
    	 user.emailAddress=json.getString("emailAddress");
    	 user.cellPhone=json.getString("cellPhone");
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
    	 if(json.has("planCover")){
    		 user.planCover=json.getString("planCover");
    	 }
    	 user.verificationCode=json.getString("verificationCode");
    	 user.userCover=json.getString("userCover");
    	 return user;
     }
}

enum Gender{
	Male,Female,NotSet;
}