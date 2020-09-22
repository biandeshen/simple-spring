package com.example.fjp.v1.factorybean;

import com.example.fjp.v1.anno.TestSelect;
import com.example.fjp.v1.dao.AccountMapper;
import com.example.fjp.v1.entity.AccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @FileName: AccountMapperFactoryBean
 * @Author: admin
 * @Date: 2020/5/20 10:25
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
public class AccountMapperFactoryBean implements FactoryBean<AccountMapper> {
	
	@Override
	public AccountMapper getObject() throws Exception {
		Object proxyInstance = Proxy.newProxyInstance(AccountMapper.class.getClassLoader(),
		                                              new Class[]{AccountMapper.class},
		                                              new AccountMapperProxy());
		return (AccountMapper) proxyInstance;
	}
	
	@Override
	public Class<?> getObjectType() {
		return AccountMapper.class;
	}
	
	@Override
	public boolean isSingleton() {
		return true;
	}
}

class AccountMapperProxy implements InvocationHandler {
	private static final Logger logger = LoggerFactory.getLogger(AccountMapper.class);
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 处理 Object 原生方法
		if (method.getDeclaringClass().equals(Object.class)) {
			return method.invoke(this, args);
		}
		// sql 占位符替换后拼接字符串
		StringBuilder sqlBuilder = new StringBuilder();
		
		// 方法上注解处理
		// select注解
		TestSelect testSelect = method.getAnnotation(TestSelect.class);
		String selectSql = testSelect.value();
		if (selectSql.isEmpty()) {
			throw new NullPointerException("空的SQL语句");
		}
		int selectMatches = StringUtils.countMatches(selectSql, "?");
		if (selectMatches <= args.length) {
			throw new IllegalArgumentException("错误的 " + TestSelect.class.getName() + " SQL占位符数量，与参数数目冲突!");
		}
		String[] selectSplitStrs = StringUtils.split(selectSql, "?");
		for (int i = 0; i <= selectMatches; i++) {
			sqlBuilder.append(selectSplitStrs[i]).append(args[i]);
		}
		selectSql = sqlBuilder.toString();
		logger.info("解析查询sql：{} 入参：{} ", selectSql, Arrays.asList(args));
		
		// delete注解
		
		
		// 模拟查询数据库后返回
		Class<?> returnType = method.getReturnType();
		Object returnObject = returnType.newInstance();
		
		// 查询返回 AccountInfo 对象
		AccountInfo accountInfo = (AccountInfo) returnObject;
		accountInfo.setAccountId("1");
		accountInfo.setAccountName("test");
		accountInfo.setId("1");
		accountInfo.setIsLogin("true");
		
		return accountInfo;
	}
}