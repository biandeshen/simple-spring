package com.example.fjp;


import com.example.fjp.starter.CustomerApplication;

/**
 * @FileName: Application
 * @Author: admin
 * @Date: 2020/4/29 13:28
 * @Description: 启动类，测试自定义实现的http服务
 * History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
public class HttpServerApplication {
	public static void main(String[] args) {
		CustomerApplication.run(HttpServerApplication.class, args);
	}
}