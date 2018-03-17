package fi.ipscResultServer.controller.home;

import org.apache.log4j.Logger;
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
	private MatchService matchService;
	
	@Autowired
	private CompetitorService competitorService;
	
	final static Logger logger = Logger.getLogger(MatchController.class);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getMatchMainPage(Model model, @PathVariable("id") String matchId) {
		try {
			model.addAttribute("match", matchService.getOne(matchId));
			model.addAttribute("competitors", competitorService.findByMatch(matchId));
			return "results/matchResultsMainPage";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchResultsMainPage";
		}
	}
}
