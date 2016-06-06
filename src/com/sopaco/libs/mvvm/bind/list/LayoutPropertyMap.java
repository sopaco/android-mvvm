package com.sopaco.libs.mvvm.bind.list;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import android.annotation.SuppressLint;

import com.sopaco.libs.mvvm.bind.BindDescription;
import com.sopaco.libs.mvvm.value.IValueConverter;

@SuppressLint("UseSparseArrays")
public class LayoutPropertyMap {

	public static final int ViewRootSelf = -1;

	private Map<Integer, Collection<BindDescription>> map;

	public LayoutPropertyMap() {
		map = new HashMap<Integer, Collection<BindDescription>>();
	}

	public void addToViewRoot(String propertyName) {
		add(ViewRootSelf, propertyName, null);
	}

	public void addToViewRoot(String propertyName, String target) {
		add(ViewRootSelf, propertyName, target);
	}

	public void add(int resId, String propertyName) {
		add(resId, propertyName, null, null, null);
	}

	public void add(int resId, String propertyName, String target) {
		add(resId, propertyName, target, null, null);
	}
	
	public void addWithSpecialClass(int resId, String propertyName, String target, Class<?> clazz) {
		add(resId, propertyName, target, null, clazz);
	}

	public void remove(int resId) {
		map.remove(resId);
	}

	public void add(int resId, String propertyName, String target,
			Class<? extends IValueConverter> converter, Class<?> parameterClazz) {
		BindDescription bindDesc = new BindDescription();
		bindDesc.resId = resId;
		bindDesc.path = propertyName;
		bindDesc.target = target;
		bindDesc.conterter = converter;
		bindDesc.parameterClazz = parameterClazz;

		Collection<BindDescription> bindDescriptions = map.get(resId);
		if (bindDescriptions == null) {
			map.put(resId, new HashSet<BindDescription>());
		}
		map.get(resId).add(bindDesc);
	}

	public Map<Integer, Collection<BindDescription>> getMapping() {
		return map;
	}
}
