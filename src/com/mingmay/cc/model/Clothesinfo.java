package com.mingmay.cc.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Clothesinfo {
	public int clothesId;
    public String clothesType;
    public String clothesSize;
    public String clothesPrice;
    public String clothesColor;
    public String clothesBrand;
    public String clothesDescription;
    public String createDate;
    public int createBy;
    public String createByName;
    
    public String createByImg;
    public String clothesName;
    public String clothesImage;
    public int  praiseNum;
    public int isPraise;
    public int commentNum;
    public int forwardNum;
    public int delFlag;
    public int heightImage;
    public int widthImage;
    public String forwardText;
    public int clothesState;
    public int secretClothes;
    public Clothesinfo originalClothes;
    
    public static Clothesinfo jsonToClothesinfo(JSONObject json) throws JSONException{
    	Clothesinfo clother=new Clothesinfo();
    	clother.clothesId=json.getInt("clothesId");
    	clother.clothesType=json.getString("clothesType");
    	clother.clothesSize=json.getString("clothesSize");
    	clother.clothesPrice=json.getString("clothesPrice");
    	
    	clother.clothesColor=json.getString("clothesColor");
    	clother.clothesBrand=json.getString("clothesBrand");
    	clother.clothesDescription=json.getString("clothesDescription");
    	clother.createDate=json.getString("createDate");
    	clother.createBy=json.getInt("createBy");
    	clother.createByName=json.getString("createByName");
    	clother.createByImg=json.getString("createByImg");
    	clother.clothesName=json.getString("clothesName");
    	clother.clothesImage=json.getString("clothesImage");
    	
    	clother.praiseNum=json.getInt("praiseNum");
    	
    	clother.isPraise=json.getInt("isPraise");
    	
    	clother.commentNum=json.getInt("commentNum");
    	clother.delFlag=json.getInt("delFlag");
    	clother.heightImage=json.getInt("heightImage");
    	clother.widthImage=json.getInt("widthImage");
    	clother.forwardText=json.getString("forwardText");
    	clother.clothesState=json.getInt("clothesState");
    	clother.secretClothes=json.getInt("secretClothes");
    	
    	if(json.has("originalClothes")){
    		clother.originalClothes=Clothesinfo.jsonToClothesinfo(json.getJSONObject("originalClothes"));
    	}
    	return clother;
    }
}
