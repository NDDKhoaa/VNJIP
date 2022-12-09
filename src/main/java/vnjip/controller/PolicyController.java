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

import vnjip.entity.Client;
import vnjip.entity.Policy;
import vnjip.entity.base.BillingCurrency;
import vnjip.entity.base.PolicyStatus;
import vnjip.model.BaseModel;
import vnjip.services.PolicyStatusService;
import vnjip.services.Impl.BillingCurrencyServiceImpl;
import vnjip.services.Impl.ClientServiceImpl;
import vnjip.services.Impl.ErrorPfImpl;
import vnjip.services.Impl.PolicyServiceImpl;
import vnjip.services.Impl.PolicyStatusServiceImpl;

@Controller
public class PolicyController {

	@Autowired
	private PolicyServiceImpl policyServiceImpl;
	@Autowired
	private PolicyStatusServiceImpl policyStatusServiceImpl;
	@Autowired
	private BillingCurrencyServiceImpl billingCurrencyServiceImpl;
	@Autowired
	private ClientServiceImpl clientServiceImpl;
	@Autowired
	private PolicyStatusService policyStatusService;
	@Autowired
	private ErrorPfImpl errorPfImpl;

	@RequestMapping("/viewPolicies")
	public String viewPolicy(Model model) {
		List<Policy> listPolicy = policyServiceImpl.listAll();
		List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
		for (Policy policy : listPolicy) {
			if (policy != null) {
				policy.setPolicyStatus(
						policyStatusServiceImpl.findByShort(policy.getPolicyStatus().getPolicyStatusShort()));
				BaseModel baseModel = new BaseModel(policy);
				listBaseModel.add(baseModel);
			}
		}
		model.addAttribute("listBaseModel", listBaseModel);
		return "/policy/viewPolicies";
	}

	@RequestMapping("/modifyPolicy")
	public String modifyPolicy(@RequestParam("policyNumber") long policyNumber, Model mav) {
		Policy policy = policyServiceImpl.findByNumber(policyNumber);
		BillingCurrency billingCurrency = policy.getBillingCurrency();
		mav.addAttribute("updateBillingCurrency", billingCurrency);
		mav.addAttribute("updatePolicy", policy);
		List<BillingCurrency> listBillingCurrency = billingCurrencyServiceImpl.listAll();
		mav.addAttribute("listBillingCurrency", listBillingCurrency);
		mav.addAttribute("policyForm", new BaseModel());
		return "/policy/modifyPolicy";
	}

	@RequestMapping(value = "/viewPolicyDetails", method = RequestMethod.GET)
	public ModelAndView viewAgentDetails(@RequestParam(value = "policyNumber") long policyNumber) {
		ModelAndView mav = new ModelAndView("/policy/viewPolicyDetails");
		Policy policy = policyServiceImpl.findByNumber(policyNumber);
		PolicyStatus policyStatus = policyStatusServiceImpl
				.findByShort(policy.getPolicyStatus().getPolicyStatusShort());
		BillingCurrency billingCurrency = billingCurrencyServiceImpl
				.findByShort(policy.getBillingCurrency().getCurrencyShort());
		BaseModel baseModel = new BaseModel(policy, policyStatus, billingCurrency);
		mav.addObject("baseModel", baseModel);
		return mav;
	}

