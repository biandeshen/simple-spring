package com.example.fjp.controller;

import com.example.fjp.beans.AutoWired;
import com.example.fjp.service.InfoService;
import com.example.fjp.web.mvc.Controller;
import com.example.fjp.web.mvc.RequestMapping;
import com.example.fjp.web.mvc.RequestParam;

/**
 * @FileName: TestController
 * @Author: admin
 * @Date: 2020/5/12 10:34
 * @Description: 测试自定义mvc框架及自定义注解的Controller
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/12           版本号
 */
@Controller
public class TestController {
	@AutoWired
	private InfoService infoService;
	
	@RequestMapping("/info")
	public String getInfo(@RequestParam("name") String name, @RequestParam("passwd") String passwd) {
		return infoService.getInfo(name) + passwd;
	}
}