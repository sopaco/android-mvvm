package com.sopaco.libs.mvvm.bind.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImmutableArraysItemViewBuilder<T> implements IArraysItemViewBuilder<T> {

	private int layoutId;
	private Context context;

	public ImmutableArraysItemViewBuilder(Context ctx, int layoutId) {
		this.context = ctx;
		this.layoutId = layoutId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent,
			Object itemData) {
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
		}
		return convertView;
	}

}
