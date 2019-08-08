package com.gh.gov.ns.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gh.gov.ns.model.Documents;
import com.gh.gov.ns.model.Institution;
import com.gh.gov.ns.model.InstitutionEntries;
import com.gh.gov.ns.model.SuppliersEntries;
import com.gh.gov.ns.repository.DocumentsRepository;
import com.gh.gov.ns.repository.InstitutionEntryRepository;
import com.gh.gov.ns.repository.InstitutionRepository;
import com.gh.gov.ns.repository.SuppliersEntryRepository;
import com.gh.gov.ns.utils.DateFormatter;
import com.gh.gov.ns.utils.FlashMessages;

@Controller
@SessionAttributes({"currentInstitutionTrxId", "currentSupplierTrxId"})
public class EntriesController {

	@Autowired
	private DocumentsRepository DocumentsRepository;
	
	private int currentInstitutionTrxId;
	
	private int currentSupplierTrxId;

	private static String UPLOADED_FOLDER = "C://Users/Quabena/Desktop/uploads/";

	@Autowired
	private InstitutionEntryRepository institutionEntryRepository;

	@Autowired
	private SuppliersEntryRepository suppliersEntryRepository;
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	@Autowired
	private DateFormatter dateFormatter;

	@GetMapping("/institutions_entries")
	public String institutionsEntries(Model model) {
		List<InstitutionEntries> institutionEntries = institutionEntryRepository.findAll();
		model.addAttribute("institutionEntries", institutionEntries);
		return "institutions_entries";
	}

	@GetMapping("/institutions_entries_new")
	public String institutionsEntriesNew(Model model) {
		model.addAttribute("institutionEntry", new InstitutionEntries());
		List<Institution> suppliers = institutionRepository.getAllSuppliers();
		model.addAttribute("suppliers", suppliers);
		return "institutions_entries_new";
	}

	@PostMapping("/institutions_entries_new")
	public String saveinstitutionsEntriesNew(Model model,@RequestParam("typeOfVehicle") String type[],
			@RequestParam("manufYear") String year[], @RequestParam("engineNumber") String engineNumber[],
			@RequestParam("chassisNumber") String chassisNumber[], @RequestParam("companyReceivedFrom") String companyReceivedFrom[],
			@RequestParam("dateReceived") String dateReceived[], @RequestParam("status") String status[],
			@RequestParam("reasonIfAuctioned") String reasonIfAuctioned[],RedirectAttributes ra) {
		    
            Random rand = new Random();
    		int trxId=rand.nextInt(10000000);
    		currentInstitutionTrxId = trxId;
            for(int i=1; i<type.length; i++){
            	InstitutionEntries entries = new InstitutionEntries();
                entries.setDateOfEntry(dateFormatter.currentDateFormmater(LocalDate.now()));
            entries.setTransactionId(String.valueOf(currentInstitutionTrxId));
            entries.setTypeOfVehicle(type[i]);
            entries.setManufYear(year[i]);
            entries.setEngineNumber(engineNumber[i]);
            entries.setChassisNumber(chassisNumber[i]);
            entries.setCompanyReceivedFrom(companyReceivedFrom[i]);
            entries.setDateReceived(dateReceived[i]);
            entries.setStatus(status[i]);
            entries.setReasonIfAuctioned(reasonIfAuctioned[i]);
            institutionEntryRepository.saveAndFlush(entries);
            }
         ra.addFlashAttribute("flash", new FlashMessages("Entries for institution " +
    				" have been saved successfully", FlashMessages.Status.SUCCESS));
		return "redirect:/attach_docs_inst";
	}

	@GetMapping("/suppliers_entries_new")
	public String suppliersEntriesNew(Model model) {
		model.addAttribute("suppliersEntries", new SuppliersEntries());
		model.addAttribute("institutions", institutionRepository.getAllInstitutions());
		return "suppliers_entries_new";
	}

