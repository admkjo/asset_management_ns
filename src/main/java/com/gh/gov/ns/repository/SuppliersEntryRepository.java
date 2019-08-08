package com.gh.gov.ns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gh.gov.ns.model.SuppliersEntries;

@Repository
public interface SuppliersEntryRepository extends JpaRepository<SuppliersEntries, String>{

}
