package com.gh.gov.ns.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class InstitutionEntries implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String entryId;
	private String transactionId;
	private String typeOfVehicle;
	private String chassisNumber;
	private String engineNumber;
	private String dateOfEntry;
	private String manufYear;
	private String dateReceived;
	private String companyReceivedFrom;
	private String status;
	private String reasonIfAuctioned;
	
	@OneToMany
	@JoinTable(name="entries_documents", joinColumns = @JoinColumn(name="entry_id", 
	referencedColumnName ="entryId"), inverseJoinColumns = 
	@JoinColumn(name="document_id", referencedColumnName="documentId"))
	private List<Documents> documents;

}
