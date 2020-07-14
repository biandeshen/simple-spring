package com.example.fjp.web.mvc;

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
	String value();
}
