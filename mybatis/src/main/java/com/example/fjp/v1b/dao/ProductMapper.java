package com.example.fjp.v1b.dao;

import com.example.fjp.v1b.anno.TestSelect;
import com.example.fjp.v1b.entity.ProductInfo;
import org.springframework.stereotype.Component;

/**
 * @FileName: ProductMapper
 * @Author: admin
 * @Date: 2020/5/20 10:18
 * @Description: History:
 * <author>          <time>          <version>
 * admin           2020/5/20           版本号
 */
@Component
public interface ProductMapper {
	
	@TestSelect("select * from product_info where product_id=?")
	ProductInfo qryById(String id);
}