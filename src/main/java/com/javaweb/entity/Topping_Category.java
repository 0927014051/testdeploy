package com.javaweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "topping_category")
public class Topping_Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long topping_category_id;
	
	@Column
	private Long topping_id;
	
	@Column
	private Long category_id;
	
	@Column
	private int topping_price;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "topping_id", insertable = false, updatable = false)
	private Topping topping;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id",insertable = false, updatable = false)
	private Category category;

	public Long getTopping_category_id() {
		return topping_category_id;
	}

	public void setTopping_category_id(Long topping_category_id) {
		this.topping_category_id = topping_category_id;
	}

	public Long getTopping_id() {
		return topping_id;
	}

	public void setTopping_id(Long topping_id) {
		this.topping_id = topping_id;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public int getTopping_price() {
		return topping_price;
	}

	public void setTopping_price(int topping_price) {
		this.topping_price = topping_price;
	}

	public Topping getTopping() {
		return topping;
	}

	public void setTopping(Topping topping) {
		this.topping = topping;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	

	public Topping_Category(Long topping_category_id, Long topping_id, Long category_id, int topping_price,
			Topping topping, Category category) {
		super();
		this.topping_category_id = topping_category_id;
		this.topping_id = topping_id;
		this.category_id = category_id;
		this.topping_price = topping_price;
		this.topping = topping;
		this.category = category;
	}

	public Topping_Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
