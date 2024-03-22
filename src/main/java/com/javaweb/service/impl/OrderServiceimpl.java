package com.javaweb.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.entity.Bill;
import com.javaweb.entity.Cart;
import com.javaweb.entity.CartDetail;
import com.javaweb.entity.Customer;
import com.javaweb.entity.OrderDetail;
import com.javaweb.entity.Orders;
import com.javaweb.entity.Product;
import com.javaweb.exception.ProductException;
import com.javaweb.reponsitory.BillRepo;
import com.javaweb.reponsitory.CartRepo;
import com.javaweb.reponsitory.OrderDetailRepo;
import com.javaweb.reponsitory.OrderRepo;
import com.javaweb.request.BuyNowRequest;
import com.javaweb.service.CartDetailService;
import com.javaweb.service.CartService;
import com.javaweb.service.OrderDetailService;
import com.javaweb.service.OrderService;

@Service
public class OrderServiceimpl implements OrderService {

	private OrderRepo orderRepo;
	private CartService cartService;
	private CartDetailService cartDetailService;
	private OrderDetailService orderDetailService;
	private OrderDetailRepo orderDetailRepo;
	private CartRepo cartRepo;
	private BillRepo billRepo;

	@Autowired
	public OrderServiceimpl(OrderRepo orderRepo, CartService cartService, CartDetailService cartDetailService,
			OrderDetailService orderDetailService, OrderDetailRepo orderDetailRepo, CartRepo cartRepo,
			BillRepo billRepo) {
		this.orderRepo = orderRepo;
		this.cartService = cartService;
		this.cartDetailService = cartDetailService;
		this.orderDetailService = orderDetailService;
		this.orderDetailRepo = orderDetailRepo;
		this.cartRepo = cartRepo;
		this.billRepo = billRepo;
	}

	@Override
	@Transactional
	public Orders createOrder(Customer customer) {
		Cart cart = cartService.findCartBCustomerId(customer.getCustomer_id());
		List<OrderDetail> list = new ArrayList<>();

//			Orders orders = new Orders();
//			orders.setCreate_at(LocalDateTime.now());
//			orders.setUpdate_at(LocalDateTime.now());
//			orders.setCustomer_id(customer.getCustomer_id());
//			orders.setStatus(0);
//			orders.setTotal_price(0);
//			orders.setTotal_quantity(0);
//			Orders createdOrders = orderRepo.save(orders);
		int totalPrice = 0;
		int totalQuantity = 0;
		for (CartDetail detail : cart.getCart_detail()) {
			OrderDetail orderDetail = new OrderDetail();
			// orderDetail.setOder_id(createdOrders.getOrder_id());
			orderDetail.setPrice(detail.getPrice());
			orderDetail.setProduct_id(detail.getProduct_id());
			orderDetail.setQuantity(detail.getQuantity());
			orderDetail.setSize(detail.getSize());
			orderDetail.setTopping(detail.getTopping());
			OrderDetail createDetail = orderDetailRepo.save(orderDetail);
			totalQuantity += createDetail.getQuantity();
			totalPrice += (createDetail.getQuantity() * createDetail.getPrice());
			list.add(createDetail);
		}
		Orders orders = new Orders();
		orders.setCreate_at(LocalDateTime.now());
		orders.setUpdate_at(LocalDateTime.now());
		orders.setCustomer_id(customer.getCustomer_id());
		orders.setCustomer(customer);
		orders.setStatus(0);
		orders.setTotal_price(totalPrice);
		orders.setTotal_quantity(totalQuantity);
		Orders createdOrders = orderRepo.save(orders);
		for (OrderDetail item : list) {
			item.setOrder_id(createdOrders.getOrder_id());
			orderDetailRepo.save(item);
		}
		cartDetailService.deleteCartDetail(cart.getCart_id());
		cart.setTotal_price(0);
		cart.setTotal_quantity(0);
		cartRepo.save(cart);
		return createdOrders;

	}

	@Override
	public Orders orderBuyNow(BuyNowRequest rq,Long customer_id) {

		Orders orders = new Orders();
		orders.setCreate_at(LocalDateTime.now());
		orders.setCustomer_id(customer_id);
		orders.setStatus(0);
		orders.setTotal_price(0);
		orders.setTotal_quantity(0);
		orders.setUpdate_at(LocalDateTime.now());
		Orders createdOrders = orderRepo.save(orders);
		if (createdOrders != null) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setPrice(rq.getPrice());
			orderDetail.setProduct_id(rq.getProduct_id());
			orderDetail.setQuantity(rq.getQuantity());
			orderDetail.setSize(rq.getSize());
			orderDetail.setTopping(rq.getTopping());
			orderDetail.setOrder_id(createdOrders.getOrder_id());
			OrderDetail createdOrderDetail = orderDetailRepo.save(orderDetail);
			if (createdOrderDetail != null) {
				int totalPrice = orderDetailRepo.totalPriceByOrderId(createdOrders.getOrder_id());
				int totalQuantity = orderDetailRepo.totalQuantityByOrderId(createdOrders.getOrder_id());
				createdOrders.setTotal_quantity(totalQuantity);
				createdOrders.setTotal_price(totalPrice);
				orderRepo.save(createdOrders);
			}
		}
		return createdOrders;

	}

	@Override
	public Orders findOrderById(Long orderId) throws ProductException {
		Optional<Orders> orderOptional = orderRepo.findById(orderId);
		if (orderOptional.isPresent()) {
			System.err.println("find product");
			return orderOptional.get();
		}
		throw new ProductException("Order not found with id " + orderId);
	}

	@Override
	public Orders updateStatusOrder(Long orderId, int status, Long staff_id) throws ProductException {

		Orders update = findOrderById(orderId);
		if (update != null) {
			if (status == 3) {
				Bill bill = new Bill();
				bill.setCreated_at(LocalDateTime.now());
				bill.setCreated_by(staff_id);
				bill.setOrder_id(update.getOrder_id());
				Bill savedBill = billRepo.save(bill);
				if (savedBill != null) {
					update.setStatus(status);
				}

			} else {
				update.setStatus(status);

			}
		}
		return orderRepo.save(update);
	}

}
