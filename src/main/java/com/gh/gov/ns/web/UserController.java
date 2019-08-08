package com.gh.gov.ns.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gh.gov.ns.model.Role;
import com.gh.gov.ns.model.User;
import com.gh.gov.ns.repository.RoleRepository;
import com.gh.gov.ns.repository.UserRepository;
import com.gh.gov.ns.utils.CredentialsGenerator;
import com.gh.gov.ns.utils.FlashMessages;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CredentialsGenerator credentialsGenerator;

	@GetMapping("/users")
	public String users() {
		return "users";
	}

	@GetMapping("/useraccount")
	public String userAccount(Model model) {

		List<User> users = new ArrayList<>();
		List<User> nationalSecUsers = userRepository.findAll();
		for (User user : nationalSecUsers) {
			if (!user.getRoleId().contentEquals("5")) {
				Role role = roleRepository.findOne(user.getRoleId());
				if (role.getRoleName().contentEquals("NS")) {
					users.add(user);
				}
			}
		}
		model.addAttribute("users", users);
		return "useraccount";
	}

	@GetMapping("/add_user")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		List<Role> roles = roleRepository.findAll();
		String nsRoleId = "";
		for (Role role : roles) {
			if (role.getRoleName().equalsIgnoreCase("NS")) {
				nsRoleId = role.getRoleId();
			}
		}
		model.addAttribute("nsRoleId", nsRoleId);
		return "add_user";
	}

	@PostMapping("/add_user")
	public String saveUser(Model model, User user, @RequestParam("role_name") String role_name,
			RedirectAttributes ra) {
		Role role = roleRepository.findOne(role_name);
		if (role != null) {
			String usernameKey=user.getName() + user.getDepartmentIdentifier() + user.getPosition();
			String username = credentialsGenerator.generateUsername(usernameKey.replaceAll("\\s", "").toLowerCase().substring(0, 5));
			  username = username + "@ns";
		      String password = credentialsGenerator.generatePassword(username.substring(0, 4));
		      user.setUsername(username);
		      user.setPassword(password);
			  user.setRoleId(role.getRoleId());
			  userRepository.saveAndFlush(user);
			  ra.addFlashAttribute("flash", new FlashMessages("User " + username +
						" has been created successfully", FlashMessages.Status.SUCCESS));
		}
		return "redirect:/useraccount";
	}

	@PostMapping("/edit_user")
	public String editUser(Model model, User user, RedirectAttributes ra) {
		userRepository.saveAndFlush(user);
		ra.addFlashAttribute("flash", new FlashMessages("Details for user " + user.getUsername() +
				" has been updated successfully", FlashMessages.Status.SUCCESS));
		return "redirect:/useraccount";
	}

	@GetMapping("/edit_user")
	public String editUser(Model model, @RequestParam("id") String username) {
		User user = userRepository.findByUsername(username);
		model.addAttribute("user", user);
		return "edit_user";
	}
}
