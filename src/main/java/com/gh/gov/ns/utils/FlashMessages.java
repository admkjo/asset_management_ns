package com.gh.gov.ns.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlashMessages {
	
	private String message;
	private Status status;
	
	public static enum Status{
		SUCCESS,FAILURE
	}

}
