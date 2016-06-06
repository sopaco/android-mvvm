package com.sopaco.libs.mvvm.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sopaco.libs.mvvm.value.IValueConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
@Inherited
public @interface BindProperty {

    public abstract String value() default "";

    public abstract boolean canBeNull() default true;

    /**
     * 绑定到View的目标属性
     * @return
     */
    public abstract String Target() default "";

    /**
     * 绑定路径
     * @return
     */
    public abstract String Path() default "";

    /**
     * 值转换器类型
     * @return
     */
    public abstract Class<? extends IValueConverter> Converter() default EmptyValueConverter.class;
    
    public abstract Class<?> ParameterClazz() default Object.class;

	public abstract boolean ignoreError() default false;

    //java not supported...public abstract BindDescription multibinder() default null;
}