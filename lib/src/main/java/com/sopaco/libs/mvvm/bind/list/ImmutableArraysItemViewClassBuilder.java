package com.sopaco.libs.mvvm.bind.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ImmutableArraysItemViewClassBuilder<T> implements IArraysItemViewBuilder<T> {

	private Context context;
	private Class<? extends View> viewClazz;

	public ImmutableArraysItemViewClassBuilder(Context ctx, Class<? extends View> viewClazz) {
		this.context = ctx;
		this.viewClazz = viewClazz;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent,
			Object itemData) {
		if(convertView == null) {
			try {
				convertView = viewClazz.getConstructor(Context.class).newInstance(context);
			} catch (
					Exception ex) {
				throw new RuntimeException("no constructor matched, please provide one with Context", ex);
			}
		}
		return convertView;
	}

}