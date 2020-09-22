package com.example.fjp.v1b;

import com.example.fjp.v1b.config.SpringMybatisConfig;
import com.example.fjp.v1b.dao.AccountMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @FileName: MainTest
 * @Author: admin
 * @Date: 2020/5/20 9:42
 * @Description: MainClass
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
public class MainTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringMybatisConfig.class);
		String[] beanDefinitionNames = context.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println("beanDefinitionName = " + beanDefinitionName);
		}
		AccountMapper accountMapper = (AccountMapper) context.getBean("accountMapper");
		accountMapper.qryById("111", "false");
		
	}
	
}