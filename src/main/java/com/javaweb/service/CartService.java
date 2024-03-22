package com.javaweb.service;

import com.javaweb.entity.Cart;
import com.javaweb.entity.Customer;
import com.javaweb.entity.User;
import com.javaweb.exception.ProductException;
import com.javaweb.request.AddItemRequest;


public interface CartService {
	
	public Cart createCart(Cart cart);
	
	public String addCartItem(Long customer_id,AddItemRequest req) throws ProductException;
//	
	public Cart findCartBCustomerId(Long customerId);
//
	public void clearCart(Long customerId);
}
