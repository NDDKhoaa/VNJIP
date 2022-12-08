package vnjip.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import vnjip.entity.Client;
import vnjip.entity.base.Country;
import vnjip.entity.base.Gender;
import vnjip.entity.base.MaritalStatus;
import vnjip.model.BaseModel;
import vnjip.services.Impl.ClientServiceImpl;
import vnjip.services.Impl.CountryServiceImpl;
import vnjip.services.Impl.ErrorPfImpl;
import vnjip.services.Impl.GenderServiceImpl;
import vnjip.services.Impl.MaritalStatusServiceImpl;

@Controller
public class ClientController {

	@Autowired
	private ClientServiceImpl clientServiceImpl;

	@Autowired
	private GenderServiceImpl genderServiceImpl;

	@Autowired
	private CountryServiceImpl countryServiceImpl;

	@Autowired
	private MaritalStatusServiceImpl maritalStatusServiceImpl;
	@Autowired
	private ErrorPfImpl errorPfImpl;

	@RequestMapping("/viewClients")
	public String viewClient(Model model) {
		List<Client> listClient = clientServiceImpl.listAll();
		List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
		for (Client client : listClient) {
			if (client != null) {
				Gender gender = client.getGender();
				Country country = client.getCountry();
				MaritalStatus maritalStatus = client.getMaritalStatus();
				BaseModel baseModel = new BaseModel(client, country, gender, maritalStatus);
				listBaseModel.add(baseModel);
			}
		}
		model.addAttribute("listClient", listBaseModel);
		return "/client/viewClients";
	}

	@RequestMapping(value = "/viewClientDetails", method = RequestMethod.GET)
	public ModelAndView viewClientDetails(@RequestParam(value = "clientNumber") long clientNumber) {
		ModelAndView mav = new ModelAndView("/client/viewClientDetails");
		Client client = clientServiceImpl.findByNumber(clientNumber);
		Gender gender = client.getGender();
		Country country = client.getCountry();
		MaritalStatus maritalStatus = client.getMaritalStatus();
		BaseModel baseModel = new BaseModel(client, country, gender, maritalStatus);
		mav.addObject("baseModel", baseModel);
		return mav;
	}

	@RequestMapping("/createClient")
	public String createClient(Model model) {
		BaseModel baseModel = new BaseModel();
		Client client = clientServiceImpl.findTopClientNumber();
		if (client != null) {
			baseModel.setClientNumber(client.getClientNumber() + 1);
		} else {
			long id = 1;
			baseModel.setClientNumber(id);
		}
		model.addAttribute("clientForm", baseModel);
		List<Gender> listGender = genderServiceImpl.listAll();
		model.addAttribute("listGender", listGender);
		List<Country> listCountry = countryServiceImpl.listAll();
		model.addAttribute("listCountry", listCountry);
		List<MaritalStatus> listMaritalStatus = maritalStatusServiceImpl.listAll();
		model.addAttribute("listMaritalStatus", listMaritalStatus);
		return "/client/createClient";
	}

	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	public ModelAndView saveClient(@ModelAttribute("clientForm") BaseModel model) {
		List<String> errorList = new ArrayList<>();
		validationNotNull(model, errorList);
		if (errorList.size() == 0) {
			validationType(model, errorList);
		}
		if (errorList.size() == 0) {
			validate2010(model, errorList);
		}
		if (errorList.size() > 0) {
			ModelAndView mav = new ModelAndView("/client/createClient");
			System.out.println("-----------------" + errorList);
			model.setErrorList(errorList);
			System.out.println("-----------------" + model.getErrorList());
			List<Gender> listGender = genderServiceImpl.listAll();
			mav.addObject("listGender", listGender);
			List<Country> listCountry = countryServiceImpl.listAll();
			mav.addObject("listCountry", listCountry);
			List<MaritalStatus> listMaritalStatus = maritalStatusServiceImpl.listAll();
			mav.addObject("listMaritalStatus", listMaritalStatus);
			mav.addObject("modelList", errorList);
			System.out.println(errorList.size());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/viewClients");
			Gender gender = genderServiceImpl.findByShort(model.getGenderShort());
			Country country = countryServiceImpl.findByShort(model.getCountryShort());
			MaritalStatus maritalStatus = maritalStatusServiceImpl.findByShort(model.getMaritalShort());
			Client client = new Client(model, gender, country, maritalStatus);
			clientServiceImpl.save(client);
			List<Client> listClient = clientServiceImpl.listAll();
			List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
			for (Client client1 : listClient) {
				if (client1 != null) {
					Gender gender1 = client.getGender();
					Country country1 = client.getCountry();
					MaritalStatus maritalStatus1 = client.getMaritalStatus();
					BaseModel baseModel = new BaseModel(client1, country1, gender1, maritalStatus1);
					listBaseModel.add(baseModel);
				}
			}
			mav.addObject("listClient", listBaseModel);
			return mav;
		}
	}

