package com.gh.gov.ns.web;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gh.gov.ns.model.Memo;
import com.gh.gov.ns.model.User;
import com.gh.gov.ns.repository.MemoRepository;
import com.gh.gov.ns.repository.UserRepository;
import com.gh.gov.ns.utils.DateFormatter;
import com.gh.gov.ns.utils.FlashMessages;


@Controller
public class MemoController {
	@Autowired
	private MemoRepository memoRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DateFormatter dateFormatter;

	@GetMapping("/inbox_memo")
	public String inboxmemo(Model model, Principal principal) {
		User user = userRepository.findUserByUsername(principal.getName());
		List<Memo> inboxMemo = memoRepository.inboxMemo(user.getUserId());
		model.addAttribute("inbox", inboxMemo);
		return "inbox_memo";
	}

	@GetMapping("/compose_memo")
	public String composememo(Model model) {
		model.addAttribute("memo", new Memo());
		model.addAttribute("internalContacts", userRepository.findInternalUsers());
		return "compose_memo";
	}

	@PostMapping("/compose_memo")
	public String createMemo(Memo memo, @RequestParam("to") String to[], Principal principal,
			RedirectAttributes ra) {
		User user = userRepository.findUserByUsername(principal.getName());
		for(int i=1;i < to.length; i++) {
			Memo newMemo=new Memo();
			User recepient = userRepository.findUserByUsername(to[i]);
			newMemo.setRecipients(recepient.getUserId());
			newMemo.setSender(user.getUserId());
			newMemo.setSubject(memo.getSubject());
			newMemo.setContent(memo.getContent());
			newMemo.setDate(dateFormatter.currentDateFormmater(LocalDate.now()));
			memoRepository.saveAndFlush(newMemo);
		}
		ra.addFlashAttribute("flash", new FlashMessages("Your memo " +
				" has been sent successfully", FlashMessages.Status.SUCCESS));
		return "redirect:/compose_memo";
	}

	@GetMapping("/draft_memo")
	public String draftmemo(Model model) {
		return "draft_memo";
	}

	@GetMapping("/sent_memo")
	public String sentmemo(Model model, Principal principal) {
		User user = userRepository.findUserByUsername(principal.getName());
		List<Memo> sentMemo = memoRepository.sentMemo(user.getUserId());
		List<Memo> sentMemos = new ArrayList<>();
		for (Memo memo : sentMemo) {
			User recepientUser = userRepository.findOne(memo.getRecipients());
			if(recepientUser!=null) {
			String username = recepientUser.getUsername();
			memo.setRecipients(username);
			sentMemos.add(memo);
			}
		}
		model.addAttribute("sentMemo", sentMemos);
		return "sent_memo";
	}

	@GetMapping("/selected_memo")
	public String selectedmemo(Model model) {
		return "selected_memo";
	}

	@GetMapping("/memo_preview")
	public String memoPreview(Model model) {

		return "memo_preview";
	}
}
