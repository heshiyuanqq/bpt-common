package com.cmri.bpt.common.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cmri.bpt.common.http.HttpMethod;

@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RestMapping {
	String[] value();

	String desc() default "";

	HttpMethod method() default HttpMethod.POST;
}
