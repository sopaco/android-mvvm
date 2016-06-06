package com.sopaco.libs.mvvm.bind;

import com.sopaco.libs.mvvm.value.IValueConverter;

public class BindDescription {
	
	public static BindDescription create(String path) {
		return create(path, null, null);
	}
	
	public static BindDescription create(String path, String target) {
		return create(path, target, null);
	}
	
	public static BindDescription create(String path, String target, Class<? extends IValueConverter> converter) {
		BindDescription bd = new BindDescription();
		bd.path = path;
		bd.target = target;
		bd.conterter = converter;
		return bd;
	}
	
	public int resId;
	public String path;
	public String target;
	public Class<? extends IValueConverter> conterter;
	public Class<?> parameterClazz;

	public boolean isDirectBindValue() {
		return target == null;
	}
}
