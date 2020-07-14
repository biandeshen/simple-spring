package com.example.fjp.v1b.entity;

/**
 * @FileName: Account
 * @Author: admin
 * @Date: 2020/5/20 9:56
 * @Description: 账号
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
public class AccountInfo {
	private String id;
	private String accountId;
	private String accountName;
	private String isLogin;
	
	@Override
	public String toString() {
		final StringBuilder sb;
		sb = new StringBuilder();
		sb.append('{');
		sb.append("\"id\":\"").append(id).append('\"');
		sb.append(",\"accountId\":\"").append(accountId).append('\"');
		sb.append(",\"accountName\":\"").append(accountName).append('\"');
		sb.append(",\"isLogin\":\"").append(isLogin).append('\"');
		sb.append('}');
		return sb.toString();
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getIsLogin() {
		return isLogin;
	}
	
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
}