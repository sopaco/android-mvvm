package com.sopaco.libs.mvvm.bind.list;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.anderfans.data.ormlite.ICursorParser;
import com.sopaco.libs.mvvm.MVVMException;
import com.sopaco.libs.mvvm.VMBinder;
import com.sopaco.libs.mvvm.property.CommandMap;

public class MvvmCursorAdapter<T> extends CursorAdapter {

	private ICursorItemViewBuilder<T> itemViewBuilder;
	private CommandMap cmdMap;
	private LayoutPropertyMap propertyMap;
	private ICursorParser<T> cursorParser;
	
	public MvvmCursorAdapter(Context context, Cursor cursor, int itemLayoutResId, LayoutPropertyMap propertyMap, CommandMap cmdMap, ICursorParser<T> cursorParser) {
		this(context, cursor, new ImmutableCursorItemViewBuilder<T>(context, itemLayoutResId), propertyMap, cmdMap, cursorParser);
	}

	public MvvmCursorAdapter(Context context, Cursor cursor, ICursorItemViewBuilder<T> itemViewBuilder, LayoutPropertyMap propertyMap, CommandMap cmdMap, ICursorParser<T> cursorParser) {
		super(context, cursor, false);
		this.propertyMap = propertyMap;
		this.cmdMap = cmdMap;
		this.cursorParser = cursorParser;
		this.itemViewBuilder = itemViewBuilder;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if (getCursor() == null || getCursor().isClosed()) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!getCursor().moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        T itemData = null;
		try {
			itemData = cursorParser.parseFromCursor(getCursor());
			View itemView = itemViewBuilder.getView(getCursor(), convertView, parent, itemData);
			VMBinder.getDefault().bindFlatView(itemView, itemData, propertyMap);
			cmdMap.attachTo(itemView, itemData);
			return itemView;
		} catch (Exception ex) {
			throw new MVVMException(ex);
		}
    }

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		throw new MVVMException("newView not supported in MvvmCursorAdapter");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		throw new MVVMException("bindView not supported in MvvmCursorAdapter");
	}
}