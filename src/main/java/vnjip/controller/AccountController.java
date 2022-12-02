package vnjip.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vnjip.entity.Account;
import vnjip.entity.base.AccountStatus;
import vnjip.model.BaseModel;
import vnjip.services.Impl.AccountServiceImpl;
import vnjip.services.Impl.AccountStatusServiceImpl;

@Controller
public class AccountController {

	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@Autowired
	private AccountStatusServiceImpl accountStatusServiceImpl;

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping("/viewAccounts")
	public String viewAccount(Model model) {
		List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
		List<Account> listAccount = accountServiceImpl.listAll();

		for (Account account : listAccount) {
			if (listAccount != null) {
				String accountStatusShort = account.getAccountStatus().getAccountStatusShort();
				AccountStatus accountStatus = accountStatusServiceImpl.findByShort(accountStatusShort);
				if (accountStatus != null) {
					BaseModel baseModel = new BaseModel(account, accountStatus);
					listBaseModel.add(baseModel);
				}
			}
		}
		model.addAttribute("listAccount", listBaseModel);
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
