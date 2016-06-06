package com.sopaco.libs.mvvm.property;

import android.view.View;

public interface IViewPropertyBindHandler {

	boolean canHandle(Object property, Object value);
	
	void bindViewProperty(Object container, View property, Object value);
}
