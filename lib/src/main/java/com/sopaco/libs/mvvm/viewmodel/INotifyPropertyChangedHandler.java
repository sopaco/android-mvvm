package com.sopaco.libs.mvvm.viewmodel;

public interface INotifyPropertyChangedHandler {
	void notifyPropertyChanged(String propertyName, Object value);
}
