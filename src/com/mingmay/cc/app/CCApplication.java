package com.mingmay.cc.app;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Base64;

import com.mingmay.cc.app.err.CrashApplication;
import com.mingmay.cc.model.User;
import com.mingmay.cc.util.TimeUtil;

public class CCApplication extends Application {
	public static final String ROOT_EXTERNAL = "cc";
	public static final String ROOT_EXTERNAL_CFG = "cfg";
	public static final String ROOT_EXTERNAL_ERR = "log";
	public static int screenWidth, screenHeight;
	public static float density;
	public static String sid;
	public static String androidID;
	public static String versionName;
	public static String ln;
    public static String mod=android.os.Build.MODEL;
    
//    public static final String HTTPSERVER="http://115.28.168.181:8080/cc";
    public static final String HTTPSERVER="http://192.168.10.115:8080/cc";
//    public static final String HTTPSERVER="http://192.168.10.116:8080/cc";
//    public static final String HTTPSERVER="http://115.28.168.181:8080/cc";
    public static List<NameValuePair> header;
    
    public static User loginUser;
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		CrashApplication.getInstance(this).onCreate();
		init();
		try {
			createHeader();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onCreate();
	}

	private void init() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		sid = tm.getDeviceId();
		versionName = getVersion();
		androidID="1002";
	}

	 
	private String getAndroidID() {
		return android.provider.Settings.Secure.getString(getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
	}

	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1.0";
	}
	
	private void createHeader() throws UnsupportedEncodingException{
		 List<NameValuePair> parameters = new ArrayList<NameValuePair>();  
	        parameters.add(new BasicNameValuePair("aid", CCApplication.androidID));  
	        parameters.add(new BasicNameValuePair("ver", CCApplication.versionName));  
	        parameters.add(new BasicNameValuePair("ln", "zh_CN"));  
//	        parameters.add(new BasicNameValuePair("cd",getVerByJava(CCApplication.androidID+CCApplication.versionName)));   
	        parameters.add(new BasicNameValuePair("cd", "khc5+/1MxDiZ1bK77Jpt7A=="));   
	        parameters.add(new BasicNameValuePair("sid", CCApplication.sid));  
	        parameters.add(new BasicNameValuePair("mos", "ANDROID"));  
	        parameters.add(new BasicNameValuePair("mod", CCApplication.mod));  
	        parameters.add(new BasicNameValuePair("de", TimeUtil.currentLocalTimeString()));  
	        parameters.add(new BasicNameValuePair("sync", "1")); 
	        header=parameters;
	}
	private String getVer(String hasDe) throws UnsupportedEncodingException{
		 
		   MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("md5");
			byte[] by = md5.digest(hasDe.getBytes("UTF-8"));
			String md5Str= new String(by);
			 return android.util.Base64.encodeToString(md5Str.getBytes(), Base64.DEFAULT);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    	 return "";
	}
	private String getVerByJava(String text) throws UnsupportedEncodingException{
		
		MessageDigest digester = null;

		try{
			digester = MessageDigest.getInstance("MD5");

			digester.update(text.getBytes("UTF-8"));
		}
		catch (NoSuchAlgorithmException nsae) {
		}
		catch (UnsupportedEncodingException uee) {
		}

		byte[] bytes = digester.digest();

		  String result= android.util.Base64.encodeToString(bytes, Base64.DEFAULT);
		  result=result.replace("\\n", "");
       return result;
	}
}
