package com.sopaco.libs.mvvm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.text.TextUtils;
import android.view.View;

import com.anderfans.common.log.LogRoot;
import com.sopaco.libs.mvvm.annotation.BindProperty;
import com.sopaco.libs.mvvm.annotation.EmptyValueConverter;
import com.sopaco.libs.mvvm.bind.BindDescription;
import com.sopaco.libs.mvvm.bind.list.LayoutPropertyMap;
import com.sopaco.libs.mvvm.property.GenericViewPropertyBindHandler;
import com.sopaco.libs.mvvm.property.IViewPropertyBindHandler;
import com.sopaco.libs.mvvm.property.ImageViewPropertyBindHandlerWithPicasso;
import com.sopaco.libs.mvvm.property.ListViewPropertyBindHandler;
import com.sopaco.libs.mvvm.property.TextViewPropertyBindHandler;
import com.sopaco.libs.mvvm.utils.ReflectionToolkit;
import com.sopaco.libs.mvvm.value.IValueConverter;

public class VMBinder {
	
	static class VMBinderHolder {
		static final VMBinder Default;
		static {
			Default = new VMBinder();
			Default.viewPropertyHandlers.add(new TextViewPropertyBindHandler());
			Default.viewPropertyHandlers.add(new ImageViewPropertyBindHandlerWithPicasso());
			Default.viewPropertyHandlers.add(new ListViewPropertyBindHandler());
            Default.viewPropertyHandlers.add(new GenericViewPropertyBindHandler());
		}
	}
	
	public static VMBinder getDefault() {
		return VMBinderHolder.Default;
	}
	
	private List<IViewPropertyBindHandler> viewPropertyHandlers;
	
	private VMBinder() {
		viewPropertyHandlers = new ArrayList<IViewPropertyBindHandler>();
	}
	
	public void bindFlatView(View view, Object data, LayoutPropertyMap propertyMap) {
		try {
			Set<Entry<Integer, Collection<BindDescription>>> entrySet = propertyMap.getMapping().entrySet();
			Class<? extends Object> dataClazz = data.getClass();
			for(Entry<Integer, Collection<BindDescription>> pair : entrySet) {
				Collection<BindDescription> bindDesces = pair.getValue();
				for(BindDescription bindDesc : bindDesces) {
					String propertyPath = bindDesc.path;
					View ctl = null;
					if(pair.getKey() == LayoutPropertyMap.ViewRootSelf) {
						ctl = view;
					} else {
						ctl = view.findViewById(pair.getKey());
					}
					if(ctl == null) {
						continue;
					}
					String target = bindDesc.target;
					String dataPath = bindDesc.path;
					try {
						Field field = ReflectionToolkit.getDeclaredFieldRecursive(dataClazz, propertyPath);
						field.setAccessible(true);
						Object value = field.get(data);
						if(bindDesc.isDirectBindValue()) {
							bindViewProperty(null, ctl, value);						
						} else {
							Class<? extends IValueConverter> valueConverter = bindDesc.conterter;
							if(!EmptyValueConverter.isEmpty(valueConverter)) {
								value = valueConverter.newInstance().convert(value, null);
							}
	                        resolveMethodAndSet(ctl, "set" + target, value, bindDesc.parameterClazz);
						}
					} catch(NoSuchFieldException ex) {
						String getMethod = "get" + dataPath;
						Method method = ReflectionToolkit.getDeclaredMethodRecursive(dataClazz, getMethod);
						method.setAccessible(true);
						try {
							Object value = method.invoke(data);
							if(bindDesc.isDirectBindValue()) {
								bindViewProperty(null, ctl, value);						
							} else {
								Class<? extends IValueConverter> valueConverter = bindDesc.conterter;
								if(!EmptyValueConverter.isEmpty(valueConverter)) {
									value = valueConverter.newInstance().convert(value, null);
								}
		                        resolveMethodAndSet(ctl, "set" + target, value, bindDesc.parameterClazz);
							}
						} catch(Exception ex2) {
							LogRoot.error("error occurs when invoke method " + getMethod + " of " + data);
							throw new RuntimeException(ex2);
						}
					}					
				}
			}			
		} catch(Exception ex) {
            LogRoot.error(ex);
			throw new MVVMException(ex);
		}
	}
	
