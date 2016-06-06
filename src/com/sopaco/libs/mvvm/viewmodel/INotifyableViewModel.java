package com.sopaco.libs.mvvm.viewmodel;

public interface INotifyableViewModel {
	INotifyPropertyChangedHandler getNotifyPropertyChangedHandler();

	void setNotifyPropertyChangedHandler(
			INotifyPropertyChangedHandler notifyPropertyChangedHandler);
}