package com.javaweb.service;

import com.javaweb.entity.Topping_Category;

public interface ToppingCategoryService {

	Topping_Category findToppingCategoryById(Long category_id,Long topping_id);
}
