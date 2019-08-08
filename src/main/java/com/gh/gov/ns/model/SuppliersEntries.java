package com.gh.gov.ns.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class SuppliersEntries implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String entryId;
	private String typeOfVehicle;
	private String transactionId;
	private String chassisNumber;
	private String engineNumber;
	private String dateOfEntry;
	private String manufYear;
	private double costOfVehicle;
	private String dateSupplied;
	private String institutionSuppliedTo;
	private int importDutyExemption;
	private String importDutyDetails;
	private String paymentChequeDetails;
	private String dvlaRegistrationDetails;
	
	@OneToMany
	@JoinTable(name="suppliers_documents", joinColumns = @JoinColumn(name="supplier_id", 
	referencedColumnName ="entryId"), inverseJoinColumns = 
	@JoinColumn(name="document_id", referencedColumnName="documentId"))
	private List<Documents> documents;

}
