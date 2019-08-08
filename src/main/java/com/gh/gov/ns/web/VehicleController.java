package com.gh.gov.ns.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gh.gov.ns.model.Vehicle;

@Controller
public class VehicleController {
	
	
	@GetMapping("/vehicles/add")
	public String addVehicles(Model model){
		model.addAttribute("vehicle", new Vehicle());
		return "addvehicle";
	}
	
	@PostMapping("/vehicles")
	public String addVehicle(Vehicle vehicle){
		System.out.println("dfsdf " + vehicle);
		//vehicleRepository.saveAndFlush(vehicle);
		return "redirect:/vehicles";
	}	

}
