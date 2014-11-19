package com.mingmay.cc.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyUtil{
   public static void putValue(Context context,String key,String value){
	   SharedPreferences spf=context.getSharedPreferences("cc", Context.MODE_PRIVATE);
	   spf.edit().putString(key, value).commit();
   }
   
   public static  String getValue(Context context,String key){
	   SharedPreferences spf=context.getSharedPreferences("cc", Context.MODE_PRIVATE);
	   return spf.getString(key, null);
   }
}

