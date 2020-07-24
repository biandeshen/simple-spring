package com.example.fjp.web.handler;

import com.example.fjp.web.mvc.Controller;
import com.example.fjp.web.mvc.RequestMapping;
import com.example.fjp.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName: HandlerMannager
 * @Author: admin
 * @Date: 2020/5/12 13:45
 * @Description: 处理程序管理器
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/12           版本号
 */
public class HandlerMannager {
	public static List<MappingHandler> mappingHandlerList = new ArrayList<>();
	
	public static Map<String, MappingHandler> mappingHandlerHashMap = new HashMap<>();
	
	public static void resolverMappingHandler(List<Class<?>> classList) {
		for (Class<?> cls : classList) {
			if (cls.isAnnotationPresent(Controller.class)) {
				parseHandlerFromController(cls);
			}
			
		}
	}
	
	private static void parseHandlerFromController(Class<?> cls) {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(RequestMapping.class)) {
				continue;
			}
			String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
			List<String> paramNameList = new ArrayList<>();
			for (Parameter parameter : method.getParameters()) {
				if (parameter.isAnnotationPresent(RequestParam.class)) {
					paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
				}
			}
			String[] params = paramNameList.toArray(new String[0]);
			MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
			mappingHandlerList.add(mappingHandler);
			MappingHandler putIfAbsent = mappingHandlerHashMap.putIfAbsent(uri, mappingHandler);
			if (putIfAbsent != null) {
				throw new IllegalArgumentException("MappingHandler 存在相同的路径定义！");
			}
		}
	}
	
}