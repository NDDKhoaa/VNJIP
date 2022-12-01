package vnjip.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vnjip.entity.Account;

@Controller
public class AccountController {

	@RequestMapping("/viewAccounts")
	public String viewAccount(Model model) {
		return "/account/viewAccounts";
	}

	@RequestMapping(value = "/viewAccountDetails", method = RequestMethod.GET)
	public ModelAndView viewAccountDetails(@RequestParam(value = "accountNumber") long accountNumber) {
		ModelAndView mav = new ModelAndView("/account/viewAccountDetails");
		return mav;
	}

	@RequestMapping("/createAccount")
	public String createAccount(Model model) {
		return "/account/createAccount";
	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("accountForm") Account account) {
		return "redirect:/viewAccounts";
	}

	@RequestMapping("/modifyAccount")
	public ModelAndView modifyAccount(@RequestParam("accountNumber") long accountNumber) {
		ModelAndView mav = new ModelAndView("/account/modifyAccount");
		return mav;
	}

	@RequestMapping("/deleteAccount")
	public String deleteAccount(@RequestParam("accountNumber") long accountNumber) {
		return "redirect:/viewAccounts";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