	@RequestMapping("/modifyClient")
	public ModelAndView modifyClient(@RequestParam("clientNumber") long clientNumber) {
		ModelAndView mav = new ModelAndView("/client/modifyClient");
		Client client = clientServiceImpl.findByNumber(clientNumber);
		Gender gender = client.getGender();
		Country country = client.getCountry();
		MaritalStatus maritalStatus = client.getMaritalStatus();
		mav.addObject("updateMaritalStatus", maritalStatus);
		mav.addObject("updateGender", gender);
		mav.addObject("updateCountry", country);
		mav.addObject("updateClient", client);
		List<Gender> listGender = genderServiceImpl.listAll();
		mav.addObject("listGender", listGender);
		List<Country> listCountry = countryServiceImpl.listAll();
		mav.addObject("listCountry", listCountry);
		List<MaritalStatus> listMaritalStatus = maritalStatusServiceImpl.listAll();
		mav.addObject("listMaritalStatus", listMaritalStatus);
		mav.addObject("clientForm", new BaseModel());
		return mav;
	}

	@RequestMapping(value = "/saveClientModify", method = RequestMethod.POST)
	public ModelAndView saveClientModify(@ModelAttribute("updateClient") Client updateClient,
			@ModelAttribute("clientForm") BaseModel md, @RequestParam("clientNumber") long clientNumber) {
		Gender gender = genderServiceImpl.findByShort(md.getGenderShort());
		Country country = countryServiceImpl.findByShort(md.getCountryShort());
		MaritalStatus maritalStatus = maritalStatusServiceImpl.findByShort(md.getMaritalShort());
		Client client = new Client(updateClient, gender, country, maritalStatus);
		client.setClientNumber(clientNumber);
		BaseModel model = new BaseModel(client, gender, country, maritalStatus);
		List<String> errorList = new ArrayList<>();
		validationNotNull(model, errorList);
		if (errorList.size() == 0) {
			validationType(model, errorList);
		}
		if (errorList.size() == 0) {
			validate2011(model, errorList);
		}
		if (errorList.size() > 0) {
			ModelAndView mav = new ModelAndView("/client/modifyClient");
			List<Gender> listGender = genderServiceImpl.listAll();
			mav.addObject("listGender", listGender);
			List<Country> listCountry = countryServiceImpl.listAll();
			mav.addObject("listCountry", listCountry);
			List<MaritalStatus> listMaritalStatus = maritalStatusServiceImpl.listAll();
			mav.addObject("listMaritalStatus", listMaritalStatus);
			mav.addObject("updateMaritalStatus", maritalStatus);
			mav.addObject("updateGender", gender);
			mav.addObject("updateCountry", country);
			mav.addObject("updateClient", client);
			mav.addObject("modelList", errorList);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/viewClients");
			Client clientId = clientServiceImpl.findByNumber(clientNumber);
			client.setClientNumber(clientId.getClientNumber());
			clientServiceImpl.save(client);

			List<Client> listClient = clientServiceImpl.listAll();
			List<BaseModel> listBaseModel = new ArrayList<BaseModel>();
			for (Client client1 : listClient) {
				if (client != null) {
					Gender gender1 = client.getGender();
					Country country1 = client.getCountry();
					MaritalStatus maritalStatus1 = client.getMaritalStatus();
					BaseModel baseModel = new BaseModel(client1, country1, gender1, maritalStatus1);
					listBaseModel.add(baseModel);
				}
			}
			mav.addObject("listClient", listBaseModel);
			return mav;
		}
	}

