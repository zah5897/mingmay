package com.mingmay.cc.ui;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmay.cc.R;
import com.mingmay.cc.task.LoginTask;
import com.mingmay.cc.thrity_sso.AccessTokenKeeper;
import com.mingmay.cc.thrity_sso.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.weibo.sdk.android.api.UserAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.GeneralDataShowActivity;
import com.tencent.weibo.sdk.android.component.GeneralInterfaceActivity;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

public class LoginPage extends Activity implements OnClickListener {
	private EditText username, password;
	 private Oauth2AccessToken mAccessToken;
	 private TextView mTokenText;
	 public int mLoginType=-1; //0本系统账号密码，1腾讯微博，2新浪微博
	 private UserAPI userAPI;//帐户相关API
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_login);
		initView();
	}

	private void initView() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		
		findViewById(R.id.to_login).setOnClickListener(this);
		findViewById(R.id.tx).setOnClickListener(this);
		findViewById(R.id.sina).setOnClickListener(this);
		findViewById(R.id.to_regist).setOnClickListener(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(mLoginType==1){
			Tx_USerInfo();			
		}
		super.onResume();
	}
	
	private void Tx_USerInfo(){
		String accessToken = Util.getSharePersistent(getApplicationContext(),
				"ACCESS_TOKEN");
		if (accessToken == null || "".equals(accessToken)) {
			Toast.makeText(this, "请先授权",
					Toast.LENGTH_SHORT).show();
			//this.finish();
			tx_Token();
			return ;
		} 
		
		AccountModel account = new AccountModel(accessToken);
		userAPI = new UserAPI(account);
		userAPI.getUserInfo(this, "json", mCallBack, null, BaseVO.TYPE_JSON);
	}
	
	private void login(){
		mLoginType=0;
		String name=username.getText().toString();
		String pwd=password.getText().toString();
		LoginTask task=new LoginTask(this);
		task.execute("5996957","123456");
//		task.execute(name,pwd);
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.to_login:
			login();
			break;
		case R.id.tx:
			tx_Token();
			break;
		case R.id.sina:
			mLoginType=2;
			 WeiboAuth mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
			 SsoHandler mSsoHandler = new SsoHandler(this, mWeiboAuth);
             mSsoHandler.authorize(new AuthListener());
			break;
		case R.id.to_regist:
			
			break;
		default:
			break;
		}
	}
	
	 
	private void tx_Token(){
		mLoginType=1;
		long appid = Long.valueOf(Util.getConfig().getProperty("APP_KEY"));
		String app_secket = Util.getConfig().getProperty("APP_KEY_SEC");
		auth(appid, app_secket);
	}
	
	private void auth(long appid, String app_secket) {
		final Context context = this.getApplicationContext();
		//注册当前应用的appid和appkeysec，并指定一个OnAuthListener
		//OnAuthListener在授权过程中实施监听
		AuthHelper.register(this, appid, app_secket, new OnAuthListener() {

			//如果当前设备没有安装腾讯微博客户端，走这里
			@Override
			public void onWeiBoNotInstalled() {
				Toast.makeText(LoginPage.this, "onWeiBoNotInstalled", 1000)
						.show();
				AuthHelper.unregister(LoginPage.this);
				Intent i = new Intent(LoginPage.this,Authorize.class);
				startActivity(i);
			}

			//如果当前设备没安装指定版本的微博客户端，走这里
			@Override
			public void onWeiboVersionMisMatch() {
				Toast.makeText(LoginPage.this, "onWeiboVersionMisMatch",
						1000).show();
				AuthHelper.unregister(LoginPage.this);
				Intent i = new Intent(LoginPage.this,Authorize.class);
				startActivity(i);
			}

			//如果授权失败，走这里
			@Override
			public void onAuthFail(int result, String err) {
				Toast.makeText(LoginPage.this, "result : " + result, 1000)
						.show();
				AuthHelper.unregister(LoginPage.this);
			}

			//授权成功，走这里
			//授权成功后，所有的授权信息是存放在WeiboToken对象里面的，可以根据具体的使用场景，将授权信息存放到自己期望的位置，
			//在这里，存放到了applicationcontext中
			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				Toast.makeText(LoginPage.this, "passed", 1000).show();
				//
				Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
				Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
				Util.saveSharePersistent(context, "OPEN_ID", token.openID);
//				Util.saveSharePersistent(context, "OPEN_KEY", token.omasKey);
				Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
//				Util.saveSharePersistent(context, "NAME", name);
//				Util.saveSharePersistent(context, "NICK", name);
				Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
				Util.saveSharePersistent(context, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
				AuthHelper.unregister(LoginPage.this);
			}
		});

		AuthHelper.auth(this, "");
	}
	
	 class AuthListener implements WeiboAuthListener {
	        
	        @Override
	        public void onComplete(Bundle values) {
	            // 从 Bundle 中解析 Token
	            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
	            if (mAccessToken.isSessionValid()) {
	                // 显示 Token
	                updateTokenView(false);
	                
	                // 保存 Token 到 SharedPreferences
	                AccessTokenKeeper.writeAccessToken(LoginPage.this, mAccessToken);
	                Toast.makeText(LoginPage.this, 
	                		"授权成功", Toast.LENGTH_SHORT).show();
	            } else {
	                // 以下几种情况，您会收到 Code：
	                // 1. 当您未在平台上注册的应用程序的包名与签名时；
	                // 2. 当您注册的应用程序包名与签名不正确时；
	                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
	                String code = values.getString("code");
	                String message = "授权失败";
	                if (!TextUtils.isEmpty(code)) {
	                    message = message + "\nObtained the code: " + code;
	                }
	                Toast.makeText(LoginPage.this, message, Toast.LENGTH_LONG).show();
	            }
	        }

	        @Override
	        public void onCancel() {
	            Toast.makeText(LoginPage.this, 
	                   "取消授权", Toast.LENGTH_LONG).show();
	        }

	        @Override
	        public void onWeiboException(WeiboException e) {
	            Toast.makeText(LoginPage.this, 
	                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
	        }
	    }
	    
	    /**
	     * 显示当前 Token 信息。
	     * 
	     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
	     */
	    private void updateTokenView(boolean hasExisted) {
	        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
	                new java.util.Date(mAccessToken.getExpiresTime()));
	        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
	        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));
	        
	        String message = String.format(format, mAccessToken.getToken(), date);
	        if (hasExisted) {
	            message = "Token 仍在有效期内，无需再次登录。" + "\n" + message;
	        }
	        mTokenText.setText(message);
	    }
	    
	    
	    
	    private HttpCallback mCallBack=new  HttpCallback() {
			@Override
			public void onResult(Object object) {
				ModelResult result = (ModelResult) object;
				if(result!=null && result.isSuccess()){
					String json=result.getObj().toString();
					LoginTask task=new LoginTask(LoginPage.this);
					task.execute(json);
				}else{
					Toast.makeText(LoginPage.this,
							"调用失败", Toast.LENGTH_SHORT)
							.show();
				}
				
			}
		};
}
