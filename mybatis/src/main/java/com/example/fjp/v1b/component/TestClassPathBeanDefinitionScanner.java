package com.example.fjp.v1b.component;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * @FileName: TestImportBeanDefinitionRegistry
 * @Author: admin
 * @Date: 2020/5/20 23:35
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
public class TestClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		// 此处设置支持接口
		return beanDefinition.getMetadata().isInterface();
	}
	
	public TestClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}
	
	@Override
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		return super.doScan(basePackages);
	}
}