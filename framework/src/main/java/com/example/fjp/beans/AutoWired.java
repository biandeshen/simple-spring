package com.example.fjp.beans;

import java.lang.annotation.*;

/**
 * @author fjp
 * @Title: AutoWired
 * @ProjectName simple-spring
 * @Description: TODO
 * @date 2020/5/1215:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoWired {}
