package com.example.fjp.v1.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fjp
 * @Title: TestMapper
 * @ProjectName simple-spring
 * @Description: 自定义的Mapper注解
 * @date 2020/5/2117:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestMapper {}
