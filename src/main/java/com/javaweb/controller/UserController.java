package com.javaweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.entity.Customer;
import com.javaweb.entity.Staff;
import com.javaweb.entity.User;
import com.javaweb.exception.UserException;
import com.javaweb.request.ProfileUserRequest;
import com.javaweb.response.ApiResponse;
import com.javaweb.response.EntityStatusResponse;
import com.javaweb.response.ListEntityStatusResponse;
import com.javaweb.response.ProfileUserAndCustomerResponse;
import com.javaweb.service.CustomerService;
import com.javaweb.service.StaffService;
import com.javaweb.service.UserService;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings("unchecked")
public class UserController {

	private UserService userService;
	private CustomerService customerService;
	private StaffService staffService;

	public UserController(UserService userService,CustomerService customerService,StaffService staffService) {
		this.userService = userService;
		this.customerService = customerService;
		this.staffService = staffService;
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/all")
	public ResponseEntity<ListEntityStatusResponse> getAllUsersHandler() throws UserException{
		List<User> users = userService.getAllUsers();
		ListEntityStatusResponse listEntityStatusResponse = new ListEntityStatusResponse(users,HttpStatus.OK.value(),"Success");
		return new ResponseEntity<>(listEntityStatusResponse,HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<EntityStatusResponse> getUserProfileAndCustomerProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		System.err.println("/api/users/profile");
		System.err.println("println jwt: " + jwt);
		ProfileUserRequest profileUserResponse = userService.findUserProfileByJwt(jwt);
		EntityStatusResponse res = new EntityStatusResponse(profileUserResponse, HttpStatus.OK.value(), "success");
		return new ResponseEntity<EntityStatusResponse>(res,HttpStatus.ACCEPTED);	
	}
	
	  @PutMapping("/profile/update")
	    public ResponseEntity<ProfileUserAndCustomerResponse> updateUserAndCustomerProfileHandler  (
	            @RequestHeader("Authorization") String jwt, @RequestBody ProfileUserRequest update) throws UserException{
	        try {
	        	ProfileUserAndCustomerResponse res =   userService.updateUserAndCustomerProfileByJwt(jwt, update);
	            return new ResponseEntity<ProfileUserAndCustomerResponse>(res, HttpStatus.OK);
	        } catch (UserException e) {
	            // Return ResponseEntity with error message if UserException is thrown
	            return new ResponseEntity<ProfileUserAndCustomerResponse>(HttpStatus.NOT_FOUND); // Or appropriate error status
	        }
	    }
	
	  @GetMapping("/{username}/find")
	  @SuppressWarnings("rawtypes")
	  public ResponseEntity<EntityStatusResponse> findUserByUserName(@PathVariable String username){
		  User findUser = userService.findUserByUserName(username);
		EntityStatusResponse entityStatusResponse = new EntityStatusResponse(findUser, HttpStatus.OK.value(), "find user success by code " + HttpStatus.OK.value());
		  return new ResponseEntity<EntityStatusResponse>(entityStatusResponse,HttpStatus.OK);
	  }
	
	@PutMapping("/update/status/{username}")
	public ResponseEntity<ApiResponse> updateStatusUserHandler(@PathVariable String username,@RequestHeader("Authorization") String jwt) throws UserException{
		User userStaff = userService.findUserByJwt(jwt);
		Staff staff = staffService.findStaffByUserId(userStaff.getUser_id());
		User updateUser = userService.updateStatusByUsername(username, staff.getStaff_id());
		ApiResponse rq = new ApiResponse("Stoped user success", true, HttpStatus.ACCEPTED.value());
		return new ResponseEntity<ApiResponse>(rq,HttpStatus.ACCEPTED);
	}
	
	
}
