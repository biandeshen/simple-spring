package com.example.fjp;

import com.example.fjp.starter.SimpleApplication;

/**
 * @FileName: Application
 * @Author: admin
 * @Date: 2020/4/29 13:28
 * @Description: 启动类，测试引入tomcat依赖实现http服务
 * History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
public class TomcatApplication {
	public static void main(String[] args) {
		SimpleApplication.run(TomcatApplication.class, args);
	}
	
}