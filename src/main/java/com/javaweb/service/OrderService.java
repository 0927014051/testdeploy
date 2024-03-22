package com.javaweb.service;

import java.util.List;

import com.javaweb.entity.Customer;
import com.javaweb.entity.Orders;
import com.javaweb.entity.User;
import com.javaweb.exception.OrdersException;
import com.javaweb.exception.ProductException;
import com.javaweb.request.BuyNowRequest;


public interface OrderService {

	public Orders createOrder(Customer customer);
	
	public Orders updateStatusOrder(Long orderId,int status,Long staff_id) throws ProductException;
	
	public Orders findOrderById(Long orderId) throws ProductException;
	
	public Orders orderBuyNow(BuyNowRequest rq,Long customer_id);
	
//	public Orders findOrdersById(Long orderId) throws OrdersException;
//	
//	public List<Orders> usersOrdersHistory(Long userId);
//	
//	public Orders placedOrders(Long orderId) throws OrdersException;
//	
//	public Orders confirmedOrders(Long orderId)throws OrdersException;
//	
//	public Orders shippedOrders(Long orderId) throws OrdersException;
	
//	public Orders deliveredOrders(Long orderId) throws OrdersException;
//	
//	public Orders cancledOrders(Long orderId) throws OrdersException;
//	
//	public List<Orders>getAllOrderss();
//	
//	public Orders deleteOrders(Long orderId) throws OrdersException;
//
//	public Orders updateOrders (Long orderId) throws OrdersException;
//	public Orders successOrders (Long orderId) throws OrdersException;
}
