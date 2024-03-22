package com.javaweb.service.impl;

import org.springframework.stereotype.Service;

import com.javaweb.entity.Role;
import com.javaweb.reponsitory.RoleRepo;
import com.javaweb.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	private RoleRepo roleRepo;
	
	
	
	public RoleServiceImpl(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}



	@Override 
	public Role createRole(Role role) {
		return roleRepo.save(role);
	}
	
	@Override
	public Role findRoleByName(String name) {
		return roleRepo.findByName(name);
	}
}
