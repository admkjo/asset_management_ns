package com.gh.gov.ns.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Institution implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String institutionId;
	private String name;
	private String address;
	private String email;
	private String phone;
	private String representativeName;
	private String representativeContact;
	private String representativePosition;
	private String representativeEmail;
		

}
