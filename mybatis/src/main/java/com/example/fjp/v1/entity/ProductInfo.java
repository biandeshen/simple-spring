package com.example.fjp.v1.entity;

/**
 * @FileName: ProductInfo
 * @Author: admin
 * @Date: 2020/5/20 10:00
 * @Description: 产品信息
 * History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
public class ProductInfo {
	private String id;
	private String productId;
	private String productName;
	private String num;
	
	@Override
	public String toString() {
		final StringBuilder sb;
		sb = new StringBuilder();
		sb.append('{');
		sb.append("\"id\":\"").append(id).append('\"');
		sb.append(",\"productId\":\"").append(productId).append('\"');
		sb.append(",\"productName\":\"").append(productName).append('\"');
		sb.append(",\"num\":\"").append(num).append('\"');
		sb.append('}');
		return sb.toString();
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getNum() {
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
}