package com.javaweb.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaweb.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String>{
	
	@Query("SELECT p From Product p where LOWER(p.product_name) Like %:query% OR LOWER(p.description) Like %:query% OR LOWER(p.category.category_name) LIKE %:query%")
	public List<Product> searchProduct(@Param("query")String query);
	
	@Query("SELECT p From Product p Where LOWER(p.category.category_name)=:category")
	public List<Product> findByCategory(@Param("category") String category);
	
	@Query("SELECT p From Product p Where LOWER(p.product_name)=:name")
	public Product findProductByName(String name);
	
//	@Query(
//			"SELECT p FROM product p " +
//	        "WHERE (p.category.name = :category OR :category = '') " +
//	        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) " +
//		    "AND (:minDiscount IS NULL OR p.discountPersent >= :minDiscount) " +
//		    "ORDER BY " +
//		    "CASE WHEN :sort = 'price_low' THEN p.discountedPrice END ASC, " +
//		    "CASE WHEN :sort = 'price_high' THEN p.discountedPrice END DESC")
//	List<Product> filterProducts(
//	        @Param("category") String category,
//			@Param("minPrice") Integer minPrice,
//			@Param("maxPrice") Integer maxPrice,
//			@Param("minDiscount") Integer minDiscount,
//			@Param("sort") String sort
//			);
}
