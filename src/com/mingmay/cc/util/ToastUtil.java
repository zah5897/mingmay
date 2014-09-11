package com.mingmay.cc.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static final int timeValue=500;
    public static void showToast(Context context,String message){
    	Toast.makeText(context, message, timeValue).show();
    }
}
