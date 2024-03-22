package com.javaweb.service;

import com.javaweb.entity.Role;

public interface RoleService {
	Role createRole(Role role);
	
	Role findRoleByName(String name);
}
