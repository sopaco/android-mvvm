package com.sopaco.libs.mvvm.bind.list;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

public interface ICursorItemViewBuilder<T> {
	View getView(Cursor cursor, View convertView, ViewGroup parent, T itemData);
}
