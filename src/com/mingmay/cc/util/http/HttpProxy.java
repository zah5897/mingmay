package com.mingmay.cc.util.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class HttpProxy {
    
	public HttpResponse post(String URL,List<NameValuePair> params) throws ClientProtocolException, IOException{
		BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams, 10000);  
	    HttpConnectionParams.setSoTimeout(httpParams, 10000);  
	    DefaultHttpClient client = new DefaultHttpClient(httpParams);  
		HttpPost httppost = new HttpPost(URL);
		//client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
		//client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		httppost.setEntity(new UrlEncodedFormEntity(params));
		return client.execute(httppost);
	}
}
