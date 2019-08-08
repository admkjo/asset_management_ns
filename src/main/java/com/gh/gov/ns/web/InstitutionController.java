package com.gh.gov.ns.web;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gh.gov.ns.model.Institution;
import com.gh.gov.ns.model.Role;
import com.gh.gov.ns.model.SuppliersEntries;
import com.gh.gov.ns.model.User;
import com.gh.gov.ns.model.Vehicle;
import com.gh.gov.ns.repository.InstitutionRepository;
import com.gh.gov.ns.repository.RoleRepository;
import com.gh.gov.ns.repository.UserRepository;
import com.gh.gov.ns.utils.CredentialsGenerator;
import com.gh.gov.ns.utils.FlashMessages;

@Controller
public class InstitutionController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CredentialsGenerator credentialsGenerator;
	
	@Autowired
	private InstitutionRepository institutionRepository;

	@GetMapping("/institutions")
	public String institution(Model model) {
		model.addAttribute("institution", new Institution());
		List<User> users = new ArrayList<>();
		List<User> supplierUsers = userRepository.findAll();
		for (User user : supplierUsers) {
			if (!user.getRoleId().contentEquals("5")) {
				Role role = roleRepository.findOne(user.getRoleId());
				if (role.getRoleName().contentEquals("INSTITUTION")) {
					users.add(user);
				}
			}
		}
		model.addAttribute("users", users);
		return "institutions";
	}

	@GetMapping("/add_institution")
	public String addInstitutions(Model model) {
		model.addAttribute("user", new User());
		List<Role> roles = roleRepository.findAll();
		String institutionRoleId = "";
		for (Role role : roles) {
			if (role.getRoleName().equalsIgnoreCase("INSTITUTION")) {
				institutionRoleId = role.getRoleId();
			}
		}
		model.addAttribute("institutionRoleId", institutionRoleId);
		return "add_institution";
	}

	@PostMapping("/add_institution")
	public String saveInstitutions(Model model, User user, @RequestParam("role_name") String role_name,
			RedirectAttributes ra) {
		Role role = roleRepository.findOne(role_name);
		Institution newInstitution = new Institution();
		if (role != null) {
      String username = credentialsGenerator.generateUsername(user.getDepartmentIdentifier().replaceAll("\\s", "").toLowerCase().substring(8, 13));
	  username = username + "@ns";
      String password = credentialsGenerator.generatePassword(username.substring(0, 4));
      user.setUsername(username);
      user.setPassword(password);
	  user.setRoleId(role.getRoleId());
	  userRepository.saveAndFlush(user);
	  newInstitution.setName(user.getDepartmentIdentifier());
	  institutionRepository.saveAndFlush(newInstitution);
	  ra.addFlashAttribute("flash", new FlashMessages("Account for institution " + user.getDepartmentIdentifier() +
				" has been created successfully", FlashMessages.Status.SUCCESS));
		}
		return "redirect:/institutions";
	}

	@GetMapping("/suppliers")
	public String suppliers(Model model) {
		model.addAttribute("institution", new Institution());
		List<User> users = new ArrayList<>();
		List<User> supplierUsers = userRepository.findAll();
		for (User user : supplierUsers) {
			if (!user.getRoleId().contentEquals("5")) {
				Role role = roleRepository.findOne(user.getRoleId());
				if (role.getRoleName().contentEquals("SUPPLIER")) {
					users.add(user);
				}
			}
		}
		model.addAttribute("supplierUsers", users);
		return "suppliers";
	}

	@GetMapping("/add_suppliers")
	public String addSuppliers(Model model) {
		model.addAttribute("user", new User());
		List<Role> roles = roleRepository.findAll();
		String supplierRoleId = "";
		for (Role role : roles) {
			if (role.getRoleName().equalsIgnoreCase("SUPPLIER")) {
				supplierRoleId = role.getRoleId();
			}
		}
		model.addAttribute("supplierRoleId", supplierRoleId);
		return "add_suppliers";
	}

	@PostMapping("/add_suppliers")
	public String saveSuppliers(Model model, User user, @RequestParam("role_name") String role_name,
			RedirectAttributes ra) {
		Role role = roleRepository.findOne(role_name);
		Institution newSupplier = new Institution();
		if (role != null) {
		      String username = credentialsGenerator.generateUsername(user.getDepartmentIdentifier().replaceAll("\\s", "").toLowerCase().substring(0, 5));
			  username = username + "@ns";
		      String password = credentialsGenerator.generatePassword(username.substring(0, 4));
		      user.setUsername(username);
		      user.setPassword(password);
			  user.setRoleId(role.getRoleId());
			  userRepository.saveAndFlush(user);
			  newSupplier.setName(user.getDepartmentIdentifier());
			  institutionRepository.saveAndFlush(newSupplier);
			  ra.addFlashAttribute("flash", new FlashMessages("Account for supplier " + user.getDepartmentIdentifier() +
						" has been created successfully", FlashMessages.Status.SUCCESS));
			}
		return "redirect:/suppliers";
	}

	@GetMapping("/edit_supplier")
	public String editSupplier(Model model, @RequestParam("id") String username) {
		System.out.println(username + "....................");
		User user = userRepository.findByUsername(username);
		model.addAttribute("user", user);
		return "edit_supplier";
	}

	@PostMapping("/edit_supplier")
	public String editSuppliers(Model model, User user, RedirectAttributes ra) {
		try {
			System.out.println("........" + user);
			userRepository.saveAndFlush(user);
			ra.addFlashAttribute("flash", new FlashMessages("Account for supplier " + user.getDepartmentIdentifier() +
					" has been updated successfully", FlashMessages.Status.SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/suppliers";
	}
	
	@GetMapping("/edit_institution")
	public String editInstitution(Model model, @RequestParam("id") String username) {
		User user = userRepository.findByUsername(username);
		model.addAttribute("user", user);
		return "edit_institution";
	}

	@PostMapping("/edit_institution")
	public String editInstitution(Model model, User user, RedirectAttributes ra) {
		try {
			System.out.println("........" + user);
			userRepository.saveAndFlush(user);
			ra.addFlashAttribute("flash", new FlashMessages("Account for institution " + user.getDepartmentIdentifier() +
					" has been updated successfully", FlashMessages.Status.SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/institutions";
	}
	
}
