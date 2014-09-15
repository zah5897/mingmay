package com.mingmay.cc.service;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mingmay.cc.app.CCApplication;
import com.xmpp.client.util.XmppTool;


public class CCService extends Service {
	public static final String Action_LOGIN = "CC_CHAT_LOGIN";
	private boolean logining=false;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(intent!=null){
			if(Action_LOGIN.equals(intent.getAction())){
				String name=intent.getStringExtra("USERID");
				String pwd=intent.getStringExtra("PWD");
				chatLogin(name,pwd);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void chatLogin(final String USERID,final String PWD){
		Thread t=new Thread(new Runnable() {				
			public void run() {
				logining=true;
				try {
					XmppTool.getConnection().login(CCApplication.loginUser.loginName, CCApplication.loginUser.ccukey);
					Presence presence = new Presence(Presence.Type.available);
					XmppTool.getConnection().sendPacket(presence);
					logining=false;
				}
				catch (XMPPException e) 
				{
					XmppTool.closeConnection();
					
					//handler.sendEmptyMessage(2);
				}					
			}
		});
		t.start();
		
	}
}
