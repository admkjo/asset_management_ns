package com.gh.gov.ns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gh.gov.ns.model.InstitutionEntries;

@Repository
public interface InstitutionEntryRepository extends JpaRepository<InstitutionEntries, String> {

	@Query(value = "SELECT * FROM institution_entries i left join suppliers_entries s on "
			+ "i.chassis_number = s.chassis_number and i.engine_number=s.engine_number and i.company_received_from = s.institution_supplied_to "
			+ "where s.institution_supplied_to= ?1", nativeQuery = true)
	List<InstitutionEntries> reconcilationReport(String institution);

	@Query(value = "SELECT * FROM institution_entries i left join suppliers_entries s on " + 
			" i.chassis_number = s.chassis_number and i.engine_number=s.engine_number " + 
			" where (i.company_received_from = s.institution_supplied_to) ", nativeQuery = true)
	List<InstitutionEntries> reconciledVehicles();
	
	/*@Query(value = "SELECT * FROM institution_entries i left join suppliers_entries s on "
			+ "i.chassis_number = s.chassis_number and i.engine_number=s.engine_number "
			+ " and i.company_received_from = s.institution_supplied_to ", nativeQuery = true)
	List<InstitutionEntries> reconciledVehiclesInstitution();*/


	@Query(value = "SELECT * FROM institution_entries  i " + "where i.status= 'AUCTIONED' ", nativeQuery = true)
	List<InstitutionEntries> auctionedVehicles();

}
