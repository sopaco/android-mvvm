package com.sopaco.libs.mvvm.value;

public interface IValueConverter {
	Object convert(Object value, Object parameter);
	Object convertBack(Object value, Object parameter);
}