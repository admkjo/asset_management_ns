package com.gh.gov.ns.web;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gh.gov.ns.model.Institution;

@ControllerAdvice
@SessionAttributes()
public class GenericController {
	
	@ModelAttribute
    public void globalAttributes(Model model, HttpSession session) {
    Institution institutionDetails = (Institution) session.getAttribute("institutionDetails");
    Institution supplierDetails = (Institution) session.getAttribute("supplierDetails");
	if(institutionDetails!=null) {
		model.addAttribute("institutionDetails", institutionDetails);
	   }
	
	if(supplierDetails!=null) {
		model.addAttribute("supplierDetails", supplierDetails);
	   }
	
    }
    

}
