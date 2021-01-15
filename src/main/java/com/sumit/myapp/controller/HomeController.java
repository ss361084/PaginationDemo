package com.sumit.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sumit.myapp.model.Human;
import com.sumit.myapp.repository.HumanRepo;
import com.sumit.myapp.service.HumanService;

@Controller
public class HomeController {

	@Autowired
	private HumanService humanService;
	@Autowired
	private HumanRepo humanRepo;

	@GetMapping(value = {"/"})
	public ModelAndView getHomePage(Model model, HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value = "pageNo",defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize",defaultValue = "5") int pageSize,
			@RequestParam(value = "sortfield",defaultValue = "humanId") String sortBy) {
		ModelAndView mv = new ModelAndView();
//		mv.addObject("humanList", humanService.getAllHumanByDescOrder());
		mv.addObject("humanList", humanService.getPaginationHumanData(pageNo, pageSize, sortBy));
		mv.addObject("totalRecord", humanRepo.count());
		mv.setViewName("Home");
		return mv;
	}
	
	@GetMapping(value = {"/showaddhuman"})
	public ModelAndView showAddStudentPage(Model model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("human", new Human());
		mv.setViewName("AddHuman");
		return mv;
	}
	
	@PostMapping(value = {"/addhuman"})
	public String addHuman(@ModelAttribute @Valid Human human, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		boolean success = false;
		String errMsg = "";
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
			redirectAttributes.addFlashAttribute("successMsg", "Successfully Added");
		} else {
			redirectAttributes.addFlashAttribute("errMsg", errMsg.isEmpty() ? "Error Occur" : errMsg);
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
					errMsg = "User Not Avaliable";
				}
			} else {
				errMsg = "User Not Valid";
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(success) {
			redirectAttributes.addFlashAttribute("successMsg", "User Successfully Deleted");
		} else {
			redirectAttributes.addFlashAttribute("errMsg", errMsg.isEmpty() ? "Error Occured" : errMsg);
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
					errMsg = "User Not Avaliable";
				}
			} else {
				errMsg = "User Not Valid";
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		if(!success) {
			model.addAttribute("errMsg", errMsg.isEmpty() ? "Error Occured" : errMsg);
		}
		mv.setViewName("AddHuman");
		return mv;
	}
}
