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
public class AgentController {

	@RequestMapping("/viewAgents")
	public String viewAgent(Model model) {
		return "/agent/viewAgents";
	}

	@RequestMapping(value = "/viewAgentDetails", method = RequestMethod.GET)
	public ModelAndView viewAgentDetails(@RequestParam(value = "agentNumber") long agentNumber) {
		ModelAndView mav = new ModelAndView("/agent/viewAgentDetails");
		return mav;
	}

	@RequestMapping("/createAgent")
	public String createAgent(Model model) {
		return "/agent/createAgent";
	}

	@RequestMapping(value = "/saveAgent", method = RequestMethod.POST)
	public String saveAgent(@ModelAttribute("agentForm") BaseModel model) {
		return "redirect:/viewAgents";
	}

	@RequestMapping("/modifyAgent")
	public ModelAndView modifyAgent(@RequestParam("agentNumber") long agentNumber) {
		ModelAndView mav = new ModelAndView("/agent/modifyAgent");
		return mav;
	}

	@RequestMapping("/deleteAgent")
	public String deleteAgent(@RequestParam("agentNumber") long agentNumber) {
		return "redirect:/viewAgents";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
