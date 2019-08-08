package com.gh.gov.ns.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Memo implements Serializable{
	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String memoId;
	private String recipients;
	private String subject;
	private String sender;
	private String date;
	@Column(columnDefinition = "TEXT")
	private String content;
	
}
