package com.example.fjp.web.mvc;

import com.example.fjp.httpserver.v1.common.HttpMethod;

import java.lang.annotation.*;

/**
 * @author fjp
 * @Title: RequestMapping
 * @ProjectName simple-spring
 * @Description: TODO
 * @date 2020/5/1210:30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	// 路径
	String value();
	
	// 方法
	HttpMethod[] method() default {HttpMethod.GET};
}
