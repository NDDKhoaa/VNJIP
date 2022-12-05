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
import vnjip.entity.Client;
import vnjip.entity.base.Country;
import vnjip.entity.base.Gender;
import vnjip.entity.base.MaritalStatus;
import vnjip.model.BaseModel;
import vnjip.services.Impl.ClientServiceImpl;
import vnjip.services.Impl.CountryServiceImpl;
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
		Account account = client.getAccount();
		if (account == null) {
			BaseModel baseModel = new BaseModel(client, country, gender, maritalStatus);
			mav.addObject("baseModel", baseModel);
		} else {
			BaseModel baseModel = new BaseModel(client, country, gender, maritalStatus, account);
			mav.addObject("baseModel", baseModel);
		}
		return mav;
	}

	@RequestMapping("/createClient")
	public String createClient(Model model) {
		model.addAttribute("clientForm", new BaseModel());
		List<Gender> listGender = genderServiceImpl.listAll();
		model.addAttribute("listGender", listGender);
		List<Country> listCountry = countryServiceImpl.listAll();
		model.addAttribute("listCountry", listCountry);
		List<MaritalStatus> listMaritalStatus = maritalStatusServiceImpl.listAll();
		model.addAttribute("listMaritalStatus", listMaritalStatus);
		return "/client/createClient";
	}

	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	public String saveClient(@ModelAttribute("clientForm") BaseModel model) {
		Gender gender = genderServiceImpl.findByShort(model.getGenderShort());
		Country country = countryServiceImpl.findByShort(model.getCountryShort());
		MaritalStatus maritalStatus = maritalStatusServiceImpl.findByShort(model.getMaritalShort());
		Client client = new Client(model, gender, country, maritalStatus);
		clientServiceImpl.save(client);
		return "redirect:/viewClients";
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
	public String saveClientModify(@ModelAttribute("updateClient") Client updateClient,
			@ModelAttribute("clientForm") BaseModel model, @RequestParam("clientNumber") long clientNumber) {
		Client clientId = clientServiceImpl.findByNumber(clientNumber);
		Gender gender = genderServiceImpl.findByShort(model.getGenderShort());
		Country country = countryServiceImpl.findByShort(model.getCountryShort());
		MaritalStatus maritalStatus = maritalStatusServiceImpl.findByShort(model.getMaritalShort());
		Client client = new Client(updateClient, gender, country, maritalStatus);
		client.setClientNumber(clientId.getClientNumber());
		clientServiceImpl.save(client);
		return "redirect:/viewClients";
	}

	@RequestMapping("/deleteClient")
	public String deleteClient(@RequestParam("clientNumber") long clientNumber) {
		clientServiceImpl.deleteByNumber(clientNumber);
		return "redirect:/viewClients";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
