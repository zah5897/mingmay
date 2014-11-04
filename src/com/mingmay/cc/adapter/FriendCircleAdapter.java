package com.mingmay.cc.adapter;

import java.util.ArrayList;

import com.mingmay.cc.R;
import com.mingmay.cc.app.cache.ImageLoader;
import com.mingmay.cc.model.Clothesinfo;
import com.mingmay.cc.ui.FriendCirclePage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FriendCircleAdapter extends BaseAdapter {

	private ArrayList<Clothesinfo> data;
	private FriendCirclePage page;
	private ImageLoader imgLoader;

	public FriendCircleAdapter(FriendCirclePage page,
			ArrayList<Clothesinfo> data) {
		this.page = page;
		this.data = data;
		imgLoader = new ImageLoader(page.getActivity());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (arg1 == null) {
			arg1 = page.getActivity().getLayoutInflater()
					.inflate(R.layout.layout_item_friend_circle, null);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) arg1.findViewById(R.id.head_icon);
			viewHolder.clothesImg = (ImageView) arg1
					.findViewById(R.id.detial_img);
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		Clothesinfo info = (Clothesinfo) getItem(arg0);
		imgLoader.DisplayImage(info.createByImg, viewHolder.icon);
		imgLoader.DisplayImage(info.clothesImage, viewHolder.clothesImg);
		return arg1;
	}

	static class ViewHolder {
		ImageView icon, clothesImg;

	}
}