	@PostMapping("/attach_docs_suppl")
	public String newattAchment(Model model, SuppliersEntries suppliers, @RequestParam("file") MultipartFile file[],
			RedirectAttributes ra) {
		if (file.length == 0) {
			ra.addFlashAttribute("message", "Please select a file to upload");
		}

		try {
			for (int i = 1; i < file.length; i++) {
				byte[] bytes = file[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + file[i].getOriginalFilename());
				Files.write(path, bytes);
				Documents newDoc = new Documents();
				newDoc.setDocumentLocation(UPLOADED_FOLDER + "/" + file[i].getOriginalFilename());
				newDoc.setSuppliersTrxId(currentSupplierTrxId);
				DocumentsRepository.saveAndFlush(newDoc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/suppliers_entries";
	}

	@PostMapping("/suppliers_entries_new")
	public String saveSuppliersEntriesNew(Model model, @RequestParam("typeOfVehicle") String type[],
			@RequestParam("manufYear") String year[], @RequestParam("engineNumber") String engineNumber[],
			@RequestParam("chassisNumber") String chassisNumber[], @RequestParam("institutionSuppliedTo") String institutionSuppliedTo[],
			@RequestParam("dateSupplied") String dateSupplied[], @RequestParam("importDutyExemption") int importDutyExemption[],
			@RequestParam("importDutyDetails") String importDutyDetails[], @RequestParam("paymentChequeDetails") String paymentChequeDetails[],
			@RequestParam("dvlaRegistrationDetails") String dvlaRegistrationDetails[],
			RedirectAttributes ra) {
		Random rand = new Random();
		int trxId=rand.nextInt(10000000);
		currentSupplierTrxId = trxId;
        for(int i=1; i<type.length; i++){
        	SuppliersEntries entries = new SuppliersEntries();
            entries.setDateOfEntry(dateFormatter.currentDateFormmater(LocalDate.now()));
        entries.setTransactionId(String.valueOf(currentSupplierTrxId));
        entries.setTypeOfVehicle(type[i]);
        entries.setManufYear(year[i]);
        entries.setEngineNumber(engineNumber[i]);
        entries.setChassisNumber(chassisNumber[i]);
        entries.setInstitutionSuppliedTo(institutionSuppliedTo[i]);
        entries.setDateSupplied(dateSupplied[i]);
        entries.setImportDutyExemption(importDutyExemption[i]);
        entries.setImportDutyDetails(importDutyDetails[i]);
        entries.setPaymentChequeDetails(paymentChequeDetails[i]);
        entries.setDvlaRegistrationDetails(dvlaRegistrationDetails[i]);
        suppliersEntryRepository.saveAndFlush(entries);
        }
        ra.addFlashAttribute("flash", new FlashMessages("Entries for supplier " +
				" have been saved successfully", FlashMessages.Status.SUCCESS));
        return  "redirect:/attach_docs_suppl";
	}

	@GetMapping("/suppliers_entries")
	public String suppliersEntries(Model model) {
		List<SuppliersEntries> suppliersEntries = suppliersEntryRepository.findAll();
		model.addAttribute("suppliersEntries", suppliersEntries);
		return "suppliers_entries";
	}

	@GetMapping("/attach_docs_suppl")
	public String attachDocsSuppl(Model model) {
		List<InstitutionEntries> institutionEntries = institutionEntryRepository.findAll();
		model.addAttribute("institutionEntries", institutionEntries);
		return "attach_docs_suppl";
	}

	@GetMapping("/attach_docs_inst")
	public String attachDocsInst(Model model) {
		List<InstitutionEntries> institutionEntries = institutionEntryRepository.findAll();
		model.addAttribute("institutionEntries", institutionEntries);
		return "attach_docs_inst";
	}
	
	@PostMapping("/attach_docs_inst")
	public String saveInstitutionDocuments(Model model, @RequestParam("file") MultipartFile file[], 
			RedirectAttributes ra) {
		if (file.length == 0) {
			ra.addFlashAttribute("message", "Please select a file to upload");
		}

		try {
			for (int i = 1; i < file.length; i++) {
				byte[] bytes = file[i].getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + file[i].getOriginalFilename());
				Files.write(path, bytes);
				Documents newDoc = new Documents();
				newDoc.setDocumentLocation(UPLOADED_FOLDER + "/" + file[i].getOriginalFilename());
				newDoc.setInstitutionTrxId(currentInstitutionTrxId);
				DocumentsRepository.saveAndFlush(newDoc);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		return "redirect:/institutions_entries";
	}

}
