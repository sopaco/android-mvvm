package com.sopaco.libs.mvvm.utils;

import java.util.ArrayList;
import java.util.List;

import com.sopaco.libs.mvvm.viewmodel.ViewModelBase;

public class ViewModelConverter {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <X extends ViewModelBase> List<X> convertToViewModel(Class<X> clazz, List<? extends Object> srcList) {
        ArrayList<X> ret = new ArrayList<X>();
        for(Object obj : srcList) {
            X viewModeObj = null;
            try {
                viewModeObj = clazz.newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            viewModeObj.wrap(obj);
            ret.add(viewModeObj);
        }
        return ret;
    }
}