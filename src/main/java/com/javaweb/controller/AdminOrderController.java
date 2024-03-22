package com.javaweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javaweb.entity.Orders;
import com.javaweb.entity.Staff;
import com.javaweb.entity.User;
import com.javaweb.exception.ProductException;
import com.javaweb.exception.UserException;
import com.javaweb.response.EntityStatusResponse;
import com.javaweb.service.OrderService;
import com.javaweb.service.StaffService;
import com.javaweb.service.UserService;

@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
	
	private OrderService orderService;
	private UserService userService;
	private StaffService staffService;
	public AdminOrderController(OrderService orderService, UserService userService, StaffService staffService) {
		super();
		this.orderService = orderService;
		this.userService = userService;
		this.staffService = staffService;
	}
	
	@PutMapping("/{order_id}/status")
	public ResponseEntity<EntityStatusResponse> updateStatusOrder(@PathVariable Long order_id,@RequestBody Orders orders, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user = userService.findUserByJwt(jwt);
		Staff staff = staffService.findStaffByUserId(user.getUser_id());
		Orders updateOrders = orderService.updateStatusOrder(order_id, orders.getStatus(),staff.getStaff_id());
		EntityStatusResponse response = new EntityStatusResponse(updateOrders,HttpStatus.OK.value(),"SUCCESS");
		return new ResponseEntity<EntityStatusResponse>(response,HttpStatus.OK) ;
	}
	

}
