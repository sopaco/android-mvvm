package com.sopaco.libs.mvvm.property;


import java.io.File;

import com.anderfans.common.Action;
import com.anderfans.common.AppBase;
import com.anderfans.common.ResultAction;
import com.anderfans.common.cache.BitmapCache;
import com.sopaco.libs.mvvm.utils.AnimationGalleryHelper;
import com.sopaco.libs.mvvm.utils.NetworkProtocolUtil;
import com.sopaco.libs.mvvm.value.ValueElementVisibilityGone;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class ImageViewPropertyBindHandler implements IViewPropertyBindHandler {
	
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
            if(NetworkProtocolUtil.isNetworkResource(uri)) {
                BitmapCache.Instance.getRemoteImageOrCacheAsync(uri, new Action<Bitmap>() {
                    @Override
                    public void execute(final Bitmap bitmap) {
                        if(bitmap == null) {
                            return;
                        }
                        AppBase.runOnDispatcher(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setImageBitmap(bitmap);
                                AnimationGalleryHelper.alphaTransitionOut(imgView.getContext(), imgView);
                            }
                        });
                    }
                }, new ResultAction<Boolean>() {
                    @Override
                    public Boolean execute() {
                        return value.equals(imgView.getTag());
                    }
                });
                return;
            }
			if(new File(uri).exists()) {
				BitmapCache.Instance.getFileImageOrCacheAsync(uri, new Action<Bitmap>() {
					@Override
					public void execute(final Bitmap bitmap) {
                        if(bitmap == null) {
                            return;
                        }
						AppBase.runOnDispatcher(new Runnable() {
							@Override
							public void run() {
								imgView.setImageBitmap(bitmap);
                                AnimationGalleryHelper.alphaTransitionOut(imgView.getContext(), imgView);
							}
						});
					}
				});
				return;
			}
		}
	}

	@Override
	public boolean canHandle(Object property, Object value) {
		return property instanceof ImageView;
	}
}
