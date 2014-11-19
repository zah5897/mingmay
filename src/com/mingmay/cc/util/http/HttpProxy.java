package com.mingmay.cc.util.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class HttpProxy {

	public HttpResponse post(String URL, List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(URL);
		HttpResponse httpResponse = null;
		httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		httpResponse = new DefaultHttpClient().execute(httpPost);
		return httpResponse;
	}
}
