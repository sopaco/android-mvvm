package com.sopaco.libs.mvvm.viewmodel;

import java.io.Serializable;

/**
 * Created by meng.jiang on 2014/12/30.
 */
public abstract class ViewModelBase<T> implements IViewModel<T>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected T data;

    @Override
    public void wrap(T args) {
        data = args;
    }
    
    @Override
	public void merge(T args) {
		
	}

    @Override
    public boolean hasDataPrepared() {
        return data == null;
    }

    public T getRawData() {
        return data;
    }
}