package vnjip.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import vnjip.entity.Agent;
import vnjip.entity.base.AccountStatus;
import vnjip.entity.base.AccountType;
import vnjip.model.BaseModel;
import vnjip.services.Impl.AccountStatusServiceImpl;
import vnjip.services.Impl.AccountTypeServiceImpl;
import vnjip.services.Impl.AgentServiceImpl;
import vnjip.services.Impl.ErrorPfImpl;

@Controller
public class AgentController {

	@Autowired
	private AgentServiceImpl agentServiceImpl;

	@Autowired
	private AccountTypeServiceImpl accountTypeServiceImpl;

	@Autowired
	private AccountStatusServiceImpl accountStatusServiceImpl;

	@Autowired
	private ErrorPfImpl errorPfImpl;

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
		BaseModel baseModel = new BaseModel(agent, accountType, accountStatus);
		mav.addObject("baseModel", baseModel);
		return mav;
	}

	@RequestMapping("/createAgent")
	public String createAgent(Model model) {
		List<AccountType> listAccountType = accountTypeServiceImpl.listAll();
		List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
		BaseModel basemodel = new BaseModel();
		Agent agentId = agentServiceImpl.findTopAgentNumber();
		if (agentId != null) {
			basemodel.setAgentNumber(agentId.getAgentNumber() + 1);
		} else {
			long id = 1;
			basemodel.setAgentNumber(id);
		}
		model.addAttribute("listAccountType", listAccountType);
		model.addAttribute("listAccountStatus", listAccountStatus);
		model.addAttribute("agentForm", basemodel);
		return "/agent/createAgent";
	}

	@RequestMapping(value = "/saveAgent", method = RequestMethod.POST)
	public ModelAndView saveAgent(@ModelAttribute("agentForm") BaseModel model) {
		List<String> errorList = new ArrayList<>();
		validationNotNull(model, errorList);
		if (errorList.size() == 0) {
			validationType(model, errorList);
		}
		if (errorList.size() == 0) {
			validate2010(model, errorList);
		}
		if (errorList.size() > 0) {
			ModelAndView mav = new ModelAndView("/agent/createAgent");
			System.out.println("-----------------" + errorList);
			model.setErrorList(errorList);
			System.out.println("-----------------" + model.getErrorList());
			List<AccountType> listAccountType = accountTypeServiceImpl.listAll();
			List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
			mav.addObject("modelList", errorList);
			mav.addObject("listAccountType", listAccountType);
			mav.addObject("listAccountStatus", listAccountStatus);
			System.out.println(errorList.size());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/viewAgents");
			AccountType accountType = accountTypeServiceImpl.findByShort(model.getAccountTypeShort());
			AccountStatus accountStatus = accountStatusServiceImpl.findByShort(model.getAccountStatusShort());
			Agent agent = new Agent(model, accountType, accountStatus);
			agentServiceImpl.save(agent);
			return mav;
		}
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

	@RequestMapping(value = "/saveAgentModify", method = RequestMethod.POST)
	public ModelAndView saveAgentModify(@ModelAttribute("updateAgent") Agent updateAgent,
			@ModelAttribute("updateAccountStatus") AccountStatus updateAccountStatus,
			@ModelAttribute("updateAccountType") AccountType updateAccountType,
			@ModelAttribute("agentForm") BaseModel md, @RequestParam("agentNumber") long agentNumber) {

		Agent agentID = agentServiceImpl.findByNumber(agentNumber);
		AccountStatus accountStatus = accountStatusServiceImpl.findByShort(md.getAccountStatusShort());
		AccountType accountType = accountTypeServiceImpl.findByShort(md.getAccountTypeShort());
		Agent agent = new Agent(updateAgent, accountStatus, accountType);
		agent.setAgentNumber(agentID.getAgentNumber());
		BaseModel model = new BaseModel(updateAgent, accountStatus, accountType);

		List<String> errorList = new ArrayList<>();
		validationNotNull(model, errorList);
		if (errorList.size() == 0) {
			validationType(model, errorList);
		}
		if (errorList.size() == 0) {
			validate2011(model, errorList);
		}
		if (errorList.size() > 0) {
			ModelAndView mav = new ModelAndView("/agent/modifyAgent");
			List<AccountType> listAccountType = accountTypeServiceImpl.listAll();
			List<AccountStatus> listAccountStatus = accountStatusServiceImpl.listAll();
			mav.addObject("updateAgent", agent);
			mav.addObject("listAccountType", listAccountType);
			mav.addObject("listAccountStatus", listAccountStatus);
			mav.addObject("updateAccountStatus", accountStatus);
			mav.addObject("updateAccountType", accountType);
			mav.addObject("modelList", errorList);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/viewAgents");
			Agent agentId = agentServiceImpl.findByNumber(agentNumber);
			agent.setAgentNumber(agentId.getAgentNumber());
			agentServiceImpl.save(agent);

			List<Agent> listAgent = agentServiceImpl.listAll();
			List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
			for (Agent agent1 : listAgent) {
				if (agent1 != null) {
					AccountStatus accountStatus1 = agent1.getAccountStatus();
					AccountType accountType1 = agent1.getAccountType();
					BaseModel baseModel = new BaseModel(agent1, accountStatus1, accountType1);
					listBaseModel.add(baseModel);
				}
			}
			mav.addObject("listClient", listBaseModel);
			return mav;
		}
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

	public void validationNotNull(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getAgentName().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Agent Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getAgentCompanyCode().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Company Code " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getAgentDOB() == null) {
			baseModel.setErrorCode("E186");
			errorList.add("Agent DOB " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getAgentCompanyName().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Company Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getAgentLicenseNumber().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("License Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getAccountStatusShort().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Account Status " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getAccountTypeShort().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Account Type " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validationType(BaseModel baseModel, List<String> errorList) {
		if (isNumeric(baseModel.getAgentName().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("Agent Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (!isNumeric(baseModel.getAgentLicenseNumber().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("License Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validate2010(BaseModel baseModel, List<String> errorList) {
		if (checkSpecial(baseModel.getAgentName().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("Fist Name" + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (checkSpecial(baseModel.getAgentLicenseNumber().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("License Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (agentServiceImpl.checkExistDB((baseModel.getAgentLicenseNumber()))) {
			baseModel.setErrorCode("E190");
			errorList.add("License Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public void validate2011(BaseModel baseModel, List<String> errorList) {
		if (checkSpecial(baseModel.getAgentName().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("Fist Name" + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (checkSpecial(baseModel.getAgentLicenseNumber().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("License Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (agentServiceImpl.checkExistDBModify((baseModel.getAgentLicenseNumber()))) {
			baseModel.setErrorCode("E190");
			errorList.add("License Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public boolean checkSpecial(String str) {
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(str);
		boolean isStringContainsSpecialCharacter = matcher.find();
		if (isStringContainsSpecialCharacter)
			return true;
		else
			return false;
	}

	@RequestMapping(value = "/agent-multi-delete", method = RequestMethod.POST)
	public String deleteAgents(@RequestParam long[] ids) {
		for (long l : ids) {
			agentServiceImpl.deleteByNumber(l);
		}
		return "redirect:/viewAgents";

	}
}