	public void bind(Object container, Object data) {
		Class<?> clazz = container.getClass();
		try {
			while(!isBasicView(clazz) && clazz != null) {
				Field[] fields = clazz.getDeclaredFields();
				for(Field propertyField : fields) {
					if (propertyField.isAnnotationPresent(BindProperty.class)) {
						propertyField.setAccessible(true);
						BindProperty bindTarget = propertyField.getAnnotation(BindProperty.class);
	                    String bindTargetExp = bindTarget.value();
	                    if(TextUtils.isEmpty(bindTargetExp)) {
	                    	bindTargetExp = bindTarget.Path();
	                    }
	                    Class<? extends Object> dataClazz = data.getClass();
                        try {
                            Field dataField = ReflectionToolkit.getDeclaredFieldRecursive(dataClazz, bindTargetExp);
                            dataField.setAccessible(true);
                            Object value = dataField.get(data);
                            bindProperty(container, propertyField, value, bindTarget);
                        } catch(NoSuchFieldException ex) {
                        	try {
                        		String getMethod = "get" + bindTargetExp;
                                Method method = ReflectionToolkit.getDeclaredMethodRecursive(dataClazz, getMethod);
                                method.setAccessible(true);
                                Object value = method.invoke(data);
                                bindProperty(container, propertyField, value, bindTarget);                        		
                        	} catch(Exception ex2) {
                        		if(bindTarget.ignoreError()) {
                        			LogRoot.error(ex2);
                        		} else {
                        			throw ex2;
                        		}
                        	}
                        }
					}
				}
				clazz = clazz.getSuperclass();
			}
		} catch(Exception ex) {
			throw new MVVMException(ex);
		}
	}

	private boolean isBasicView(Class<?> clazz) {
		return clazz.getPackage().getName().startsWith("android.view")
				|| clazz.getPackage().getName().startsWith("android.app")
				|| clazz.getPackage().getName().startsWith("java.lang");
	}

	private void bindProperty(Object container, Field bindTargetField, Object value, BindProperty bindAnnotation) throws Exception {
		Object bindTarget = bindTargetField.get(container);
		//old simple way
		if(!TextUtils.isEmpty(bindAnnotation.value())) {
			if(bindTarget instanceof View) {
				bindViewProperty(container, (View)bindTarget, value);
				return;
			}
			//no use to work
			try {
				bindTargetField.set(container, value);
				Method setterMethod = ReflectionToolkit.getFieldSetter(container, bindTargetField);
				setterMethod.invoke(container, value);
			} catch(Exception ex) {
				LogRoot.error("error occurs when bind property..." + bindTargetField.getName());
				throw ex;
			}
			return;
		}
		//for custom attribute
		Class<? extends IValueConverter> valueConverter = bindAnnotation.Converter();
		String target = bindAnnotation.Target();
//		String dataPath = bindAnnotation.Path();
		if(!EmptyValueConverter.isEmpty(valueConverter)) {
			value = valueConverter.newInstance().convert(value, null);
		}
        resolveMethodAndSet(bindTarget, "set" + target, value, null);
		return;
	}

    private void resolveMethodAndSet(Object bindTarget, String methodName, Object value, Class<?> parameterClazz) throws Exception{
        Class<? extends Object> clazz = bindTarget.getClass();
        Method method = null;
        try {
        	Class<? extends Object> alteredClass = getAlteredPrimitiveClass(value.getClass());
        	if(alteredClass != null) {
        		try {
        			method = clazz.getMethod(methodName, alteredClass);        			
        		} catch(NoSuchMethodException ex) {
        			method = clazz.getMethod(methodName, value.getClass());
        		}
        	} else {
        		if(parameterClazz == null) {
        			method = clazz.getMethod(methodName, value.getClass());
        		} else {
        			method = clazz.getMethod(methodName, parameterClazz);
        		}
        	}
        } catch(Exception ex) {
            Method[] methods = clazz.getMethods();
            for(Method m : methods) {
                Class<?>[] paramTypes = m.getParameterTypes();
                if(m.getName().equals(methodName) && paramTypes != null && paramTypes.length == 1) {
                    method = m;
                    break;
                }
            }
        }
        try {
        	method.invoke(bindTarget, value);        	
        } catch(Exception ex) {
        	LogRoot.debug("method invoke failure..." + bindTarget.getClass().toString() + "_" + method.getName() + "..." + value);
        	throw new RuntimeException("method invoke failure..." + bindTarget.getClass().toString() + "_" + method.getName() + "..." + value, ex);
        }
    }

    private void bindViewProperty(Object container, View bindTarget, Object value) {
		//TODO:if value is a mvvm viewattribute
		for(IViewPropertyBindHandler handler : viewPropertyHandlers) {
			if(handler.canHandle(bindTarget, value)) {
				handler.bindViewProperty(container, bindTarget, value);
				return;
			}
		}
		LogRoot.getDebugLogger().warn("property bind declared but no ViewPropertyHandler matched!");
	}
    
    private Class<? extends Object> getAlteredPrimitiveClass(Class<? extends Object> clazz) {
    	if(clazz == Integer.class) {
    		return int.class;
    	}
    	if(clazz == Long.class) {
    		return long.class;
    	}
    	if(clazz == Short.class) {
    		return short.class;
    	}
    	if(clazz == Byte.class) {
    		return byte.class;
    	}
    	if(clazz == Float.class) {
    		return float.class;
    	}
    	if(clazz == Double.class) {
    		return double.class;
    	}
    	if(clazz == Boolean.class) {
    		return boolean.class;
    	}
    	return null;
    }
}
