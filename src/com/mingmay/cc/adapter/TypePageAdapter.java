package com.mingmay.cc.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager适配器
 * 
 * @author wwj
 * 
 */
public class TypePageAdapter extends PagerAdapter {
	// 界面列表
	private List<View> views;

	public TypePageAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position)); // 把被点击的图片放入缓存中
		return views.get(position); // 返回被点击图片对象
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}
}
