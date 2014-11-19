package com.mingmay.cc.ui.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingmay.cc.R;
import com.mingmay.cc.model.Tag;
import com.mingmay.cc.task.LoadTagTask;

public class TypeFragment extends Fragment {
	private int width;
	private float density;
	private ListView listview;
    private LinearLayout tagContainer;
	private ArrayList<Tag> tags;
    
	private ProgressBar loadingTag;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_category_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		width = getResources().getDisplayMetrics().widthPixels;
		density = getResources().getDisplayMetrics().density;
		listview = (ListView) getView().findViewById(R.id.listview);
		tagContainer = (LinearLayout) getView().findViewById(R.id.tag_container);
		loadingTag=(ProgressBar) getView().findViewById(R.id.loading_tag);
		loadTags();
	}

	private void loadTags() {
		LoadTagTask task = new LoadTagTask(this);
		task.execute();
	}
	public void callBackTag(ArrayList<Tag> tags) {
		// TODO Auto-generated method stub
		this.tags = tags;
		if (tags != null&&tags.size()>0) {
			loadingTag.setVisibility(View.GONE);
			tagContainer.removeAllViews();
			for (Tag tag : tags) {
				TextView tagView = new TextView(getActivity());
				tagView.setText(tag.name);
				tagView.setGravity(Gravity.CENTER);
				LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(-2, -2);
				param.setMargins(5, 5, 5, 5);
				tagContainer.addView(tagView, param);
			}
		}
	}

}
