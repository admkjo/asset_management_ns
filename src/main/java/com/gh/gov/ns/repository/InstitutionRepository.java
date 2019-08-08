package com.gh.gov.ns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gh.gov.ns.model.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, String>{
	@Query(value = "SELECT * FROM institution WHERE name = ?1", nativeQuery = true)
	Institution findInstitutionByName(String name);
	
	@Query(value = "SELECT * FROM institution WHERE name = ?1 and address is not null and phone is not null", 
			nativeQuery = true)
	Institution isInstitutionProfileComplete(String name);
	
	@Query(value = "SELECT * FROM institution t inner join user u on t.name = u.department_identifier and u.role_id = '2' ", 
			nativeQuery = true)
	List<Institution> getAllSuppliers();
	
	@Query(value = "SELECT * FROM institution t inner join user u on t.name = u.department_identifier and u.role_id = '1' ", 
			nativeQuery = true)
	List<Institution> getAllInstitutions();

}
