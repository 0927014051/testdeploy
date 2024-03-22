package com.javaweb.controller;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.entity.Cart;
import com.javaweb.entity.Customer;
import com.javaweb.entity.User;
import com.javaweb.exception.ProductException;
import com.javaweb.exception.UserException;
import com.javaweb.request.AddItemRequest;
import com.javaweb.response.ApiResponse;
import com.javaweb.response.EntityStatusResponse;
import com.javaweb.service.CartService;
import com.javaweb.service.CustomerService;
import com.javaweb.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private CartService cartService;
	private UserService userService;
	private CustomerService customerService;
	public CartController(CartService cartService, UserService userService,CustomerService customerService) {
		this.cartService = cartService;
		this.userService = userService;
		this.customerService = customerService;
	}
	
	@GetMapping("/")
	public ResponseEntity<EntityStatusResponse<Cart>> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		User user = userService.findUserByJwt(jwt);
		Customer customer = customerService.findCustomerByUserId(user.getUser_id());
		Cart cart = cartService.findCartBCustomerId(customer.getCustomer_id());
		System.err.println("cart - " + cart.getCustomer().getEmail());

		EntityStatusResponse<Cart> responseData = new EntityStatusResponse<>(cart,HttpStatus.OK.value(),"The request was successfully completed");
		return ResponseEntity.status(HttpStatus.OK).body(responseData);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		User user = userService.findUserByJwt(jwt);
		Customer customer = customerService.findCustomerByUserId(user.getUser_id());
		cartService.addCartItem(customer.getCustomer_id(), req);
		ApiResponse response = new ApiResponse("Item add to cart",true,HttpStatus.ACCEPTED.value());
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
	}
	
	 
    @GetMapping("/test")
    public ResponseEntity<String> withResponseEntity() {
        int randomInt = new Random().ints(1, 1, 11).findFirst().getAsInt();
        if (randomInt < 9) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Expectation Failed from Client (CODE 417)\n");   
        } else {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("April Fool's Status Code (CODE 418)\n");
        }
    }   

}
