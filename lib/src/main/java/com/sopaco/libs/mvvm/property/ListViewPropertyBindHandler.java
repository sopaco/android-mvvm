package com.sopaco.libs.mvvm.property;

import android.view.View;
import android.widget.ListView;

public class ListViewPropertyBindHandler implements IViewPropertyBindHandler {

	@Override
	public void bindViewProperty(Object container, View property, Object value) {
		
	}

	@Override
	public boolean canHandle(Object property, Object value) {
		return property instanceof ListView;
	}

}
