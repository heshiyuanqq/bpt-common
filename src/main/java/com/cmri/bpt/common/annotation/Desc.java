package com.cmri.bpt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cmri.bpt.common.base.IOMode;

@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Desc {
	// 描述文本
	String value() default "";

	IOMode mode() default IOMode.InOut;

	// 默认值
	String defValue() default "";

	/**
	 * 泛型类型数组
	 */
	Class<?>[] xType() default {};

	boolean ignore() default false;

}
