package fi.ipscResultServer.controller.home;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.service.MatchService;

@Controller
public class HomePageController {
	@Autowired
	MatchService matchService;
		
	private final static Logger LOGGER = Logger.getLogger(HomePageController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		model.addAttribute("matches", matchService.getFullMatchList());
		
		return "home";
	}
}
