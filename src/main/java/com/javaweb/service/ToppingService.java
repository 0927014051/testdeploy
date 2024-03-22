package com.javaweb.service;

import com.javaweb.entity.Topping;
import com.javaweb.request.CreateToppingCategoryRequest;

public interface ToppingService {

	public Topping findToppingByName(String id);
	
	public Topping createTopping(CreateToppingCategoryRequest topping);
}
