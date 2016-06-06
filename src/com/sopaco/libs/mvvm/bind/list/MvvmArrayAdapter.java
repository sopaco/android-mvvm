package com.sopaco.libs.mvvm.bind.list;

import java.util.List;

import com.sopaco.libs.mvvm.VMBinder;
import com.sopaco.libs.mvvm.property.CommandMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MvvmArrayAdapter<T> extends ArrayAdapter<T> {

	private IArraysItemViewBuilder<T> itemViewBuilder;
	private CommandMap cmdMap;
	private LayoutPropertyMap propertyMap;
	
	public MvvmArrayAdapter(Context context, List<T> dataSource, int itemLayoutResId, LayoutPropertyMap propertyMap, CommandMap cmdMap) {
		this(context, dataSource, new ImmutableArraysItemViewBuilder<T>(context, itemLayoutResId), propertyMap, cmdMap);
	}

	public MvvmArrayAdapter(Context context, List<T> dataSource, IArraysItemViewBuilder<T> itemViewBuilder, LayoutPropertyMap propertyMap, CommandMap cmdMap) {
		super(context, 0, dataSource);
		this.propertyMap = propertyMap;
		this.cmdMap = cmdMap;
		this.itemViewBuilder = itemViewBuilder;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		T itemData = super.getItem(position);
		View itemView = itemViewBuilder.getView(position, convertView, parent, itemData);
		VMBinder.getDefault().bindFlatView(itemView, itemData, propertyMap);
		cmdMap.attachTo(itemView, itemData);
		return itemView;
	}

}
