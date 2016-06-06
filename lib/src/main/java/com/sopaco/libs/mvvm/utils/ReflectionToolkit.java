package com.sopaco.libs.mvvm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;

import com.anderfans.common.log.LogRoot;

public class ReflectionToolkit {
	@SuppressLint("DefaultLocale")
	public static Method getFieldSetter(Object obj, Field field) throws Exception {
		String name = field.getName();
		String setterName = null;
		if(name.length() == 1) {
			setterName = name.toUpperCase();
		}
		setterName = (name.charAt(0) + "").toUpperCase() + name.substring(1);
		return obj.getClass().getMethod(setterName);
	}
	
	public static Field getDeclaredFieldRecursive(Class<?> clazz, String name) throws Exception {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                Field result = c.getDeclaredField(name);
                if (result != null) {
                    return result;
                }
            } catch(Exception NoSuchMethodException) {
                //LogRoot.debug("field not found..." + name + " in class " + clazz.getName() + ", recursive to continue");
            }
        }
        throw new NoSuchFieldException("field not found..." + name + " in class " + clazz.getName());
    }

	public static Method getDeclaredMethodRecursive(
			Class<? extends Object> clazz, String name) throws Exception {
		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                Method result = c.getDeclaredMethod(name);
                if (result != null) {
                    return result;
                }
            } catch(Exception NoSuchMethodException) {
                LogRoot.debug("method not found..." + name + " in class " + clazz.getName() + ", recursive to continue");
            }
        }
        throw new NoSuchFieldException("method not found..." + name + " in class " + clazz.getName());
	}
}
