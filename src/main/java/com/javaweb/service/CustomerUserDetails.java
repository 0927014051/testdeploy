package com.javaweb.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javaweb.entity.User;
import com.javaweb.reponsitory.UserRepo;


@Service
public class CustomerUserDetails implements UserDetailsService {
	
	private UserRepo userRepository;
	
	public CustomerUserDetails(UserRepo userRepository) {
		this.userRepository=userRepository;
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not found with email "+username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}

}
