package vnjip.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import vnjip.entity.base.Role;
import vnjip.model.BaseModel;
import vnjip.services.Impl.AccountServiceImpl;
import vnjip.services.Impl.AccountStatusServiceImpl;
import vnjip.services.Impl.RoleServiceImpl;

@Controller
public class AccountController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@Autowired
	private AccountStatusServiceImpl accountStatusServiceImpl;

	@Autowired
	private RoleServiceImpl roleServiceImpl;

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
		model.addAttribute("accountForm", new BaseModel());
		List<AccountStatus> accountStatusList = accountStatusServiceImpl.listAll();
		model.addAttribute("accountStatusList", accountStatusList);
		List<Role> roleList = roleServiceImpl.listAll();
		model.addAttribute("roleList", roleList);
		return "/account/createAccount";
	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public String saveAccount(@ModelAttribute("accountForm") BaseModel baseModel) {
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(baseModel.getAccountStatusShort());
		List<Role> roles = new ArrayList<Role>();
		Role role = roleServiceImpl.findByNumber(baseModel.getRoleNumber());
		roles.add(role);
		String pwdEncrypt = bCryptPasswordEncoder.encode(baseModel.getAccountPassword());
		Account account = new Account(baseModel.getAccountUsername(), baseModel.getAccountEmail(), pwdEncrypt,
				new HashSet<>(roles), accountStatus);
		accountServiceImpl.save(account);
		return "redirect:/viewAccounts";
	}

	@RequestMapping("/modifyAccount")
	public ModelAndView modifyAccount(@RequestParam("accountNumber") long accountNumber) {
		ModelAndView mav = new ModelAndView("/account/modifyAccount");
		Account account = accountServiceImpl.findByNumber(accountNumber);
		AccountStatus accountStatus = account.getAccountStatus();
		List<Role> roles = new ArrayList<Role>();
		for (Role role : account.getRoles()) {
			roles.add(role);
		}
		mav.addObject("updateAccount", account);
		mav.addObject("updateAccountStatus", accountStatus);
		mav.addObject("updateRole", roles);
		List<AccountStatus> accountStatusList = accountStatusServiceImpl.listAll();
		mav.addObject("accountStatusList", accountStatusList);
		List<Role> roleList = roleServiceImpl.listAll();
		mav.addObject("roleList", roleList);
		return mav;
	}

	@RequestMapping("/deleteAccount")
	public String deleteAccount(@RequestParam("accountNumber") long accountNumber) {
		accountServiceImpl.deleteByNumber(accountNumber);
		return "redirect:/viewAccounts";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
