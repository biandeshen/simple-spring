package com.example.fjp.service;

import com.example.fjp.beans.Bean;

/**
 * @FileName: InfoService
 * @Author: admin
 * @Date: 2020/5/12 18:50
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/5/12           版本号
 */
@Bean
public class InfoService {
	
	public String getInfo(String name) {
		return "Hello " + name;
	}
	
}