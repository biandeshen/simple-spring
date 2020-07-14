package com.example.fjp.web.mvc;

import java.lang.annotation.*;

/**
 * @author fjp
 * @Title: RequestParam
 * @ProjectName simple-spring
 * @Description: TODO
 * @date 2020/5/1210:33
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
	String value();
}
