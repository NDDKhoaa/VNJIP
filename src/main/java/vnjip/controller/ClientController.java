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

import vnjip.model.BaseModel;

@Controller
public class ClientController {

	@RequestMapping("/viewClients")
	public String viewClient(Model model) {
		return "/client/viewClients";
	}

	@RequestMapping(value = "/viewClientDetails", method = RequestMethod.GET)
	public ModelAndView viewClientDetails(@RequestParam(value = "clientNumber") long clientNumber) {
		ModelAndView mav = new ModelAndView("/client/viewClientDetails");
		return mav;
	}

	@RequestMapping("/createClient")
	public String createClient(Model model) {
		return "/client/createClient";
	}

	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	public String saveClient(@ModelAttribute("clientForm") BaseModel model) {
		return "redirect:/viewClients";
	}

	@RequestMapping("/modifyClient")
	public ModelAndView modifyClient(@RequestParam("clientNumber") long clientNumber) {
		ModelAndView mav = new ModelAndView("/client/modifyClient");
		return mav;
	}

	@RequestMapping("/deleteClient")
	public String deleteClient(@RequestParam("clientNumber") long clientNumber) {
		return "redirect:/viewClients";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
