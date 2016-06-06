package com.sopaco.libs.mvvm.viewmodel;

/**
 * Created by meng.jiang on 2014/12/30.
 */
public interface IViewModel<T> {
    boolean hasDataPrepared();

    void wrap(T args);

    void merge(T args);
}