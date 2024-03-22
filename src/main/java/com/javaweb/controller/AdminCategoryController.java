package com.javaweb.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.javaweb.entity.Category;
import com.javaweb.entity.Customer;
import com.javaweb.entity.Staff;
import com.javaweb.entity.User;
import com.javaweb.exception.UserException;
import com.javaweb.function.ConvertToSlug;
import com.javaweb.service.CategoryService;
import com.javaweb.service.StaffService;
import com.javaweb.service.UserService;

@RestController
@RequestMapping("/api/admin/category")
public class AdminCategoryController {

	private CategoryService categoryService;
	private UserService userService;
	private StaffService staffService;

	public AdminCategoryController(CategoryService categoryService,UserService userService, StaffService staffService) {
		super();
		this.categoryService = categoryService;
		this.userService = userService;
		this.staffService = staffService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Category> createCategoryHandler(@RequestBody Map<String, Object> categoryMap,@RequestHeader("Authorization") String jwt) throws UserException{
		Category category = new Category();
		User user = userService.findUserByJwt(jwt);
		Staff staff = staffService.findStaffByUserId(user.getUser_id());
		String category_name = null;
		if(categoryMap.containsKey("category_name")) {
			category_name = (String) categoryMap.get("category_name");
		}
		category.setCategory_name(category_name);
		category.setCreated_at(LocalDateTime.now());
		category.setUpdated_at(LocalDateTime.now());
		category.setCreated_by(staff.getStaff_id());
		category.setUpdated_by(staff.getStaff_id());
		category.setSlug(ConvertToSlug.convertToSlug(category_name));
		Category createCategory = categoryService.createCategory(category);
		return new ResponseEntity<Category>(createCategory,HttpStatus.ACCEPTED);
	}
}
