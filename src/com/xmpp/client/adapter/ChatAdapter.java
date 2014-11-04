package com.xmpp.client.adapter;

import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingmay.cc.R;
import com.mingmay.cc.app.cache.ImageLoader;
import com.mingmay.cc.model.Msg;
import com.xmpp.client.FormClient;
import com.xmpp.client.util.ExpressionUtil;

public class ChatAdapter extends BaseAdapter {
	private Context cxt;
	private LayoutInflater inflater;
	private List<Msg> listMsg;
   private ImageLoader imgLoader;
	public ChatAdapter(FormClient formClient, List<Msg> mList) {
		this.cxt = formClient;
		this.listMsg = mList;
		this.inflater = (LayoutInflater) this.cxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imgLoader=new ImageLoader(cxt);
	}

	public int getCount() {
		return listMsg.size();
	}

	public Object getItem(int position) {
		return listMsg.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = this.inflater.inflate(R.layout.formclient_chat_out,
					null);
			viewHolder = new ViewHolder();
			viewHolder.leftIcon = (ImageView) convertView
					.findViewById(R.id.left_icon);
			viewHolder.rightIcon = (ImageView) convertView
					.findViewById(R.id.right_icon);

			viewHolder.leftMsgLayout = (RelativeLayout) convertView
					.findViewById(R.id.left_msg_layout);
			viewHolder.rightMsgLayout = (RelativeLayout) convertView
					.findViewById(R.id.right_msg_layout);
			viewHolder.leftMsgImg = (ImageView) convertView
					.findViewById(R.id.left_msg_img);
			viewHolder.rightMsgImg = (ImageView) convertView
					.findViewById(R.id.right_msg_img);
			viewHolder.leftMsgText = (TextView) convertView
					.findViewById(R.id.left_msg_text);
			viewHolder.rightMsgText = (TextView) convertView
					.findViewById(R.id.right_msg_text);

			viewHolder.timeOrNotify = (TextView) convertView
					.findViewById(R.id.time_or_notify);

			viewHolder.leftName = (TextView) convertView
					.findViewById(R.id.left_name);
			viewHolder.rightName = (TextView) convertView
					.findViewById(R.id.right_name);
			viewHolder.leftIconLayout = (LinearLayout) convertView
					.findViewById(R.id.left_icon_layout);
			viewHolder.rightIconLayout = (LinearLayout) convertView
					.findViewById(R.id.right_icon_layout);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Msg msg = (Msg) getItem(position);
		if (msg.from.equals("IN")) {
			viewHolder.rightIconLayout.setVisibility(View.GONE);
			viewHolder.rightMsgLayout.setVisibility(View.GONE);

			viewHolder.leftIconLayout.setVisibility(View.VISIBLE);
			viewHolder.leftMsgLayout.setVisibility(View.VISIBLE);

			viewHolder.leftIcon.setImageResource(R.drawable.ic_launcher);
			viewHolder.leftName.setText(msg.userid);

			
			String str = listMsg.get(position).msg; // 消息具体内容
			String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
			try {
				SpannableString spannableString = ExpressionUtil
						.getExpressionString(cxt, str, zhengze);
				viewHolder.leftMsgText.setText(spannableString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} else {
			viewHolder.leftIconLayout.setVisibility(View.GONE);
			viewHolder.leftMsgLayout.setVisibility(View.GONE);

			viewHolder.rightIconLayout.setVisibility(View.VISIBLE);
			viewHolder.rightMsgLayout.setVisibility(View.VISIBLE);

			viewHolder.rightIcon.setImageResource(R.drawable.ic_launcher);
			
			viewHolder.rightName.setText(msg.userid);

			String str = listMsg.get(position).msg; // 消息具体内容
			String zhengze = "f0[0-9]{2}|f10[0-7]"; // 正则表达式，用来判断消息内是否有表情
			try {
				SpannableString spannableString = ExpressionUtil
						.getExpressionString(cxt, str, zhengze);
				viewHolder.rightMsgText.setText(spannableString);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		viewHolder.timeOrNotify.setText(listMsg.get(position).date);
		return convertView;
	}

	static class ViewHolder {
		LinearLayout leftIconLayout, rightIconLayout;
		ImageView leftIcon, rightIcon;
		RelativeLayout leftMsgLayout, rightMsgLayout;
		ImageView leftMsgImg, rightMsgImg;
		TextView leftMsgText, rightMsgText, leftName, rightName, timeOrNotify;
	}

}
