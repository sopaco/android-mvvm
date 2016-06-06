package com.sopaco.libs.mvvm.bind.list;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImmutableCursorItemViewBuilder<T> implements ICursorItemViewBuilder<T> {
	private int layoutId;
	private Context context;

	public ImmutableCursorItemViewBuilder(Context ctx, int layoutId) {
		this.context = ctx;
		this.layoutId = layoutId;
	}

	@Override
	public View getView(Cursor cursor, View convertView, ViewGroup parent,
			T itemData) {
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
		}
		return convertView;
	}
}
