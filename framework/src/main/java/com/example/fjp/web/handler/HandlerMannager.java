package com.example.fjp.web.handler;

import com.example.fjp.httpserver.v1.common.HttpMethod;
import com.example.fjp.web.mvc.Controller;
import com.example.fjp.web.mvc.RequestMapping;
import com.example.fjp.web.mvc.RequestParam;

import java.lang.annotation.Annotation;
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
			// TODO 方法注解
			HttpMethod[] reqMethod = method.getDeclaredAnnotation(RequestMapping.class).method();
			List<String> paramNameList = new ArrayList<>();
			for (Parameter parameter : method.getParameters()) {
				Annotation[] parameterAnnotations = parameter.getAnnotations();
				if (parameterAnnotations.length == 0) {
					// TODO 解析为普通的 java bean 对象 通过构造方法创建
					
				}
				// TODO 或许存在多个注解，若冲突，则异常；否则尝试链式调用
				if (parameterAnnotations.length == 1) {
					Annotation annotation = parameterAnnotations[0];
					if (annotation instanceof RequestParam) {
						paramNameList.add(((RequestParam) annotation).value());
					}
					//	TODO 其他方法参数注解
				}
				//Stream.of(parameterAnnotations).
				
				// TODO 若未加注解 则应解析为普通 POJO 若为其他类型，则应根据类型解析为 对应的类型
				
			}
			String[] params = paramNameList.toArray(new String[0]);
			// 请求方法设置
			MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params, reqMethod);
			mappingHandlerList.add(mappingHandler);
			MappingHandler putIfAbsent = mappingHandlerHashMap.putIfAbsent(uri, mappingHandler);
			if (putIfAbsent != null) {
				throw new IllegalArgumentException("MappingHandler 存在相同的路径定义！");
			}
		}
	}
}