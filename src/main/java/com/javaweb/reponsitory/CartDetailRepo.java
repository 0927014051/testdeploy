package com.javaweb.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaweb.entity.Cart;
import com.javaweb.entity.CartDetail;
import com.javaweb.entity.Product;

@Repository
public interface CartDetailRepo extends JpaRepository<CartDetail, Long>{
	@Query("SELECT ci From CartDetail ci Where ci.cart=:cart And ci.product=:product And ci.cart.customer_id=:customerId")
	public CartDetail isCartItemExist(@Param("cart")Cart cart,@Param("product")Product product, @Param("customerId")Long customerId);
	
	@Query("SELECT SUM(quantity*price) FROM CartDetail WHERE cart_id = ?1")
	public int totalPriceByCartId(Long cart_id);
	
	@Query("SELECT SUM(quantity) FROM CartDetail WHERE cart_id = ?1")
	public int totalQuantityByCartId(Long cart_id);
	
	@Query( value = "SELECT * FROM cart_detail WHERE cart_id = ?1 AND product_id = ?2 AND size = ?3 AND topping = ?4",nativeQuery =  true)
	public CartDetail findCartDetailByCartIdAndProductIdWithTopping(Long cart_id,String product_id, String size, String topping);
	
	@Query( value = "SELECT * FROM cart_detail WHERE cart_id = ?1 AND product_id = ?2 AND size = ?3",nativeQuery =  true)
	public CartDetail findCartDetailByCartIdAndProductIdWithToppingNull(Long cart_id,String product_id, String size);

	
	@Query( value = "SELECT * FROM cart_detail WHERE cart_id = ?1",nativeQuery =  true)
	public List<CartDetail> findCartDetailByCartId(Long cart_id);
	
	@Modifying
    @Query("DELETE FROM CartDetail cd WHERE cd.cart_id = :cart_id")
	public void deleteCartDetail(Long cart_id);

}
