package com.sopaco.libs.mvvm.bind;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
@Inherited
public @interface BindCommand {
	public abstract BindingCommandType commandType();
	public abstract String commandName();
    public abstract boolean canBeNull() default false;
}
