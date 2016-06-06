package com.sopaco.libs.mvvm.viewmodel;

public class NotifiableViewModelBase<T> extends ViewModelBase<T> implements INotifyableViewModel {

	public INotifyPropertyChangedHandler notifyPropertyChangedHandler;

	public INotifyPropertyChangedHandler getNotifyPropertyChangedHandler() {
		return notifyPropertyChangedHandler;
	}

	public void setNotifyPropertyChangedHandler(
			INotifyPropertyChangedHandler notifyPropertyChangedHandler) {
		this.notifyPropertyChangedHandler = notifyPropertyChangedHandler;
	}

	protected void notifyPropertyChanged(String propertyName, Object value) {
		if(notifyPropertyChangedHandler != null) {
			notifyPropertyChangedHandler.notifyPropertyChanged(propertyName, value);
		}
	}

}
