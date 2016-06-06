package com.sopaco.libs.mvvm.utils;

import java.io.File;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.anderfans.common.Action;
import com.anderfans.common.AppBase;
import com.anderfans.common.ResultAction;
import com.anderfans.common.cache.BitmapCache;
import com.squareup.picasso.Picasso;

public class BitmapAsyncBindToolkit {
	
	public static int holderImageResId;
	
	public static void bindImageView(final ImageView imageView, int holderResId, final String uri) {
		if(NetworkProtocolUtil.isNetworkResource(uri)) {
			Picasso.with(AppBase.getAppContext()).load(uri).placeholder(holderResId).into(imageView);			
		} else {
			Picasso.with(AppBase.getAppContext()).load(new File(uri)).placeholder(holderResId).into(imageView);
		}
	}
	
	public static void bindImageView(final ImageView imageView, final String uri) {
		if(holderImageResId != 0) {
			bindImageView(imageView, holderImageResId, uri);
			return;
		}
		if(NetworkProtocolUtil.isNetworkResource(uri)) {
			Picasso.with(AppBase.getAppContext()).load(uri).into(imageView);			
		} else {
			Picasso.with(AppBase.getAppContext()).load(new File(uri)).into(imageView);
		}
//		imageView.setTag(uri);
//		bindImagaProperty(uri, new Action<Bitmap>() {
//			@Override
//			public void execute(Bitmap bitmap) {
//				imageView.setImageBitmap(bitmap);
//			}
//		}, new ResultAction<Boolean>() {
//			@Override
//			public Boolean execute() {
//				return uri.equals(imageView.getTag());
//			}
//		});
	}
	
	public static void bindImagaProperty(String uri, final Action<Bitmap> onResultCompleted, final ResultAction<Boolean> cancelHandler) {
		if(NetworkProtocolUtil.isNetworkResource(uri)) {
			BitmapCache.Instance.getRemoteImageOrCacheAsync(uri, new Action<Bitmap>() {
				@Override
				public void execute(final Bitmap bitmap) {
	                if(bitmap == null) {
	                    return;
	                }
	                if(!cancelHandler.execute()) {
						return;
					}
					AppBase.runOnDispatcher(new Runnable() {
						@Override
						public void run() {
							if(!cancelHandler.execute()) {
								return;
							}
							onResultCompleted.execute(bitmap);
						}
					});
				}
			}, new ResultAction<Boolean>() {
				@Override
				public Boolean execute() {
					return cancelHandler.execute();
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
							if(!cancelHandler.execute()) {
								return;
							}
							onResultCompleted.execute(bitmap);
						}
					});
				}
			});
			return;
		}
	}
}
