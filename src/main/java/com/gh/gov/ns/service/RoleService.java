package com.gh.gov.ns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gh.gov.ns.model.Role;
import com.gh.gov.ns.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findRoleByRoleName(String role_name){
		return roleRepository.findRoleByRoleName(role_name);
	}
	

}
