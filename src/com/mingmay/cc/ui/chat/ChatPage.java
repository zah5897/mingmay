package com.mingmay.cc.ui.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmay.cc.R;
import com.mingmay.cc.model.Msg;
import com.xmpp.client.FormFiles;
import com.xmpp.client.util.TimeRender;
import com.xmpp.client.util.XmppTool;

public class ChatPage extends Activity {

	private MyAdapter adapter;
	private List<Msg> listMsg = new ArrayList<Msg>();
	private String pUSERID;
	private EditText msgText;
	private ProgressBar pb;

 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formclient);
		
		//��ȡIntent���������û���
		this.pUSERID = getIntent().getStringExtra("USERID");
		
		ListView listview = (ListView) findViewById(R.id.formclient_listview);
		listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		
		this.adapter = new MyAdapter(this);
		listview.setAdapter(adapter);
		
		//��ȡ�ı���Ϣ
		this.msgText = (EditText) findViewById(R.id.formclient_text);
		this.pb = (ProgressBar) findViewById(R.id.formclient_pb);

		//��Ϣ����
		ChatManager cm = XmppTool.getConnection().getChatManager();
		//������Ϣ��water-pc������water����ȡ�Լ��ķ��������ͺ��ѣ�
//		final Chat newchat = cm.createChat(this.pUSERID+"@water-pc", null);
		final Chat newchat = cm.createChat("lee@water-pc", null);
		final Chat newchat1 = cm.createChat("chai@water-pc", null);
		final Chat newchat2 = cm.createChat("huang@water-pc", null);
		
		cm.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean able) 
			{
				chat.addMessageListener(new MessageListener() {
					@Override
					public void processMessage(Chat chat2, Message message)
					{
						Log.v("--tags--", "--tags-form--"+message.getFrom());
						Log.v("--tags--", "--tags-message--"+message.getBody());
						//�յ�����water-pc������water����Ϣ����ȡ�Լ��ķ��������ͺ��ѣ�
						if(message.getFrom().contains(pUSERID+"@water-pc"))
						{
							//��ȡ�û�����Ϣ��ʱ�䡢IN
							String[] args = new String[] { pUSERID, message.getBody(), TimeRender.getDate(), "IN" };
							
							//��handler��ȡ������ʾ��Ϣ
							android.os.Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = args;
							msg.sendToTarget();
						}
						else
						{
							//message.getFrom().cantatins(��ȡ�б��ϵ��û����飬������Ϣ);
							//��ȡ�û�����Ϣ��ʱ�䡢IN
							String[] args = new String[] { message.getFrom(), message.getBody(), TimeRender.getDate(), "IN" };
							
							//��handler��ȡ������ʾ��Ϣ
							android.os.Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = args;
							msg.sendToTarget();
						}
						
					}
				});
			}
		});

		//����
		Button btattach = (Button) findViewById(R.id.formclient_btattach);
		btattach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(ChatPage.this, FormFiles.class);
				startActivityForResult(intent, 2);				
			}			
		});
		//������Ϣ
		Button btsend = (Button) findViewById(R.id.formclient_btsend);
		btsend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��ȡtext�ı�
				String msg = msgText.getText().toString();
				
				if(msg.length() > 0){
					//������Ϣ
					listMsg.add(new Msg(pUSERID, msg, TimeRender.getDate(), "OUT"));
					//ˢ��������
					adapter.notifyDataSetChanged();
					
					try {
						//������Ϣ��xiaowang
						newchat.sendMessage(msg);
						newchat1.sendMessage(msg);
						newchat2.sendMessage(msg);
					} 
					catch (XMPPException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					Toast.makeText(ChatPage.this, "��������Ϣ", Toast.LENGTH_SHORT).show();
				}
				//���text
				msgText.setText("");
			}
		});
		
		//�����ļ�
		FileTransferManager fileTransferManager = new FileTransferManager(XmppTool.getConnection());
		fileTransferManager.addFileTransferListener(new RecFileTransferListener());
	}

	
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
			Toast.makeText(ChatPage.this,"����ʧ��!",Toast.LENGTH_SHORT).show();
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
							Toast.makeText(ChatPage.this,"���ͳɹ�!",Toast.LENGTH_SHORT).show();
						}
					}
				} 
				catch (Exception e) 
				{
					Toast.makeText(ChatPage.this,"����ʧ��!",Toast.LENGTH_SHORT).show();
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
				AlertDialog.Builder builder = new AlertDialog.Builder(ChatPage.this);
				
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
											Toast.makeText(ChatPage.this,"����ʧ��!",Toast.LENGTH_SHORT).show();
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
													Toast.makeText(ChatPage.this,"�������!",Toast.LENGTH_SHORT).show();
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
		System.exit(0);
		android.os.Process.killProcess(android.os.Process
				.myPid());
	}

	class MyAdapter extends BaseAdapter {

		private Context cxt;
		private LayoutInflater inflater;

		public MyAdapter(ChatPage formClient) {
			this.cxt = formClient;
		}

		@Override
		public int getCount() {
			return listMsg.size();
		}

		@Override
		public Object getItem(int position) {
			return listMsg.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			//��ʾ��Ϣ�Ĳ��֣����ݡ��������û���ʱ��
			this.inflater = (LayoutInflater) this.cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			//IN,OUT��ͼƬ
			if(listMsg.get(position).from.equals("IN"))
			{
				convertView = this.inflater.inflate(R.layout.formclient_chat_in, null);
			}
			else
			{
				convertView = this.inflater.inflate(R.layout.formclient_chat_out, null);
			}
			
			TextView useridView = (TextView) convertView.findViewById(R.id.formclient_row_userid);
			TextView dateView = (TextView) convertView.findViewById(R.id.formclient_row_date);
			TextView msgView = (TextView) convertView.findViewById(R.id.formclient_row_msg);
			
			useridView.setText(listMsg.get(position).userid);
			dateView.setText(listMsg.get(position).date);
			msgView.setText(listMsg.get(position).msg);
			
			return convertView;
		}
	}
}