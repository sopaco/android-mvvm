package com.sopaco.libs.mvvm.property;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.sopaco.libs.mvvm.utils.BitmapAsyncBindToolkit;
import com.sopaco.libs.mvvm.value.ValueElementVisibilityGone;

public class ImageViewPropertyBindHandlerWithPicasso implements IViewPropertyBindHandler {
	
	public void bindViewProperty(Object container, View property, final Object value) {
		property.setTag(value);
		final ImageView imgView = (ImageView)property;
        if(value instanceof ValueElementVisibilityGone) {
            imgView.setVisibility(View.GONE);
            return;
        }
        if(value == null) {
            imgView.setImageBitmap(null);
            imgView.setVisibility(View.INVISIBLE);
            return;
        }
        imgView.setVisibility(View.VISIBLE);
        if(value instanceof Integer) {
            imgView.setImageResource((Integer)value);
        } else if(value instanceof Bitmap) {
			imgView.setImageBitmap((Bitmap)value);
		} else if(value instanceof Drawable) {
			imgView.setImageDrawable((Drawable)value);
		} else {
			String uri = value.toString();
			BitmapAsyncBindToolkit.bindImageView(imgView, uri);
//            if(NetworkProtocolUtil.isNetworkResource(uri)) {
//                Picasso.with(AppBase.getAppContext()).load(uri).into(imgView);
//                return;
//            }
//			if(new File(uri).exists()) {
//                Picasso.with(AppBase.getAppContext()).load(new File(uri)).into(imgView);
//				return;
//			}
		}
	}

	@Override
	public boolean canHandle(Object property, Object value) {
		return property instanceof ImageView;
	}
}