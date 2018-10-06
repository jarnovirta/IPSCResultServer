package fi.ipscResultServer.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.repository.springJDBCRepository.impl.ScoreCardRepositoryImpl;
import fi.ipscResultServer.service.MatchService;

@Controller
public class HomePageController {
	@Autowired
	MatchService matchService;
	
	@Autowired
	ScoreCardRepositoryImpl sci;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		model.addAttribute("matches", matchService.findAll());
		return "home";
	}
	
}
