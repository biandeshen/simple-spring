package com.example.fjp.web.servlet;

import com.example.fjp.httpserver.v1.common.HttpHeaders;
import com.example.fjp.httpserver.v1.request.AbstractHttpHandler;
import com.example.fjp.httpserver.v1.request.HttpRequest;
import com.example.fjp.httpserver.v1.response.HttpResponse;
import com.example.fjp.web.handler.HandlerMannager;
import com.example.fjp.web.handler.MappingHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @FileName: DispatcherHandlerImpl
 * @Author: fjp
 * @Date: 2020/7/23 14:22
 * @Description: History:
 * <author>          <time>          <version>
 * fjp           2020/7/23           版本号
 */
public class DispatcherHandler extends AbstractHttpHandler {
	
	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		String uri = httpRequest.getRequestURI();
		MappingHandler mappingHandler = HandlerMannager.mappingHandlerHashMap.get(uri);
		if (mappingHandler != null) {
			try {
				mappingHandler.handler(httpRequest, httpResponse);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			HttpHeaders httpHeaders = new HttpHeaders(new HashMap<>(8));
			httpResponse.setHeaders(httpHeaders);
			httpResponse.getHeaders().setEncoding("UTF-8");
			httpResponse.getHeaders().setContentType("text/html");
			httpResponse.setResponseBody("<body><p>不存在的请求路径！</p></body>");
		}
		
	}
}