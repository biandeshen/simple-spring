package com.example.fjp.v1b.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fjp
 * @Title: TestSelect
 * @ProjectName simple-spring
 * @Description: 查询
 * @date 2020/5/209:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestSelect {
	String value();
}
