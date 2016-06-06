package com.sopaco.libs.mvvm.property;

import android.view.View;
import android.widget.TextView;

import com.sopaco.libs.mvvm.value.ValueElementVisibilityGone;

public class TextViewPropertyBindHandler implements IViewPropertyBindHandler {
	public void bindViewProperty(Object container, View property, Object value) {
        if(value instanceof ValueElementVisibilityGone) {
            property.setVisibility(View.GONE);
            return;
        }
        if(value == null) {
            value = "";
        }
		((TextView)property).setText(value.toString());	
	}

	@Override
	public boolean canHandle(Object property, Object value) {
		if(!(value instanceof String) && !(value instanceof Integer)) {
			return false;
		}
		return property instanceof TextView;
	}
}
