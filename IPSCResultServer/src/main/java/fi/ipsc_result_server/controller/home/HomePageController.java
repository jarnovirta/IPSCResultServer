package fi.ipsc_result_server.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.service.MatchService;

@Controller
public class HomePageController {
	@Autowired
	MatchService matchService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		model.addAttribute("matches", matchService.findAll());
		return "home";
	}
	
}
