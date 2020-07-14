package com.example.fjp.v1b.dao;

import com.example.fjp.v1.anno.TestSelect;
import com.example.fjp.v1.entity.AccountInfo;

/**
 * @FileName: AccountMapper
 * @Author: admin
 * @Date: 2020/5/20 10:18
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
//@Component
public interface AccountMapper {
	@TestSelect("select * from account_info where account_id=? and account_isLogin=?")
	AccountInfo qryById(String id, String isLogin);
}