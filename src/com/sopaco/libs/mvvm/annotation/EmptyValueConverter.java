package com.sopaco.libs.mvvm.annotation;

import com.sopaco.libs.mvvm.value.IValueConverter;

public class EmptyValueConverter implements IValueConverter {
	
	private EmptyValueConverter() {
		
	}

	@Override
	public Object convert(Object value, Object parameter) {
		return null;
	}

	@Override
	public Object convertBack(Object value, Object parameter) {
		return null;
	}

	public static boolean isEmpty(
			Class<? extends IValueConverter> valueConverter) {
		if(valueConverter == null) {
			return true;
		}
		return valueConverter.equals(EmptyValueConverter.class);
	}

}