	@RequestMapping("/createPolicy")
	public String createAgent(Model model) {
		List<BillingCurrency> listBillingCurrency = billingCurrencyServiceImpl.listAll();
		try {
			Policy policyNum = policyServiceImpl.findTopByOrderByIdDesc();
			if (policyNum != null) {
				model.addAttribute("listBillingCurrency", listBillingCurrency);
				model.addAttribute("policyNum", policyNum.getPolicyNumber() + 1);
			} else {
				model.addAttribute("policyNum", 1);
			}
			model.addAttribute("policyForm", new BaseModel());
			model.addAttribute("listBillingCurrency", listBillingCurrency);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		return "/policy/createPolicy";
	}

	@RequestMapping(value = "/savePolicy", method = RequestMethod.POST)
	public ModelAndView saveAgent(@ModelAttribute("policyForm") BaseModel model) {
		ModelAndView mav = new ModelAndView("/policy/createPolicy");
		List<String> errorList = new ArrayList<>();
		Client client = clientServiceImpl.findByNumber(model.getPolicyOwner());
		validationCreatePolicy(model, errorList);
		if (errorList.size() == 0) {
			validationCreatePolicyType(model, errorList);
			if (client == null) {
				model.setErrorCode("E182");
				errorList.add("Client " + errorPfImpl.findByShort(model.getErrorCode()).getErrorDesc());
			}
		}
		if (errorList.size() == 0) {
			validate2010(model, errorList);
		}
		Policy policy = new Policy(model, client);
		if (errorList.size() > 0) {
			System.out.println("-----------------" + errorList);
			model.setErrorList(errorList);
			System.out.println("-----------------" + model.getErrorList());
			List<BillingCurrency> listBillingCurrency = billingCurrencyServiceImpl.listAll();
			mav.addObject("listBillingCurrency", listBillingCurrency);
			mav.addObject("modelList", errorList);
			System.out.println(errorList.size());
			System.out.println("-------" + policyStatusService.findByShort("PN"));
			return mav;
		} else {
			policy.setBillingCurrency(billingCurrencyServiceImpl.findByShort(model.getCurrencyShort()));
			policy.setPolicyStatus(policyStatusService.findByShort("PN"));
			ModelAndView mavSuccess = new ModelAndView("redirect:/viewPolicies");
			policyServiceImpl.save(policy);
			List<Policy> listPolicy = policyServiceImpl.listAll();
			List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
			for (Policy policy1 : listPolicy) {
				if (policy1 != null) {
					BaseModel baseModel = new BaseModel(policy1);
					listBaseModel.add(baseModel);
				}
			}
			mavSuccess.addObject("listBaseModel", listBaseModel);
			return mavSuccess;
		}
	}

	@RequestMapping(value = "/savePolicyModify", method = RequestMethod.POST)
	public ModelAndView savePolicyModify(@ModelAttribute("updatePolicy") Policy updatePolicy,
			@ModelAttribute("policyForm") BaseModel md, @RequestParam("policyNumber") long policyNumber) {
		BillingCurrency currency = billingCurrencyServiceImpl.findByShort(md.getCurrencyShort());
		System.out.println("-------------------------" + currency);
		updatePolicy.setPolicyStatus(policyStatusService.findByShort("PN"));
		updatePolicy.setBillingCurrency(currency);
		BaseModel model = new BaseModel(updatePolicy, billingCurrencyServiceImpl.findByShort(md.getCurrencyShort()));
		ModelAndView mav = new ModelAndView("/policy/modifyPolicy");

		List<String> errorList = new ArrayList<>();
		Client client = clientServiceImpl.findByNumber(model.getPolicyOwner());
		validationCreatePolicy(model, errorList);
		if (errorList.size() == 0) {
			validationCreatePolicyType(model, errorList);
			if (client == null) {
				model.setErrorCode("E182");
				errorList.add("Client " + errorPfImpl.findByShort(model.getErrorCode()).getErrorDesc());
			}
		}
		if (errorList.size() == 0) {
			validate2011(model, errorList);
		}
		Policy policy = new Policy(model, client);
		if (errorList.size() > 0) {
			System.out.println("-----------------" + errorList);
			model.setErrorList(errorList);
			System.out.println("-----------------" + model.getErrorList());
			List<BillingCurrency> listBillingCurrency = billingCurrencyServiceImpl.listAll();
			mav.addObject("listBillingCurrency", listBillingCurrency);
			mav.addObject("modelList", errorList);
			System.out.println(errorList.size());
			System.out.println("-------" + policyStatusService.findByShort("PN"));
			return mav;
		} else {
			policy.setBillingCurrency(billingCurrencyServiceImpl.findByShort(model.getCurrencyShort()));
			policy.setPolicyStatus(policyStatusService.findByShort("PN"));
			mav.addObject("listBillingCurrency", policy.getBillingCurrency());
			ModelAndView mavSuccess = new ModelAndView("redirect:/viewPolicies");
			Policy policyID = policyServiceImpl.findByNumber(policyNumber);
			policy.setPolicyNumber(policyID.getPolicyNumber());
			policyServiceImpl.save(policy);

			List<Policy> listPolicy = policyServiceImpl.listAll();
			List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
			for (Policy policy1 : listPolicy) {
				if (policy1 != null) {
					BaseModel baseModel = new BaseModel(policy1);
					listBaseModel.add(baseModel);
				}
			}
			mavSuccess.addObject("listBaseModel", listBaseModel);
			return mavSuccess;
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	public void validationCreatePolicy(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getInceptionDate() == null) {
			baseModel.setErrorCode("E186");
			errorList.add("Inception Date " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getExpiryDate() == null) {
			baseModel.setErrorCode("E186");
			errorList.add("Expiry Date " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getPolicyOwner() == 0) {
			baseModel.setErrorCode("E186");
			errorList.add("Policy Owner " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getCurrencyShort().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Billing Currency " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getEngineNo().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Engine No " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getChassisNo().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Chassis No " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getVehicleRegistrationNo().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList
					.add("Vehicle Registration No " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getSumInsured().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Sum Insured " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getRate() == 0) {
			baseModel.setErrorCode("E186");
			errorList.add("Rate " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getAnnualPremium() == 0) {
			baseModel.setErrorCode("E186");
			errorList.add("Annual Premium " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validationCreatePolicyType(BaseModel baseModel, List<String> errorList) {
		if (isNumeric(Long.toString(baseModel.getPolicyOwner()))) {
			baseModel.setErrorCode("E189");
			errorList.add("Policy Owner " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(baseModel.getEngineNo().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("Engine No " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(baseModel.getChassisNo().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("Chassis No " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(baseModel.getVehicleRegistrationNo().trim())) {
			baseModel.setErrorCode("E189");
			errorList
					.add("Vehicle Registration No " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(baseModel.getSumInsured())) {
			baseModel.setErrorCode("E189");
			errorList.add("Sum Insured " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(String.valueOf(baseModel.getRate()))) {
			baseModel.setErrorCode("E189");
			errorList.add("Rate " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(String.valueOf((baseModel.getAnnualPremium())))) {
			baseModel.setErrorCode("E189");
			errorList.add("Annual Premium " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validate2010(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getInceptionDate().after(baseModel.getExpiryDate())
				|| baseModel.getInceptionDate().equals((baseModel.getExpiryDate()))) {
			baseModel.setErrorCode("E181");
			errorList.add(errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (!policyServiceImpl.findByEngineNo(baseModel.getEngineNo())
				|| !policyServiceImpl.findByChassisNo(baseModel.getChassisNo())) {
			baseModel.setErrorCode("E183");
			errorList.add(errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (Double.parseDouble(baseModel.getSumInsured()) < 0) {
			baseModel.setErrorCode("E184");
			errorList.add("Sum Insured " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getRate() < 0) {
			baseModel.setErrorCode("E184");
			errorList.add("Rate " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public void validate2011(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getInceptionDate().after(baseModel.getExpiryDate())
				|| baseModel.getInceptionDate().equals((baseModel.getExpiryDate()))) {
			baseModel.setErrorCode("E181");
			errorList.add(errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (!policyServiceImpl.findByEngineNoModify(baseModel.getEngineNo())
				|| !policyServiceImpl.findByChassisNoModify(baseModel.getChassisNo())) {
			baseModel.setErrorCode("E183");
			errorList.add(errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (Double.parseDouble(baseModel.getSumInsured()) < 0) {
			baseModel.setErrorCode("E184");
			errorList.add("Sum Insured " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getRate() < 0) {
			baseModel.setErrorCode("E184");
			errorList.add("Rate " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	@RequestMapping(value = "/multi-delete", method = RequestMethod.POST)
	public String deletePolicy(@RequestParam long[] ids, Model model) {
		for (long l : ids) {
			if (ids.length > 0) {
				policyServiceImpl.deleteById(l);
			}
		}
		return "redirect:/viewPolicies";
	}

	@RequestMapping("/deletePolicy")
	public String deletePolicy(@RequestParam("policyNumber") long policyNumber) {
		policyServiceImpl.deleteByNumber(policyNumber);
		return "redirect:/viewPolicies";
	}

	@RequestMapping("/issuePolici")
	public String issuePolici(@RequestParam("policyNumber") long policyNumber) {
		Policy policy = policyServiceImpl.findByNumber(policyNumber);
		PolicyStatus policyStatus = policyStatusServiceImpl.findByShort("IF");
		policy.setPolicyStatus(policyStatus);
		policyServiceImpl.save(policy);
		return "redirect:/viewPolicies";
	}

	@RequestMapping("/issuePolicy")
	public String issuePolicy(@RequestParam long[] ids) {
		for (long l : ids) {
			if (ids.length > 0) {
				Policy policy = policyServiceImpl.findByNumber(l);
				PolicyStatus policyStatus = policyStatusServiceImpl.findByShort("IF");
				policy.setPolicyStatus(policyStatus);
				policyServiceImpl.save(policy);
			}
		}
		return "redirect:/viewPolicies";
	}

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return true;
		}
		try {
			double d = Double.parseDouble(strNum);
			return false;
		} catch (NumberFormatException nfe) {
			return true;
		}
	}
}
