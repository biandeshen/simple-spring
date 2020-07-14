package com.example.fjp.starter;

import com.example.fjp.beans.BeanFactory;
import com.example.fjp.core.ClassScanner;
import com.example.fjp.web.handler.HandlerMannager;
import com.example.fjp.web.server.TomcatServer;

import java.util.List;

/**
 * @FileName: SimpleApplication
 * @Author: admin
 * @Date: 2020/4/29 14:58
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
public class SimpleApplication {
	public static void run(Class<?> cls, String[] args) {
		System.out.println("Simple Application!");
		TomcatServer tomcatServer = new TomcatServer(args);
		try {
			// 启动tomcat服务器拦截请求
			tomcatServer.startServer();
			// 启动框架时扫描所有的类，即类扫描器
			System.out.println("启动类扫描器！");
			System.out.println("cls.getPackage().getName() = " + cls.getPackage().getName());
			List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
			// 将 bean 类交由 beanFactory 管理
			System.out.println("初始化BeanFactory！");
			BeanFactory.initBean(classList);
			// 在扫描的所有类中寻找 MappingHandler 添加进 管理程序中
			System.out.println("Mapping all URL！");
			HandlerMannager.resolverMappingHandler(BeanFactory.getBeanSet());
			for (Class<?> aClass : classList) {
				System.out.println(aClass);
			}
			System.out.println("框架初始化完成！");
		} catch (Exception e) {
			e.printStackTrace();
			//
			System.out.println("Tomcat Start Faild!!!");
		}
	}
	
	/**
	 * 关闭容器的钩子
	 */
	private static void shutDownHook(Runnable hook) {
		Runtime.getRuntime().addShutdownHook(new Thread(hook));
	}
}