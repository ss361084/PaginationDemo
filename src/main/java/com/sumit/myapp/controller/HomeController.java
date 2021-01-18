package com.sumit.myapp.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sumit.myapp.model.Human;
import com.sumit.myapp.serviceImpl.HumanServiceImpl;

@Controller
public class HomeController {

	@Autowired
	private HumanServiceImpl humanService;
	@Autowired
	private MessageSource messageSource;
	@Value("${default.page.size}")
	private int pageSize;

//	@GetMapping(value = {"/"})
//	public ModelAndView getHomePage(Model model, HttpServletRequest req, HttpServletResponse res, 
//			@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
//			@RequestParam(value = "pageSize",defaultValue = "5") int pageSize,
//			@RequestParam(value = "sortfield",defaultValue = "humanId") String sortBy) {
//		ModelAndView mv = new ModelAndView();
////		mv.addObject("humanList", humanService.getAllHumanByDescOrder());
//		mv.addObject("humanList", humanService.getPaginationHumanData(pageNo, pageSize, sortBy));
//		mv.addObject("totalRecord", humanRepo.count());
//		mv.setViewName("Home");
//		return mv;
//	}
	
	@GetMapping(value = {"/"})
	public String getHomePage(Model model) {
//		return findPaginated(1, model);
		return findPaginatedWithSort(1,"humanId",model);
	}
	
	@GetMapping(value = {"/showaddhuman"})
	public ModelAndView showAddStudentPage(Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("human", new Human());
		mv.addObject("operation", "add");
		mv.setViewName("AddHuman");
		return mv;
	}
	
	@PostMapping(value = {"/addhuman"})
	public String addHuman(@ModelAttribute @Valid Human human, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest req) {
		boolean success = false;
		String errMsg = "";
		String opeartion = req.getParameter("operation") == null ? "" : req.getParameter("operation");
		try {
			if(bindingResult.hasErrors()) {
				return "AddHuman";
			}
			humanService.saveHuman(human);
			success = true;
		} catch(Exception ex) {
			errMsg = ex.getMessage();
			ex.printStackTrace();
		}
		if(success) {
			redirectAttributes.addFlashAttribute("successMsg", 
					!opeartion.isEmpty() && opeartion.equalsIgnoreCase("add") ? messageSource.getMessage("msg_successfully_added", null, Locale.ENGLISH)
							: messageSource.getMessage("msg_successfully_update", null, Locale.ENGLISH));
			
		} else {
			redirectAttributes.addFlashAttribute("errMsg", errMsg.isEmpty() ? messageSource.getMessage("msg_error_occur", null, Locale.ENGLISH) : errMsg);
		}
		return "redirect:/";
	}
	
	@GetMapping(value = {"/deletehuman/{humanid}"})
	public String deleteHuman(@PathVariable(value = "humanid") long humanId, RedirectAttributes redirectAttributes) {
		boolean success = false;
		String errMsg = "";
		try {
			if(humanId != 0) {
				Human humanObj = humanService.getHumanById(humanId);
				if(humanObj != null) {
					humanService.deleteHuman(humanObj);
					success = true;
				} else {
					errMsg = messageSource.getMessage("msg_user_not_avaliable", null, Locale.ENGLISH);
				}
			} else {
				errMsg = messageSource.getMessage("msg_user_not_valid", null, Locale.ENGLISH);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(success) {
			redirectAttributes.addFlashAttribute("successMsg", messageSource.getMessage("msg_successfully_deleted", null, Locale.ENGLISH));
		} else {
			redirectAttributes.addFlashAttribute("errMsg", errMsg.isEmpty() ? messageSource.getMessage("msg_error_occur", null, Locale.ENGLISH) : errMsg);
		}
		return "redirect:/";
	}
	
	@GetMapping(value = {"/updatehuman/{humanid}"})
	public ModelAndView updateHuman(@PathVariable(value = "humanid") long humanId, Model model) {
		boolean success = false;
		String errMsg = "";
		Human human = null;
		ModelAndView mv = new ModelAndView();
		
		try {
			if(humanId != 0) {
				human = humanService.getHumanById(humanId);
				if(human != null) {
					mv.addObject("human", human);
					success = true;
				} else {
					errMsg = messageSource.getMessage("msg_user_not_avaliable", null, Locale.ENGLISH);
				}
			} else {
				errMsg = messageSource.getMessage("msg_user_not_valid", null, Locale.ENGLISH);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(!success) {
			model.addAttribute("errMsg", errMsg.isEmpty() ? messageSource.getMessage("msg_error_occur", null, Locale.ENGLISH) : errMsg);
		}
		mv.addObject("operation", "update");
		mv.setViewName("AddHuman");
		return mv;
	}
	
	@GetMapping(value = {"/page/{pageNo}"})
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, 
			Model model) {
		try {
			Page<Human> page = humanService.getPaginateHumanData(pageNo, pageSize);
			List<Human> listHuman = page.hasContent() ? page.getContent() : null;
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listHuman", listHuman);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "Home";
	}
	
	@GetMapping(value = {"/page/{pageNo}/{sorted}"})
	public String findPaginatedWithSort(@PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "sorted") String sorted, Model model) {
		try {
			Page<Human> page = humanService.getPaginateSortedHumanData(pageNo, pageSize, sorted);
			List<Human> listHuman = page.hasContent() ? page.getContent() : null;
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listHuman", listHuman);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "Home";
	}
}
