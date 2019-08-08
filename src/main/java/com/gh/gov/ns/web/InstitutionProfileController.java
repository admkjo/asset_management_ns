package com.gh.gov.ns.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gh.gov.ns.model.Institution;
import com.gh.gov.ns.repository.InstitutionRepository;
import com.gh.gov.ns.utils.FlashMessages;

@Controller
public class InstitutionProfileController {
	@Autowired
	private InstitutionRepository institutionRepository;

	@GetMapping("/institution_profile")
	public String showProfile(Model model) {
		model.addAttribute("institution", new Institution());
		return "institution_profile";
	}

	@PostMapping("/institution_profile")
	public String saveinstitutionProfile(Model model, Institution institution,
			@RequestParam("institutionId") String institutionId, @RequestParam("name") String name
			,RedirectAttributes ra) {
		institution.setInstitutionId(institutionId);
		institution.setName(name);
		institutionRepository.saveAndFlush(institution);
		ra.addFlashAttribute("flash", new FlashMessages("Profile for institution " + name +
				" has been saved successfully", FlashMessages.Status.SUCCESS));
		return "redirect:/institutions_entries";
	}

	@GetMapping("/suppliers_profile")
	public String showProfileSupplier(Model model) {
		model.addAttribute("institution", new Institution());
		return "suppliers_profile";
	}

	@PostMapping("/suppliers_profile")
	public String saveSuppliersProfile(Model model, Institution institution,
			@RequestParam("supplierId") String supplierId, @RequestParam("name") String name
			,RedirectAttributes ra) {
		institution.setInstitutionId(supplierId);
		institution.setName(name);
		institutionRepository.saveAndFlush(institution);
		ra.addFlashAttribute("flash", new FlashMessages("Profile for supplier " + name +
				" has been saved successfully", FlashMessages.Status.SUCCESS));
		return "redirect:/suppliers_entries";
	}

	

	

}
