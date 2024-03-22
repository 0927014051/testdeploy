package com.javaweb.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaweb.entity.Topping_Category;

@Repository
public interface ToppingCategoryRepo extends JpaRepository<Topping_Category, Long>{
	@Query("SELECT p From Topping_Category p Where p.category_id =:category_id AND p.topping_id =:topping_id")
	Topping_Category findToppingCategoryById(Long category_id, Long topping_id);

}