	@RequestMapping("/deleteClient")
	public String deleteClient(@RequestParam("clientNumber") long clientNumber) {
		clientServiceImpl.deleteByNumber(clientNumber);
		return "redirect:/viewClients";
	}

	@RequestMapping(value = "/client-multi-delete", method = RequestMethod.POST)
	public String deleteClients(@RequestParam long[] ids, Model model) {
		for (long l : ids) {
			if (ids.length > 0) {
				clientServiceImpl.deleteByNumber(l);
			}
		}
		return "redirect:/viewPolicies";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	public void validationNotNull(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getClientFirstName().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("First Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getClientLastName().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Last Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getClientDOB() == null) {
			baseModel.setErrorCode("E186");
			errorList.add("Client DOB " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getClientIdentityNumber().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Identity Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getClientAddress().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Client Address " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getCountryShort().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Country Short " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getGenderShort().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Gender " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (baseModel.getMaritalShort().trim().equals("")) {
			baseModel.setErrorCode("E186");
			errorList.add("Marital " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validationType(BaseModel baseModel, List<String> errorList) {
		if (!isNumeric(baseModel.getClientFirstName().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("First Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (!isNumeric(baseModel.getClientLastName().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("Last Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
		if (isNumeric(baseModel.getClientIdentityNumber().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("Identity Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validate2010(BaseModel baseModel, List<String> errorList) {
		if (checkSpecial(baseModel.getClientFirstName().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("Fist Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (clientServiceImpl.findByIdentityNumber(baseModel.getClientIdentityNumber())) {
			baseModel.setErrorCode("E190");
			errorList.add("Identity Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (checkSpecial(baseModel.getClientLastName().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("Last Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (daysBetween2Dates(baseModel.getClientDOB()) <= 0) {
			System.out.println("------------" + daysBetween2Dates(baseModel.getClientDOB()));
			baseModel.setErrorCode("E192");
			errorList.add("Date of Birth " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public void validate2011(BaseModel baseModel, List<String> errorList) {
		if (checkSpecial(baseModel.getClientFirstName().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("Fist Name" + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (clientServiceImpl.findByIdentityNumberModify(baseModel.getClientIdentityNumber())) {
			baseModel.setErrorCode("E190");
			errorList.add("Identity Number " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (checkSpecial(baseModel.getClientLastName().trim())) {
			baseModel.setErrorCode("E191");
			errorList.add("Last Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (daysBetween2Dates(baseModel.getClientDOB()) <= 0) {
			System.out.println("------------" + daysBetween2Dates(baseModel.getClientDOB()));
			baseModel.setErrorCode("E192");
			errorList.add("Date of Birth " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
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

	public boolean checkSpecial(String str) {
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(str);
		boolean isStringContainsSpecialCharacter = matcher.find();
		if (isStringContainsSpecialCharacter)
			return true;
		else
			return false;
	}

	public long daysBetween2Dates(Date date1) {

		Date date2 = new Date(System.currentTimeMillis());
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		long noDay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
		return noDay;

	}

	@RequestMapping(value = "/client-multi-delete", method = RequestMethod.POST)
	public String deleteClients(@RequestParam long[] ids, Model model) {
		for (long l : ids) {
			if (ids.length > 0) {
				clientServiceImpl.deleteByNumber(l);
			}
		}
		return "redirect:/viewPolicies";
	}

}
