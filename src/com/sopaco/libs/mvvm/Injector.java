package com.sopaco.libs.mvvm;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.View;

import com.sopaco.libs.mvvm.annotation.FromId;
import com.sopaco.libs.mvvm.annotation.FromIds;
import com.sopaco.libs.mvvm.utils.QArrays;

public class Injector {
    private static final Class<?>[] HALT_CLASSES = { Activity.class, Fragment.class, View.class, Object.class,
            Dialog.class };

    public static void inject(Object container) {
        inject(container, container, false);
    }

    public static void inject(Object container, boolean ignore) {
        inject(container, container, ignore);
    }

    public static void inject(Object container, Object parent) {
        inject(container, parent, false);
    }

    public static void inject(Object container, Object parent, boolean ignore) {
        Class<?> clazz = container.getClass();
        while (!QArrays.contains(HALT_CLASSES, clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            clazz = clazz.getSuperclass();
            for (Field field : fields) {
                if (field.isAnnotationPresent(FromId.class)) {
                    FromId from = field.getAnnotation(FromId.class);
                    int id = from.value();
                    field.setAccessible(true);
                    View view = inflateView(parent, id);
                    if (!ignore && !from.canBeNull() && view == null) {
                    	throw new MVVMException("view not found..." + field.getName());
                    } else if (view != null) {
                        try {
                            field.set(container, view);
                        } catch (Exception ex) {
                        	throw new MVVMException("set field failure, consider weather type compatiable..." + field.getName(), ex);
                        }
                    }
                } else if (field.isAnnotationPresent(FromIds.class)) {
                    FromIds fromArray = field.getAnnotation(FromIds.class);
                    int[] ids = fromArray.value();
                    field.setAccessible(true);
                    Class<?> componentType = field.getType().getComponentType();
                    if (componentType != null) {
                        Object grown = Array.newInstance(componentType, ids.length);
                        ArrayList<View> tempArray = new ArrayList<View>(ids.length);
                        View view;
                        for (int id : ids) {
                            view = inflateView(parent, id);
                            if (!ignore && !fromArray.canBeNull() && view == null) {
                            	throw new MVVMException("field not found for id..." + id);
                            }
                            tempArray.add(view);
                        }
                        System.arraycopy(tempArray.toArray(), 0, grown, 0, ids.length);
                        try {
                            field.set(container, grown);
                        } catch (Exception ex) {
                            throw new MVVMException(ex);
                        }
                    }

                }
            }

        }
    }

    private static View inflateView(Object parent, int id) {
        View view;
        view = null;
        if (parent instanceof Activity) {
            view = ((Activity) parent).findViewById(id);
        } else if (parent instanceof Fragment) {
            view = ((Fragment) parent).getView().findViewById(id);
        } else if (parent instanceof View) {
            view = ((View) parent).findViewById(id);
        } else if (parent instanceof Dialog) {
            view = ((Dialog) parent).findViewById(id);
        }
        return view;
    }
}
