package com.javaweb.reponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaweb.entity.Topping;

@Repository
public interface ToppingRepo extends JpaRepository<Topping, Long>{
	@Query("SELECT p From Topping p Where LOWER(p.topping_name)=:name")
	public Topping findToppingByName(String name);
}
