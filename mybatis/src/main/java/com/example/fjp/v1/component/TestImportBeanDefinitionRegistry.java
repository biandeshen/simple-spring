package com.example.fjp.v1.component;

import com.example.fjp.v1.anno.EnableMapperScanner;
import com.example.fjp.v1.factorybean.TestFactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * @FileName: TestImportBeanDefinitionRegistry
 * @Author: admin
 * @Date: 2020/5/20 23:41
 * @Description: 在此处启用对自定义scanner类的使用，以支持scanner中启用接口扫描为bean
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
@SuppressWarnings("ALL")
public class TestImportBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {
	
	private static Class targetClass = TestFactoryBean.class;
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes annotationAttributes =
				(AnnotationAttributes) importingClassMetadata.getAnnotationAttributes(EnableMapperScanner.class.getName());
		// 没有配置 EnbaleMapperScanner注解
		if (annotationAttributes == null) {
			return;
		}
		
		// 获取配置支持的路径
		String[] basePackages = annotationAttributes.getStringArray("basePackages");
		
		// 扫描bean的定义
		TestClassPathBeanDefinitionScanner testClassPathBeanDefinitionScanner =
				new TestClassPathBeanDefinitionScanner(registry);
		// 此时 scanner 中默认实现的是过滤接口， 此处放行接口 也可放行所有
		testClassPathBeanDefinitionScanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> metadataReader.getClassMetadata().isInterface());
		
		// 此时扫描出的bean定义为 接口类型
		// 指定扫描的包下的接口类型bean定义
		// 批量导入bean的定义
		for (String basePackage : basePackages) {
			Set<BeanDefinitionHolder> beanDefinitionHolders = testClassPathBeanDefinitionScanner.doScan(basePackage);
			for (BeanDefinitionHolder bdh : beanDefinitionHolders) {
				//获取bean定义
				GenericBeanDefinition beanDefinition = (GenericBeanDefinition) bdh.getBeanDefinition();
				//拿到定义中的接口的class字符串
				String sourceClass = beanDefinition.getBeanClassName();
				System.out.println("原始接口的class类型：" + sourceClass);
				// 实例化接口的类型 代理后的FactoryBean
				beanDefinition.setBeanClass(targetClass);
				// 为自定义的FactoryBean设置构造方法的参数，以获取具体的返回类型
				beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(sourceClass);
			}
			System.out.println("beanDefinitionHolders = " + beanDefinitionHolders);
		}
	}
}