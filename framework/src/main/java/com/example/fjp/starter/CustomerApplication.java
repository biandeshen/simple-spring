package com.example.fjp.starter;

import com.example.fjp.beans.BeanFactory;
import com.example.fjp.core.ClassScanner;
import com.example.fjp.web.handler.HandlerMannager;
import com.example.fjp.web.server.CustomerServer;
import com.example.fjp.web.server.TomcatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @FileName: SimpleApplication
 * @Author: admin
 * @Date: 2020/4/29 14:58
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
@SuppressWarnings("ALL")
public class CustomerApplication {
	private static final Logger logger = LoggerFactory.getLogger(CustomerApplication.class);
	
	public static void run(Class<?> cls, String[] args) {
		logger.debug("Simple Application...");
		CustomerServer customerServer = new CustomerServer(args);
		try {
			// 启动tomcat服务器拦截请求
			customerServer.startServer();
			// 启动框架时扫描所有的类，即类扫描器
			logger.debug("classScanner scanning...");
			logger.debug("current package >>>> {}", cls.getPackage().getName());
			List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
			// 将 bean 类交由 beanFactory 管理
			logger.debug("beanfactory initializing...");
			BeanFactory.initBean(classList);
			logger.debug("beanfactory initialized!");
			// 在扫描的所有类中寻找 MappingHandler 添加进 管理程序中
			logger.debug("Mapping all URL");
			HandlerMannager.resolverMappingHandler(BeanFactory.getBeanSet());
			logger.info("SIMPLE-SPRING FRAMEWORK STARTING SUCCESS");
		} catch (Exception e) {
			logger.error("SIMPLE-SPRING FRAMEWORK FAIL TO START!!! {}", e.toString());
		}
	}
	
	/**
	 * 关闭容器的钩子
	 */
	private static void shutDownHook(Runnable hook) {
		Runtime.getRuntime().addShutdownHook(new Thread(hook));
	}
}