package com.example.fjp.v2.dao;

import com.example.fjp.v2.anno.TulingSelect;
import com.example.fjp.v2.entity.ProductInfo;
import org.springframework.stereotype.Component;

/**
 * [来个全套]
 *
 * @slogan: 高于生活，源于生活
 * @Description: TODO
 * @author: smlz
 * @date 2020/5/5 14:03
 */
@Component
public interface ProductMapper {
	
	@TulingSelect(value = "select * from product_info where product_id=?")
	ProductInfo qryById(Integer productId);
}
