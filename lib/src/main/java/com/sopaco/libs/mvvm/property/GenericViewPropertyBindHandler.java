package com.sopaco.libs.mvvm.property;

import android.view.View;

import com.sopaco.libs.mvvm.value.ValueElementVisibilityGone;
import com.sopaco.libs.mvvm.value.ValueElementVisibilityVisible;

/**
 * Created by meng.jiang on 2015/1/24.
 */
public class GenericViewPropertyBindHandler implements IViewPropertyBindHandler {
    @Override
    public boolean canHandle(Object property, Object value) {
        return property instanceof View;
    }

    @Override
    public void bindViewProperty(Object container, View property, Object value) {
        if(value instanceof ValueElementVisibilityVisible) {
            property.setVisibility(View.VISIBLE);
            return;
        }
        if(value instanceof ValueElementVisibilityGone) {
            property.setVisibility(View.GONE);
            return;
        }
    }
}
