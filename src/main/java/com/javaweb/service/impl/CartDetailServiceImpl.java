package com.javaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javaweb.entity.Cart;
import com.javaweb.entity.CartDetail;
import com.javaweb.entity.Customer;
import com.javaweb.entity.PriceUpdateDetail;
import com.javaweb.entity.Product;
import com.javaweb.entity.User;
import com.javaweb.exception.CartDetailException;
import com.javaweb.exception.UserException;
import com.javaweb.reponsitory.CartDetailRepo;
import com.javaweb.reponsitory.CartRepo;
import com.javaweb.reponsitory.CustomerRepo;
import com.javaweb.service.CartDetailService;
import com.javaweb.service.CustomerService;
import com.javaweb.service.PriceUpdateService;
import com.javaweb.service.UserService;

@Service
public class CartDetailServiceImpl implements CartDetailService{

	private CartDetailRepo cartDetailRepo;
	private UserService userService;
	private CartRepo cartRepo;
	private CustomerService customerService;
	private PriceUpdateService priceUpdateService;
	public CartDetailServiceImpl(CartDetailRepo cartDetailRepo, UserService userService, CartRepo cartRepo, CustomerService customerService,PriceUpdateService priceUpdateService) {
		super();
		this.cartDetailRepo = cartDetailRepo;
		this.userService = userService;
		this.cartRepo = cartRepo;
		this.customerService = customerService;
		this.priceUpdateService = priceUpdateService;
	}
	
	@Override
	public CartDetail createCartDetail(CartDetail cartDetail) {
		PriceUpdateDetail priceUpdateDetail = priceUpdateService.findPriceUpdateByProductId(cartDetail.getProduct().getProduct_id());
		cartDetail.setQuantity(1);
		cartDetail.setPrice(priceUpdateDetail.getPrice_new()*cartDetail.getQuantity());
		CartDetail createdCartDetail = cartDetailRepo.save(cartDetail);
		return createdCartDetail;
	}
	
	@Override
	public CartDetail isCartDetailExist(Cart cart, Product product, Long customer_id) {
		CartDetail cartDetail = cartDetailRepo.isCartItemExist(cart, product, customer_id);
		return cartDetail;
	}
	@Override
	public void removeCartDetail(Long customer_id, Long cart_detail_id) throws CartDetailException,UserException{
		System.err.println("customerId - " + customer_id + " cartDetailId - " + cart_detail_id);
		
		CartDetail cartDetail = findCartDetailById(cart_detail_id);
		
		Customer customer = customerService.findCustomerById(cartDetail.getCart().getCustomer_id());
		Customer rq = customerService.findCustomerById(customer_id);
		if(customer.getCustomer_id().equals(rq.getCustomer_id())) {
			cartDetailRepo.deleteById(cartDetail.getCart_detail_id());
		}
		else {
			throw new UserException("You can't remove anothor users item");
		}
	}
	
	@Override
	public CartDetail findCartDetailById(Long cartDetailId) throws CartDetailException{
		
		Optional<CartDetail> opt = cartDetailRepo.findById(cartDetailId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartDetailException("cart item not found with id " + cartDetailId);
	}

	@Override
	public CartDetail updateCartDetail(Long customerId, Long cartDetailId, CartDetail cartDetail)
			throws CartDetailException, UserException {
		CartDetail item = findCartDetailById(cartDetailId);
		Customer customer = customerService.findCustomerById(item.getCart().getCustomer_id());
		
		if(customer.getCustomer_id().equals(customerId)) {
			item.setQuantity(cartDetail.getQuantity());
			item.setPrice(item.getQuantity());
			return cartDetailRepo.save(item);
		}
		else {
			throw new CartDetailException("You can't update another customer cart detail");
		}
	}
	
	@Override
	public CartDetail findCartDetailByCartIdAndProductIdWithTopping(Long cart_id, String proudct_id,String size, String topping) {
		return cartDetailRepo.findCartDetailByCartIdAndProductIdWithTopping(cart_id, proudct_id,size,topping);
	}
	@Override
	public CartDetail findCartDetailByCartIdAndProductIdWithToppingNull(Long cart_id, String proudct_id,String size) {
		return cartDetailRepo.findCartDetailByCartIdAndProductIdWithToppingNull(cart_id, proudct_id,size);
	}
	
	@Override
	public List<CartDetail> findCartDetailByCartId(Long cart_id) {
		return cartDetailRepo.findCartDetailByCartId(cart_id);
	}
	
	@Override
	public void deleteCartDetail(Long cart_id){
		cartDetailRepo.deleteCartDetail(cart_id);
	}
}
