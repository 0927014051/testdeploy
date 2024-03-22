package com.javaweb.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.javaweb.entity.Category;
import com.javaweb.entity.Topping;
import com.javaweb.entity.Topping_Category;
import com.javaweb.reponsitory.CategoryRepo;
import com.javaweb.reponsitory.ToppingCategoryRepo;
import com.javaweb.reponsitory.ToppingRepo;
import com.javaweb.request.CreateToppingCategoryRequest;
import com.javaweb.service.CategoryService;
import com.javaweb.service.ToppingService;

@Service
public class ToppingServiceImpl implements ToppingService{
	private ToppingRepo toppingRepo;
	private CategoryRepo categoryRepo;
	private ToppingCategoryRepo toppingCategoryRepo;

	public ToppingServiceImpl(ToppingRepo toppingRepo,CategoryRepo categoryRepo,ToppingCategoryRepo toppingCategoryRepo) {
		super();
		this.toppingRepo = toppingRepo;
		this.categoryRepo = categoryRepo;
		this.toppingCategoryRepo = toppingCategoryRepo;
	}
	
	@Override
	public Topping findToppingByName(String name) {
		Topping findTopping = toppingRepo.findToppingByName(name);
		return findTopping;
	}
	
	@Override
	public Topping createTopping(CreateToppingCategoryRequest rq) {
		Topping topping = new Topping();
		topping.setCreated_at(LocalDateTime.now());
		topping.setCreated_by(rq.getStaff_id());
		topping.setImage(rq.getImage());
		topping.setTopping_name(rq.getTopping_name());
		topping.setUpdated_at(LocalDateTime.now());
		topping.setUpdated_by(rq.getStaff_id());
		Topping createdTopping = toppingRepo.save(topping);
		if(createdTopping != null) {
			Topping_Category topping_Category =  new Topping_Category();
			Category category = categoryRepo.findCategoryByName(rq.getCategory_name());
			topping_Category.setCategory_id(category.getCategory_id());
			topping_Category.setTopping_id(createdTopping.getTopping_id());
			topping_Category.setTopping_price(rq.getTopping_price());
			Topping_Category createdToppingCategory = toppingCategoryRepo.save(topping_Category);
		}
		return createdTopping;
	}

}
