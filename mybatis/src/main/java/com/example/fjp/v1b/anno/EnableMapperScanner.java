package com.example.fjp.v1b.anno;

import com.example.fjp.v1.component.TestImportBeanDefinitionRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName: EnableMapperScanner
 * @Author: admin
 * @Date: 2020/5/21 0:12
 * @Description: 启用自定义的scanner以实现支持接口扫描为bean
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/21           版本号
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({TestImportBeanDefinitionRegistry.class})
public @interface EnableMapperScanner {
	String[] basePackages();
}