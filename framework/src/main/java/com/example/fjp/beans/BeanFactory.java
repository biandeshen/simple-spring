package com.example.fjp.beans;

import com.example.fjp.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: BeanFactory
 * @Author: admin
 * @Date: 2020/5/12 15:14
 * @Description: BeanFactory
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/12           版本号
 */
public class BeanFactory {
	private static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();
	
	public static Object getBean(Class<?> cls) {
		return classToBean.get(cls);
	}
	
	public static List<Class<?>> getBeanSet() {
		return new ArrayList<>(classToBean.keySet());
	}
	
	public static void initBean(List<Class<?>> classList) throws Exception {
		List<Class<?>> toCreate = new ArrayList<>(classList);
		// 穷举法，每次添加所有能成功被维护的bean，直至循环时需注册的bean为空（成功）或不再减少（依赖异常）为止
		while (!toCreate.isEmpty()) {
			int remainSize = toCreate.size();
			// 禁止使用一般的for循环，一般的循环过程中调用remove方法会发生异常，删除不可预知的对象
			Iterator<Class<?>> iterator = toCreate.iterator();
			while (iterator.hasNext()) {
				if (finishCreate(iterator.next())) {
					iterator.remove();
				}
			}
			//ListIterator<Class<?>> classListIterator = toCreate.listIterator();
			//while (classListIterator.hasNext()) {
			//	if (finishCreate(classListIterator.next())) {
			//		classListIterator.remove();
			//	}
			//}
			if (toCreate.size() == remainSize) {
				throw new Exception("cycle dependency!");
			}
		}
	}
	
	private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
		if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
			return true;
		}
		// 创建被维护的bean的 类 的 实例
		Object bean = cls.newInstance();
		// 遍历此类的所有成员变量
		for (Field field : cls.getDeclaredFields()) {
			// TODO 其他成员变量注解
			// 对含有自动注入的成员变量进行初始化
			if (field.isAnnotationPresent(AutoWired.class)) {
				Class<?> fieldType = field.getType();
				Object reliantBean = BeanFactory.getBean(fieldType);
				if (reliantBean == null) {
					return false;
				}
				field.setAccessible(true);
				field.set(bean, reliantBean);
			}
		}
		// 添加此 类的实例 至 beanFactory 进行维护,即 类 和 实例 的一个映射
		classToBean.put(cls, bean);
		return true;
	}
}