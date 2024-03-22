package com.javaweb.service.impl;

import org.springframework.stereotype.Service;

import com.javaweb.entity.Category;
import com.javaweb.reponsitory.CategoryRepo;
import com.javaweb.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepo categoryRepo;

	public CategoryServiceImpl(CategoryRepo categoryRepo) {
		super();
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}

}
