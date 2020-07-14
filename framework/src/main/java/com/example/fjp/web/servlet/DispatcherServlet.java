package com.example.fjp.web.servlet;

import com.example.fjp.web.handler.HandlerMannager;
import com.example.fjp.web.handler.MappingHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

/**
 * @FileName: DispatcherServlet
 * @Author: admin
 * @Date: 2020/4/29 21:24
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/4/29           版本号
 */
public class DispatcherServlet implements Servlet {
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
	}
	
	@Override
	public ServletConfig getServletConfig() {
		return null;
	}
	
	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException,
	                                                                                           IOException {
		//for (MappingHandler mappingHandler : HandlerMannager.mappingHandlerList) {
		//	//if (mappingHandler.handler(req, res)) {
		//	//	return;
		//	//}
		//
		//}
		String uri = ((HttpServletRequest) servletRequest).getRequestURI();
		MappingHandler mappingHandler = HandlerMannager.mappingHandlerHashMap.get(uri);
		if (mappingHandler != null) {
			try {
				mappingHandler.handler(servletRequest, servletResponse);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			//throw new NullPointerException("不存在的请求路径！");
			servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			servletResponse.setContentType("text/html");
			servletResponse.getWriter().println("不存在的请求路径！");
		}
		
	}
	
	@Override
	public String getServletInfo() {
		return null;
	}
	
	@Override
	public void destroy() {
	}
}