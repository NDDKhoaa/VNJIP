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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vnjip.entity.Account;
import vnjip.entity.Agent;
import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.AccountType;
import vnjip.model.BaseModel;
import vnjip.services.Impl.AccountStatusServiceImpl;
import vnjip.services.Impl.AccountTypeServiceImpl;
import vnjip.services.Impl.AgentServiceImpl;

@Controller
public class AgentController {

	@Autowired
	private AgentServiceImpl agentServiceImpl;

	@Autowired
	private AccountTypeServiceImpl accountTypeServiceImpl;

	@Autowired
	private AccountStatusServiceImpl accountStatusServiceImpl;

	@RequestMapping("/viewAgents")
	public String viewAgent(Model model) {
		List<Agent> listAgent = agentServiceImpl.listAll();
		List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
		for (Agent agent : listAgent) {
			if (agent != null) {
				AccountType accountType = agent.getAccountType();
				AccountStatus accountStatus = agent.getAccountStatus();
				BaseModel baseModel = new BaseModel(agent, accountType, accountStatus);
				listBaseModel.add(baseModel);
			}
		}
		model.addAttribute("listBaseModel", listBaseModel);
		return "/agent/viewAgents";
	}

	@RequestMapping(value = "/viewAgentDetails", method = RequestMethod.GET)
	public ModelAndView viewAgentDetails(@RequestParam(value = "agentNumber") long agentNumber) {
		ModelAndView mav = new ModelAndView("/agent/viewAgentDetails");
		Agent agent = agentServiceImpl.findByNumber(agentNumber);
		AccountType accountType = agent.getAccountType();
		AccountStatus accountStatus = agent.getAccountStatus();
		Account account = agent.getAccount();
		if (account == null) {
			BaseModel baseModel = new BaseModel(agent, accountType, accountStatus);
			mav.addObject("baseModel", baseModel);
		} else {
			BaseModel baseModel = new BaseModel(agent, accountType, accountStatus, account);
			mav.addObject("baseModel", baseModel);
		}
		return mav;
	}

	@RequestMapping("/createAgent")
	public String createAgent(Model model) {
		List<AccountType> listAccountType = accountTypeServiceImpl.listAll();
		List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
		model.addAttribute("listAccountType", listAccountType);
		model.addAttribute("listAccountStatus", listAccountStatus);
		model.addAttribute("agentForm", new BaseModel());
		return "/agent/createAgent";
	}

	@RequestMapping(value = "/saveAgent", method = RequestMethod.POST)
	public String saveAgent(@ModelAttribute("agentForm") BaseModel model) {
		AccountType accountType = accountTypeServiceImpl.findByShort(model.getAccountTypeShort());
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(model.getAccountStatusShort());
		Agent agent = new Agent(model, accountType, accountStatus);
		agentServiceImpl.save(agent);
		return "redirect:/viewAgents";
	}

	@RequestMapping("/modifyAgent")
	public ModelAndView modifyAgent(@RequestParam("agentNumber") long agentNumber) {
		ModelAndView mav = new ModelAndView("/agent/modifyAgent");
		Agent agent = agentServiceImpl.findByNumber(agentNumber);
		AccountType accountType = agent.getAccountType();
		AccountStatus accountStatus = agent.getAccountStatus();
		mav.addObject("updateAccountStatus", accountStatus);
		mav.addObject("updateAccountType", accountType);
		mav.addObject("updateAgent", agent);
		List<AccountType> listAccountType = accountTypeServiceImpl.listAll();
		List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
		mav.addObject("listAccountType", listAccountType);
		mav.addObject("listAccountStatus", listAccountStatus);
		mav.addObject("agentForm", new BaseModel());
		return mav;
	}

	@RequestMapping(value = "/saveModify", method = RequestMethod.POST)
	public String saveModify(@ModelAttribute("updateAgent") Agent updateAgent,
			@ModelAttribute("updateAccountStatus") AccountStatus updateAccountStatus,
			@ModelAttribute("updateAccountType") AccountType updateAccountType,
			@ModelAttribute("agentForm") BaseModel model, @RequestParam("agentNumber") long agentNumber) {
		Agent agentID = agentServiceImpl.findByNumber(agentNumber);
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(model.getAccountStatusShort());
		AccountType accountType = accountTypeServiceImpl.findByShort(model.getAccountTypeShort());
		Agent agent = new Agent(updateAgent, accountStatus, accountType);
		agent.setAgentNumber(agentID.getAgentNumber());
		agentServiceImpl.save(agent);
		return "redirect:/viewAgents";
	}

	@RequestMapping("/deleteAgent")
	public String deleteAgent(@RequestParam("agentNumber") long agentNumber) {
		agentServiceImpl.deleteByNumber(agentNumber);
		return "redirect:/viewAgents";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
