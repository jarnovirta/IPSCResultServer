package fi.ipscResultServer.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;

@Controller
@RequestMapping("/match")
public class MatchController {
	@Autowired
	MatchService matchService;
	
	@Autowired
	CompetitorService competitorService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getMatchMainPage(Model model, @PathVariable("id") String matchId) {
		try {
			model.addAttribute("match", matchService.getOne(matchId));
			model.addAttribute("competitors", competitorService.findAll());
			return "results/matchResultsMainPage";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchResultsMainPage";
		}
	}
}
