package com.gh.gov.ns.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gh.gov.ns.model.Institution;
import com.gh.gov.ns.model.Role;
import com.gh.gov.ns.model.User;
import com.gh.gov.ns.repository.InstitutionEntryRepository;
import com.gh.gov.ns.repository.InstitutionRepository;
import com.gh.gov.ns.repository.RoleRepository;
import com.gh.gov.ns.repository.SuppliersEntryRepository;
import com.gh.gov.ns.repository.UserRepository;

@Controller
@SessionAttributes({"institutionDetails", "supplierDetails"})
public class IndexController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private InstitutionEntryRepository institutionEntryRepository;
	
	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private SuppliersEntryRepository suppliersEntryRepository;

	@GetMapping("/login")
	public String index() {
		return "login";
	}

	@GetMapping({ "/", "/dashboard" })
	public String dashboard(Principal principal, Model model) {
		Integer totalInstitutionVehicles = institutionEntryRepository.findAll().size();
		Integer totalSuppliersVehicles = suppliersEntryRepository.findAll().size();
		Integer totalReconciledVehicles = institutionEntryRepository.reconciledVehicles().size();
		System.out.println(".........."+totalReconciledVehicles);
		Integer totalUnReconciledVehicles = (totalSuppliersVehicles + totalInstitutionVehicles) -(totalReconciledVehicles);
		Integer auctionedvehicles = institutionEntryRepository.auctionedVehicles().size();
		/*Integer unreconciledSuppliersVehicles = totalSuppliersVehicles - totalReconciledVehicles;
		Integer unreconciledInstitutionVehicles = totalInstitutionVehicles - totalReconciledVehicles;*/

		model.addAttribute("totalInstitution", totalInstitutionVehicles);
		model.addAttribute("totalSuppliers", totalSuppliersVehicles);
		model.addAttribute("totalReconciledVehicles", totalReconciledVehicles);
		model.addAttribute("totalUnReconciledVehicles", totalUnReconciledVehicles);
		model.addAttribute("auctionedvehicles", auctionedvehicles);
		/*model.addAttribute("unreconciledSuppliersVehicles", unreconciledSuppliersVehicles);
		model.addAttribute("unreconciledInstitutionVehicles", unreconciledInstitutionVehicles);*/

		String redirectPage = "login";
		User user = userRepository.findUserByUsername(principal.getName());
		if (user != null) {
			Role role = roleRepository.findOne(user.getRoleId());
			Institution firstLoginOfInstitution = institutionRepository.findInstitutionByName(user.getDepartmentIdentifier());
			Institution isInstitutionProfileComplete= institutionRepository.isInstitutionProfileComplete(user.getDepartmentIdentifier());
			if (role.getRoleName().equalsIgnoreCase("INSTITUTION")) {
				if(firstLoginOfInstitution != null) {
					if(isInstitutionProfileComplete !=null) {
						redirectPage = "redirect:/institutions_entries";
					}else {
						redirectPage = "redirect:/institution_profile";
					}		
				model.addAttribute("institutionDetails", firstLoginOfInstitution);
				}
			} else if (role.getRoleName().equalsIgnoreCase("SUPPLIER")) {
				/*
				 * fix me later --> change firstLoginOfInstitution to a more meaningful name
				 */
				if(firstLoginOfInstitution != null) {
					if(isInstitutionProfileComplete !=null) {
						redirectPage = "redirect:/suppliers_entries";
					}else {
						redirectPage = "redirect:/suppliers_profile";
					}		
				model.addAttribute("supplierDetails", firstLoginOfInstitution);


				}
			} else if (role.getRoleName().equalsIgnoreCase("NS")) {
				redirectPage = "dashboard";
			}
		}
		return redirectPage;
	}

}