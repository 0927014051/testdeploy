package com.javaweb.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaweb.entity.OrderDetail;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long>{
	@Query("SELECT SUM(quantity*price) FROM OrderDetail WHERE order_id = ?1")
	public int totalPriceByOrderId(Long cart_id);
	
	@Query("SELECT SUM(quantity) FROM OrderDetail WHERE order_id = ?1")
	public int totalQuantityByOrderId(Long cart_id);

}
