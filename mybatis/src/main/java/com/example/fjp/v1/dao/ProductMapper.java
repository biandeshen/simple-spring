package com.example.fjp.v1.dao;

import com.example.fjp.v1.anno.TestSelect;
import com.example.fjp.v1.entity.ProductInfo;
import org.springframework.stereotype.Component;

/**
 * @FileName: ProductMapper
 * @Author: admin
 * @Date: 2020/5/20 10:18
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
//@Component
public interface ProductMapper {
	
	@TestSelect("select * from product_info where product_id=?")
	ProductInfo qryById(String id);
}