package com.example.fjp.web.handler;

import com.example.fjp.beans.BeanFactory;
import com.example.fjp.httpserver.v1.request.HttpRequest;
import com.example.fjp.httpserver.v1.response.HttpResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

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
	
	public void handler(HttpRequest httpRequest, HttpResponse httpResponse) throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
		Object[] parameters = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			// 此处将原本args中存储的参数名称替换为请求中的参数值，
			// 由于正常请求中参数允许为空，故此处允许空值
			//parameters[i] = new String(httpRequest.getParameter(args[i]).getBytes("iso8859-1"),"UTF-8");
			parameters[i] = URLDecoder.decode(httpRequest.getParameter(args[i]),"UTF-8");
			
		}
		Object ctl = BeanFactory.getBean(controller);
		Object response = method.invoke(ctl, parameters);
		httpResponse.setResponseBody(response.toString());
		
	}
	
	MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
		this.uri = uri;
		this.method = method;
		this.controller = controller;
		this.args = args;
	}
}