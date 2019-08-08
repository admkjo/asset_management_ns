package com.gh.gov.ns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gh.gov.ns.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	
	@Query(value = "SELECT * FROM Role WHERE role_name = ?1", nativeQuery = true)
	public Role findRoleByRoleName(String role_name);

}
