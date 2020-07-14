package com.example.fjp.v1b.component;//package com.example.fjp.v1.component;
//
//import com.example.fjp.v1.factorybean.AccountMapperFactoryBean;
//import com.example.fjp.v1.factorybean.ProductMapperFactoryBean;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.stereotype.Component;
//
///**
// * @FileName: TestBeanFactoryPostProcessor
// * @Author: admin
// * @Date: 2020/5/20 15:56
// * @Description: bean工厂的后处理器
// * History:
// * <author>          <time>          <version>
// * admin           2020/5/20           版本号
// */
//
///**
// * 执行beanfactoryprocessor的执行顺序比排除Interface接口为bean的顺序靠后，参考此类
// * {@link org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider }
// *
// * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#isCandidateComponent(AnnotatedBeanDefinition)
// */
//public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//
//	@Override
//	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//		GenericBeanDefinition accountMapperBd =
//				(GenericBeanDefinition) configurableListableBeanFactory.getBeanDefinition("accountMapper");
//		System.out.println("accountMapperBd.toString() = " + accountMapperBd.toString());
//		accountMapperBd.setBeanClass(AccountMapperFactoryBean.class);
//
//		GenericBeanDefinition productMapperBd =
//				(GenericBeanDefinition) configurableListableBeanFactory.getBeanDefinition("productMapper");
//		System.out.println("productMapperBd.toString() = " + productMapperBd.toString());
//		productMapperBd.setBeanClass(ProductMapperFactoryBean.class);
//	}
//}