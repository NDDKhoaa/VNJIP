package vnjip.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import vnjip.entity.Agent;
import vnjip.entity.Client;
import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.Role;
import vnjip.model.BaseModel;
import vnjip.services.Impl.AccountServiceImpl;
import vnjip.services.Impl.AccountStatusServiceImpl;
import vnjip.services.Impl.AgentServiceImpl;
import vnjip.services.Impl.ClientServiceImpl;
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

	@Autowired
	private ClientServiceImpl clientServiceImpl;

	@Autowired
	private AgentServiceImpl agentServiceImpl;

	@GetMapping(value = { "/login", "/" })
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
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
		Account account = accountServiceImpl.findByNumber(accountNumber);
		AccountStatus accountStatus = account.getAccountStatus();
		Set<Role> listRole = account.getRoles();
		Agent agent = account.getAgent();
		Client client = account.getClient();
		if (agent == null && client != null) {
			BaseModel baseModel = new BaseModel(account, accountStatus, listRole, client);
			mav.addObject("baseModel", baseModel);
		}
		if (agent != null && client == null) {
			BaseModel baseModel = new BaseModel(account, accountStatus, listRole, agent);
			mav.addObject("baseModel", baseModel);
		}
		if (agent == null && client == null) {
			BaseModel baseModel = new BaseModel(account, accountStatus, listRole);
			mav.addObject("baseModel", baseModel);
		}
		return mav;
	}

	@RequestMapping("/createAgentAccount")
	public String createAgentAccount(Model model) {
		model.addAttribute("accountForm", new BaseModel());
		List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
		model.addAttribute("listAccountStatus", listAccountStatus);
		List<Role> listRoles = roleServiceImpl.listAll();
		model.addAttribute("listRoles", listRoles);
		List<Agent> listAgent = agentServiceImpl.listAll();
		model.addAttribute("listAgent", listAgent);
		return "/account/createAgentAccount";
	}

	@RequestMapping("/createClientAccount")
	public String createClientAccount(Model model) {
		model.addAttribute("accountForm", new BaseModel());
		List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
		model.addAttribute("listAccountStatus", listAccountStatus);
		List<Role> listRoles = roleServiceImpl.listAll();
		model.addAttribute("listRoles", listRoles);
		List<Client> listClients = clientServiceImpl.listAll();
		model.addAttribute("listClient", listClients);
		return "/account/createClientAccount";
	}

	@RequestMapping(value = "/saveAgentAccount", method = RequestMethod.POST)
	public String saveAgentAccount(@ModelAttribute("accountForm") BaseModel baseModel) {
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(baseModel.getAccountStatusShort());
		List<Role> roles = new ArrayList<Role>();
		Role role = roleServiceImpl.findByNumber(baseModel.getRoleNumber());
		roles.add(role);
		String pwdEncrypt = bCryptPasswordEncoder.encode(baseModel.getAccountPassword());
		Agent agent = agentServiceImpl.findByNumber(baseModel.getAgentNumber());
		if (agent != null) {
			Account account = new Account(baseModel.getAccountUsername(), baseModel.getAccountEmail(), pwdEncrypt,
					new HashSet<>(roles), accountStatus, agent);
			accountServiceImpl.save(account);
		}
		if (agent == null) {
			Account account = new Account(baseModel.getAccountUsername(), baseModel.getAccountEmail(), pwdEncrypt,
					new HashSet<>(roles), accountStatus);
			accountServiceImpl.save(account);
		}
		return "redirect:/viewAccounts";
	}

	@RequestMapping(value = "/saveClientAccount", method = RequestMethod.POST)
	public String saveClientAccount(@ModelAttribute("accountForm") BaseModel baseModel) {
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(baseModel.getAccountStatusShort());
		List<Role> roles = new ArrayList<Role>();
		Role role = roleServiceImpl.findByNumber(baseModel.getRoleNumber());
		roles.add(role);
		String pwdEncrypt = bCryptPasswordEncoder.encode(baseModel.getAccountPassword());
		Client client = clientServiceImpl.findByNumber(baseModel.getClientNumber());
		if (client != null) {
			Account account = new Account(baseModel.getAccountUsername(), baseModel.getAccountEmail(), pwdEncrypt,
					new HashSet<>(roles), accountStatus, client);
			accountServiceImpl.save(account);
		}
		if (client == null) {
			Account account = new Account(baseModel.getAccountUsername(), baseModel.getAccountEmail(), pwdEncrypt,
					new HashSet<>(roles), accountStatus);
			accountServiceImpl.save(account);
		}
		return "redirect:/viewAccounts";
	}

	@RequestMapping("/modifyAgentAccount")
	public ModelAndView modifyAgentAccount(@RequestParam("accountNumber") long accountNumber) {
		ModelAndView mav = new ModelAndView("/account/modifyAgentAccount");
		Account account = accountServiceImpl.findByNumber(accountNumber);
		AccountStatus accountStatus = account.getAccountStatus();
		Agent agent = account.getAgent();
		List<Role> roles = new ArrayList<Role>();
		for (Role role : account.getRoles()) {
			roles.add(role);
		}
		mav.addObject("updateAccount", account);
		mav.addObject("updateAccountStatus", accountStatus);
		mav.addObject("updateRole", roles);
		mav.addObject("updateAgent", agent);
		List<AccountStatus> accountStatusList = accountStatusServiceImpl.listAll();
		mav.addObject("accountStatusList", accountStatusList);
		List<Role> roleList = roleServiceImpl.listAll();
		mav.addObject("roleList", roleList);
		List<Agent> listAgent = agentServiceImpl.listAll();
		mav.addObject("listAgent", listAgent);
		mav.addObject("accountForm", new BaseModel());
		return mav;
	}

	@RequestMapping("/modifyClientAccount")
	public ModelAndView modifyClientAccount(@RequestParam("accountNumber") long accountNumber) {
		ModelAndView mav = new ModelAndView("/account/modifyClientAccount");
		Account account = accountServiceImpl.findByNumber(accountNumber);
		AccountStatus accountStatus = account.getAccountStatus();
		Client client = account.getClient();
		List<Role> roles = new ArrayList<Role>();
		for (Role role : account.getRoles()) {
			roles.add(role);
		}
		mav.addObject("updateAccount", account);
		mav.addObject("updateAccountStatus", accountStatus);
		mav.addObject("updateRole", roles);
		mav.addObject("updateClient", client);
		List<AccountStatus> accountStatusList = accountStatusServiceImpl.listAll();
		mav.addObject("accountStatusList", accountStatusList);
		List<Role> roleList = roleServiceImpl.listAll();
		mav.addObject("roleList", roleList);
		mav.addObject("accountForm", new BaseModel());
		return mav;
	}

	@RequestMapping(value = "/saveModifyClientAccount", method = RequestMethod.POST)
	public String saveModifyClientAccount(@ModelAttribute("updateAccount") Account updateAccount,
			@ModelAttribute("accountForm") BaseModel model, @RequestParam("accountNumber") long accountNumber) {
		Account accountID = accountServiceImpl.findByNumber(accountNumber);
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(model.getAccountStatusShort());
		Role role = roleServiceImpl.findByNumber(model.getRoleNumber());
		List<Role> listRole = new ArrayList<Role>();
		listRole.add(role);
		Client client = clientServiceImpl.findByNumber(model.getClientNumber());
		Account account = new Account(updateAccount, accountStatus, new HashSet<>(listRole), client);
		account.setAccountNumber(accountID.getAccountNumber());
		accountServiceImpl.save(account);
		return "redirect:/viewAccounts";
	}

	@RequestMapping(value = "/saveModifyAgentAccount", method = RequestMethod.POST)
	public String saveModifyAgentAccount(@ModelAttribute("updateAccount") Account updateAccount,
			@ModelAttribute("accountForm") BaseModel model, @RequestParam("accountNumber") long accountNumber) {
		Account accountID = accountServiceImpl.findByNumber(accountNumber);
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(model.getAccountStatusShort());
		Role role = roleServiceImpl.findByNumber(model.getRoleNumber());
		List<Role> listRole = new ArrayList<Role>();
		listRole.add(role);
		Agent agent = agentServiceImpl.findByNumber(model.getAgentNumber());
		Account account = new Account(updateAccount, accountStatus, new HashSet<>(listRole), agent);
		account.setAccountNumber(accountID.getAccountNumber());
		accountServiceImpl.save(account);
		return "redirect:/viewAccounts";
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
