package com.example.fjp.v1b.config;

import com.example.fjp.v1b.anno.EnableMapperScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName: SpringMybatisConfig
 * @Author: admin
 * @Date: 2020/5/20 9:46
 * @Description: mybatis的配置文件
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
@Configuration
@ComponentScan(value = {"com.example.fjp.v1b"})
@EnableMapperScanner(basePackages = {"com.example.fjp.v1b.dao"})
public class SpringMybatisConfig {
	
}