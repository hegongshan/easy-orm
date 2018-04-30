package com.hegongshan.easy.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 属性所表示的列名，默认为""，即默认列名为驼峰命名的下划线形式，
	 * 如属性名为gmtCreate，默认列名gmt_create
	 * @return 列名 
	 */
	String value() default "";
	/**
	 * 该列若有值，是否允许被更新，默认允许
	 * @return 是否允许被更新
	 */
	boolean allowUpdate() default true;
	
	boolean isForeignKey() default false;
	
	boolean orderBy() default false;
	
	Order order() default Order.ASC;
	
	int priority() default 0;
	
}
