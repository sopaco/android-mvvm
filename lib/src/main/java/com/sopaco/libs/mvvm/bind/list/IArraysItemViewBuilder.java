package com.sopaco.libs.mvvm.bind.list;

import android.view.View;
import android.view.ViewGroup;

public interface IArraysItemViewBuilder<T> {
	View getView(int position, View convertView, ViewGroup parent, T itemData);
}