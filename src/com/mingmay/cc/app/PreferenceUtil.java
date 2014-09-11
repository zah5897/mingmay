package com.mingmay.cc.app;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	public String readConfig(Context context){
		SharedPreferences spf=context.getSharedPreferences("config",Context.MODE_PRIVATE);
		if(spf.contains("config")){
			return spf.getString("config", null);
		}
		return null;
	} 
	
	public void writeConfig(Context context,String jsonString){
		SharedPreferences spf=context.getSharedPreferences("config",Context.MODE_PRIVATE);
		spf.edit().putString("config", jsonString).commit();
	} 
}
