package com.example.fjp.v1.anno;

import java.lang.annotation.*;

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
