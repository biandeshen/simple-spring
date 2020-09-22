package com.example.fjp.web.handler;

import com.example.fjp.beans.BeanFactory;
import com.example.fjp.httpserver.v1.common.HttpMethod;
import com.example.fjp.httpserver.v1.request.HttpRequest;
import com.example.fjp.httpserver.v1.response.HttpResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.stream.Stream;

/**
 * @FileName: MappingHandler
 * @Author: admin
 * @Date: 2020/5/12 13:44
 * @Description: mappingHandler
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/12           版本号
 */
@SuppressWarnings("ALL")
public class MappingHandler {
	private String uri;
	private Method method;
	private Class<?> controller;
	private String[] args;
	private String[] reqMethods;
	
	public void handler(ServletRequest req, ServletResponse resp) throws IllegalAccessException,
	                                                                     InstantiationException,
	                                                                     InvocationTargetException, IOException {
		Object[] parameters = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			// 此处将原本args中存储的参数名称替换为请求中的参数值，
			// 由于正常请求中参数允许为空，故此处允许空值
			parameters[i] = req.getParameter(args[i]);
		}
		Object ctl = BeanFactory.getBean(controller);
		Object response = method.invoke(ctl, parameters);
		resp.getWriter().println(response.toString());
	}
	
	public void handler(HttpRequest httpRequest, HttpResponse httpResponse) throws InvocationTargetException,
	                                                                               IllegalAccessException,
	                                                                               UnsupportedEncodingException {
		Object[] parameters = new Object[args.length];
		String httpMethod = httpRequest.getMethod();
		
		if (Stream.of(reqMethods).anyMatch(httpMethod::equals)) {
			for (int i = 0; i < args.length; i++) {
				// 此处将原本args中存储的参数名称替换为请求中的参数值，
				// 由于正常请求中参数允许为空，故此处允许空值
				// TODO 增加对 multipart/form-data 和 application/x-www-form-urlencoded 的处理
				parameters[i] = URLDecoder.decode(httpRequest.getParameter(args[i]), "UTF-8");
			}
			Object ctl = BeanFactory.getBean(controller);
			Object response = method.invoke(ctl, parameters);
			httpResponse.setResponseBody(response.toString());
		} else {
			//	抛出不支持的方法异常
			throw new IllegalAccessException("UNSUPPORTED REQUEST METHOD TYPE");
		}
		
	}
	
	MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
		this.uri = uri;
		this.method = method;
		this.controller = controller;
		this.args = args;
		// requestMethod 默认请求方法为 GET
		this.reqMethods = new String[]{HttpMethod.GET.getName()};
	}
	
	MappingHandler(String uri, Method method, Class<?> controller, String[] args, HttpMethod[] reqMethods) {
		this.uri = uri;
		this.method = method;
		this.controller = controller;
		this.args = args;
		//this.reqMethods = Stream.of(reqMethods).map(HttpMethod::getName).collect(Collectors.toList()).toArray(new String[0]);
		this.reqMethods = Stream.of(reqMethods).map(HttpMethod::getName).toArray(String[]::new);
		// 请求方法为空判断
		if (this.reqMethods == null || this.reqMethods.length == 0) {
			// requestMethod 默认请求方法为 GET
			this.reqMethods = new String[]{HttpMethod.GET.getName()};
		}
	}
}