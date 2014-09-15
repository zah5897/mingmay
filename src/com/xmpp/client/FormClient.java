package com.xmpp.client;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mingmay.cc.R;
import com.mingmay.cc.model.Msg;
import com.xmpp.client.adapter.ChatAdapter;
import com.xmpp.client.util.TimeRender;
import com.xmpp.client.util.XmppTool;

public class FormClient extends Activity implements OnClickListener {

	private ChatAdapter adapter;
	private List<Msg> listMsg = new ArrayList<Msg>();
	private String pUSERID;
	private EditText msgText;
	private ProgressBar pb;
	private  Chat newchat;
	private Dialog builder;
	private int[] imageIds = new int[107];
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formclient);
		this.pUSERID = getIntent().getStringExtra("USERID");
		ListView listview = (ListView) findViewById(R.id.formclient_listview);
		listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		this.adapter = new ChatAdapter(this,listMsg);
		listview.setAdapter(adapter);
		this.msgText = (EditText) findViewById(R.id.formclient_text);
		this.pb = (ProgressBar) findViewById(R.id.formclient_pb);
		ChatManager cm = XmppTool.getConnection().getChatManager();
	    newchat = cm.createChat("lee@water-pc", null);
		cm.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean able) 
			{
				chat.addMessageListener(new MessageListener() {
					@Override
					public void processMessage(Chat chat2, Message message)
					{
						if(message.getFrom().contains(pUSERID+"@water-pc"))
						{
							String[] args = new String[] { pUSERID, message.getBody(), TimeRender.getDate(), "IN" };
							android.os.Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = args;
							msg.sendToTarget();
						}
						else
						{
							String[] args = new String[] { message.getFrom(), message.getBody(), TimeRender.getDate(), "IN" };
							android.os.Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = args;
							msg.sendToTarget();
						}
						
					}
				});
			}
		});
		findViewById(R.id.smile).setOnClickListener(this);
		findViewById(R.id.formclient_btsend).setOnClickListener(this);
		FileTransferManager fileTransferManager = new FileTransferManager(XmppTool.getConnection());
		fileTransferManager.addFileTransferListener(new RecFileTransferListener());
	}

	//createExpressionDialog();
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//���͸���
		if(requestCode==2 && resultCode==2 && data!=null){
			
			String filepath = data.getStringExtra("filepath");
			if(filepath.length() > 0)
			{
				sendFile(filepath);
			}
		}
	}
	
	private void sendFile(String filepath) {
		// ServiceDiscoveryManager sdm = new ServiceDiscoveryManager(connection);
		
		final FileTransferManager fileTransferManager = new FileTransferManager(XmppTool.getConnection());
		//���͸�water-pc��������water����ȡ�Լ��ķ��������ͺ��ѣ�
		final OutgoingFileTransfer fileTransfer = fileTransferManager.createOutgoingFileTransfer(this.pUSERID+"@water-pc/Spark 2.6.3");				
		
		final File file = new File(filepath);
		
		try 
		{
			fileTransfer.sendFile(file, "Sending");
		} 
		catch (Exception e) 
		{
			Toast.makeText(FormClient.this,"����ʧ��!",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			@Override
			public void run() 
			{
				try{					
					while (true) 
					{
						Thread.sleep(500L);
						
						Status status = fileTransfer.getStatus();								
						if ((status == FileTransfer.Status.error)
								|| (status == FileTransfer.Status.complete)
								|| (status == FileTransfer.Status.cancelled)
								|| (status == FileTransfer.Status.refused))
						{
							handler.sendEmptyMessage(4);
							break;
						}
						else if(status == FileTransfer.Status.negotiating_transfer)
						{
							//..
						}
						else if(status == FileTransfer.Status.negotiated)
						{							
							//..
						}
						else if(status == FileTransfer.Status.initial)
						{
							//..
						}
						else if(status == FileTransfer.Status.negotiating_stream)
						{							
							//..
						}
						else if(status == FileTransfer.Status.in_progress)
						{
							//�������ʾ
							handler.sendEmptyMessage(2);
							
							long p = fileTransfer.getBytesSent() * 100L / fileTransfer.getFileSize();	
							
							android.os.Message message = handler.obtainMessage();
							message.arg1 = Math.round((float) p);
							message.what = 3;
							message.sendToTarget();
							Toast.makeText(FormClient.this,"���ͳɹ�!",Toast.LENGTH_SHORT).show();
						}
					}
				} 
				catch (Exception e) 
				{
					Toast.makeText(FormClient.this,"����ʧ��!",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		}).start();
	}


	private FileTransferRequest request;
	private File file;

	class RecFileTransferListener implements FileTransferListener 
	{
		@Override
		public void fileTransferRequest(FileTransferRequest prequest)
		{
			//���ܸ���
//			System.out.println("The file received from: " + prequest.getRequestor());
			
			file = new File("mnt/sdcard/" + prequest.getFileName());
			request = prequest;
			handler.sendEmptyMessage(5);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) 
		{
							
			switch (msg.what) {
			case 1:
				//��ȡ��Ϣ����ʾ
				String[] args = (String[]) msg.obj;
				listMsg.add(new Msg(args[0], args[1], args[2], args[3]));
				//ˢ��������
				adapter.notifyDataSetChanged();
				break;			
			case 2:
				//���������
				if(pb.getVisibility()==View.GONE){
					pb.setMax(100);
					pb.setProgress(1);
					pb.setVisibility(View.VISIBLE);
				}
				break;
			case 3:
				pb.setProgress(msg.arg1);
				break;
			case 4:
				pb.setVisibility(View.GONE);
				break;
			case 5:
				final IncomingFileTransfer infiletransfer = request.accept();
				
				//��ʾ��
				AlertDialog.Builder builder = new AlertDialog.Builder(FormClient.this);
				
				builder.setTitle("������")
						.setCancelable(false)
						.setMessage("�Ƿ�����ļ���"+file.getName()+"?")
						.setPositiveButton("����",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										try 
										{
											infiletransfer.recieveFile(file);
										} 
										catch (XMPPException e)
										{
											Toast.makeText(FormClient.this,"����ʧ��!",Toast.LENGTH_SHORT).show();
											e.printStackTrace();
										}
										
										handler.sendEmptyMessage(2);
										
										Timer timer = new Timer();
										TimerTask updateProgessBar = new TimerTask() {
											public void run() {
												if ((infiletransfer.getAmountWritten() >= request.getFileSize())
														|| (infiletransfer.getStatus() == FileTransfer.Status.error)
														|| (infiletransfer.getStatus() == FileTransfer.Status.refused)
														|| (infiletransfer.getStatus() == FileTransfer.Status.cancelled)
														|| (infiletransfer.getStatus() == FileTransfer.Status.complete)) 
												{
													cancel();
													handler.sendEmptyMessage(4);
												} 
												else
												{
													long p = infiletransfer.getAmountWritten() * 100L / infiletransfer.getFileSize();													
													
													android.os.Message message = handler.obtainMessage();
													message.arg1 = Math.round((float) p);
													message.what = 3;
													message.sendToTarget();
													Toast.makeText(FormClient.this,"�������!",Toast.LENGTH_SHORT).show();
												}
											}
										};
										timer.scheduleAtFixedRate(updateProgessBar, 10L, 10L);
										dialog.dismiss();
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id)
									{
										request.reject();
										dialog.cancel();
									}
								}).show();
				break;
			default:
				break;
			}
		};
	};

	//�˳�
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		XmppTool.closeConnection();
		//System.exit(0);
		//android.os.Process.killProcess(android.os.Process
			//	.myPid());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.formclient_btsend:
		{
			String msg = msgText.getText().toString();
			if(msg.length() > 0){
				listMsg.add(new Msg(pUSERID, msg, TimeRender.getDate(), "OUT"));
				adapter.notifyDataSetChanged();
				try {
					newchat.sendMessage(msg);
				} 
				catch (XMPPException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				Toast.makeText(FormClient.this, "消息不能为空", Toast.LENGTH_SHORT).show();
			}
			msgText.setText("");
		}
			break;
		case R.id.smile:
			createExpressionDialog();
			break;
		default:
			break;
		}
	}
	private void createExpressionDialog() {
		builder = new Dialog(this);
		GridView gridView = createGridView();
		builder.setContentView(gridView);
		builder.setTitle("默认表情");
		builder.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(), imageIds[arg2 % imageIds.length]);
				ImageSpan imageSpan = new ImageSpan(FormClient.this, bitmap);
				String str = null;
				if(arg2<10){
					str = "f00"+arg2;
				}else if(arg2<100){
					str = "f0"+arg2;
				}else{
					str = "f"+arg2;
				}
				SpannableString spannableString = new SpannableString(str);
				spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				msgText.append(spannableString);
				builder.dismiss();
			}
		});
	}
	private GridView createGridView() {
		final GridView view = new GridView(this);
		List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
		//生成107个表情的id，封装
		for(int i = 0; i < 107; i++){
			try {
				if(i<10){
					Field field = R.drawable.class.getDeclaredField("f00" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					imageIds[i] = resourceId;
				}else if(i<100){
					Field field = R.drawable.class.getDeclaredField("f0" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					imageIds[i] = resourceId;
				}else{
					Field field = R.drawable.class.getDeclaredField("f" + i);
					int resourceId = Integer.parseInt(field.get(null).toString());
					imageIds[i] = resourceId;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	        Map<String,Object> listItem = new HashMap<String,Object>();
			listItem.put("image", imageIds[i]);
			listItems.add(listItem);
		}
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.team_layout_single_expression_cell, new String[]{"image"}, new int[]{R.id.image});
		view.setAdapter(simpleAdapter);
		view.setNumColumns(6);
		view.setBackgroundColor(Color.rgb(214, 211, 214));
		view.setHorizontalSpacing(1);
		view.setVerticalSpacing(1);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		view.setGravity(Gravity.CENTER);
		return view;
	}
}