package com.example.fjp.web.server;

import com.example.fjp.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.Tomcat.FixContextListener;
import org.apache.tomcat.util.threads.TaskThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @FileName: TomcatServer
 * @Author: admin
 * @Date: 2020/4/29 20:19
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
@SuppressWarnings("ALL")
public class TomcatServer {
	private Tomcat tomcat;
	private String[] args;
	
	public TomcatServer(String[] args) {
		this.args = args;
	}
	
	public void startServer() throws LifecycleException {
		tomcat = new Tomcat();
		tomcat.setPort(10087);
		this.tomcat.start();
		
		//final Tomcat tomcat1 = new Tomcat();
		//tomcat1.setPort(10087);
		//tomcat1.start();
		
		Context context = new StandardContext();
		context.setPath("");
		context.addLifecycleListener(new FixContextListener());
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet).setAsyncSupported(true);
		context.addServletMappingDecoded("/", "dispatcherServlet");
		this.tomcat.getHost().addChild(context);
		
		
		//Context context1 = new StandardContext();
		//context1.setPath("/2");
		//context1.addLifecycleListener(new FixContextListener());
		//
		//DispatcherServlet dispatcherServlet1 = new DispatcherServlet(){
		//	@Override
		//	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws
		//	ServletException, IOException {
		//		servletResponse.getWriter().println("tttttt");
		//	}
		//};
		//Tomcat.addServlet(context1, "dispatcherServlet1", dispatcherServlet1).setAsyncSupported(true);
		//context1.addServletMappingDecoded("/","dispatcherServlet1");
		//this.tomcat.getHost().addChild(context1);
		
		tomcatAwaitThreadStart();
	}
	
	private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
	                                                                 new LinkedBlockingQueue<>(2 << 6),
	                                                                 new TaskThreadFactory("AWAIT_TOMCAT", false, 1));
	
	//private Thread AWAIT_THREAD = new Thread("Tomcat_Await_Thread") {
	//	@Override
	//	public void run() {
	//		while (!AWAIT_THREAD.isInterrupted()) {
	//			TomcatServer.this.tomcat.getServer().await();
	//			try {
	//				TimeUnit.SECONDS.sleep(1);
	//			} catch (InterruptedException e) {
	//				// 线程睡眠异常，则调用线程终止方法
	//				tomcatAwaitThreadShutdown();
	//			}
	//		}
	//	}
	//};
	
	public void tomcatAwaitThreadStart() {
		executorService.execute(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				TomcatServer.this.tomcat.getServer().await();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// 线程睡眠异常，则调用线程终止方法
					tomcatAwaitThreadShutdown();
				}
			}
		});
		System.out.println("Tomcat started!");
	}
	
	public void tomcatAwaitThreadShutdown() {
		executorService.shutdown();
		System.out.println("Tomcat ShutDown!");
	}
}