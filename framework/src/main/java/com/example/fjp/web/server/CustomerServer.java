package com.example.fjp.web.server;

import com.example.fjp.httpserver.v1.core.AbstractHttpServer;
import com.example.fjp.web.servlet.DispatcherHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @FileName: TomcatServer
 * @Author: admin
 * @Date: 2020/4/29 20:19
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
@SuppressWarnings("ALL")
public class CustomerServer {
	private AbstractHttpServer httpServer;
	private String[] args;
	
	public CustomerServer(String[] args) {
		this.args = args;
	}
	
	public void startServer() throws IOException {
		httpServer = AbstractHttpServer.create(new InetSocketAddress(9999), 1);
		httpServer.createContext("/", new DispatcherHandler());
		start();
	}
	
	
	public void start() {
		this.httpServer.start();
		System.out.println("HttpServer started!");
	}
	
	public void stop(int second) {
		this.httpServer.stop(1);
		System.out.println("HttpServer ShutDown!");
	}
}