package com.gh.gov.ns.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateFormatter {
	
	public String currentDateFormmater(LocalDate localDate) {
		LocalDate date = LocalDate.now();
	    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
	    String convertedDate = date.format(formatters);
	    return convertedDate;	
	}

}
