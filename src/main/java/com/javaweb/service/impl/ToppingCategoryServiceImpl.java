package com.javaweb.service.impl;

import org.springframework.stereotype.Service;

import com.javaweb.entity.Topping_Category;
import com.javaweb.reponsitory.ToppingCategoryRepo;
import com.javaweb.service.ToppingCategoryService;
import com.javaweb.service.ToppingService;

@Service
public class ToppingCategoryServiceImpl implements ToppingCategoryService{
	
	private ToppingCategoryRepo toppingCategoryRepo;
	
	
	public ToppingCategoryServiceImpl(ToppingCategoryRepo toppingCategoryRepo) {
		super();
		this.toppingCategoryRepo = toppingCategoryRepo;
	}


	@Override
	public Topping_Category findToppingCategoryById(Long category_id,Long topping_id) {
		return toppingCategoryRepo.findToppingCategoryById(category_id, topping_id);
	}
}
