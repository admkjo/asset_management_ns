package com.gh.gov.ns.web;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gh.gov.ns.model.InstitutionEntries;
import com.gh.gov.ns.repository.InstitutionEntryRepository;
import com.gh.gov.ns.repository.SuppliersEntryRepository;

@Controller
public class ReportController {
	private static final String FILE_FORMAT = "format";

    private static final String DATASOURCE = "datasource";

	@Autowired
	private InstitutionEntryRepository institutionEntryRepository;
	
	@Autowired
	private SuppliersEntryRepository suppliersEntryRepository;
	
	@Autowired
	private DataSource dataSource;

	@GetMapping("/institutions_report")
	public String institutionsReport(Model model) {
		model.addAttribute("institutionEntries", institutionEntryRepository.findAll());
		return "institutions_report";
	}

	@GetMapping("/suppliers_report")
	public String suppliersReport(Model model) {
   model.addAttribute("suppliersEntriesReport", suppliersEntryRepository.findAll());
		return "suppliers_report";
	}

	@GetMapping("/reconciliation_report")
	public String reconciliationReport(Model model) {
List<InstitutionEntries> reconciliationReport = institutionEntryRepository.reconcilationReport("");
   model.addAttribute("reconciliationReport", reconciliationReport);
		return "reconciliation_report";
	} 
	
	@GetMapping("/updateSuppliersEntries")
	public String updateInstitutionReconciliationReport(Model model, @RequestParam(value="institution", required=false) String institution) {
List<InstitutionEntries>  reconciliationReport= institutionEntryRepository.reconcilationReport(institution);
   model.addAttribute("reconciliationReport", reconciliationReport);
		return "reconciliation_report";
	}
	
	@GetMapping("/supplied_vehicles_report")
	public String suppliedVehiclesReport(Model model) {
		return "supplied_vehicles_report";
	}
	
	@GetMapping("/institutions_entries_report")
    public ModelAndView getRptByParam(ModelMap modelMap, ModelAndView modelAndView) {
        modelMap.put(DATASOURCE, dataSource);
        modelMap.put(FILE_FORMAT, "pdf");
        modelAndView = new ModelAndView("rpt_institutions", modelMap);
        return modelAndView;
    }
	
	
}
