package com.gh.gov.ns.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Letters implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String letterId;
	private String subject;
	@Column(columnDefinition = "TEXT")
	private String content;
	private String sender;
	@OneToMany(mappedBy = "attachment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Attachment> attachments;
	private String date;
	private String recipient;

}
