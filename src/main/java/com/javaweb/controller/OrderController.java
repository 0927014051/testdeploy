package com.javaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.entity.Customer;
import com.javaweb.entity.Orders;
import com.javaweb.entity.User;
import com.javaweb.exception.UserException;
import com.javaweb.reponsitory.OrderRepo;
import com.javaweb.request.BuyNowRequest;
import com.javaweb.response.ApiResponse;
import com.javaweb.service.CustomerService;
import com.javaweb.service.OrderService;
import com.javaweb.service.UserService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private OrderService orderService;
	private CustomerService customerService;
	private UserService userService;
	
	@Autowired
	private OrderRepo orderRepo;

	public OrderController(OrderService orderService, CustomerService customerService, OrderRepo orderRepo,UserService userService) {
		super();
		this.orderService = orderService;
		this.customerService = customerService;
		this.orderRepo = orderRepo;
		this.userService = userService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Orders> createOrderHandler( @RequestHeader("Authorization") String jwt) throws UserException{
		 
		User user = userService.findUserByJwt(jwt);
		Customer customer = customerService.findCustomerByUserId(user.getUser_id());
		Orders orders = orderService.createOrder(customer);
		return new ResponseEntity<Orders>(orders,HttpStatus.OK);
	}
	
	@PostMapping("/buynow")
	public ResponseEntity<ApiResponse> buynowOrderHandler(@RequestHeader("Authorization") String jwt, @RequestBody BuyNowRequest rq) throws UserException{
		User user = userService.findUserByJwt(jwt);
		Customer customer = customerService.findCustomerByUserId(user.getUser_id());
		Orders orders = orderService.orderBuyNow(rq,customer.getCustomer_id());
		ApiResponse response = new ApiResponse();
		if(orders != null) {
			response.setCode(HttpStatus.CREATED.value());
			response.setMessage("created order success");
			response.setStatus(true);		
		}else {
			response.setCode(HttpStatus.BAD_REQUEST.value());
			response.setMessage("created order fail");
			response.setStatus(false);
		}
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	

}

