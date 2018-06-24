package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;

@Controller
@RequestMapping("/divisionResults")
public class DivisionResultsController {
	@Autowired
	MatchResultDataService matchResultDataService;
	
	@Autowired
	private MatchService matchService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getDivisionOverAllResultsPage(Model model, @RequestParam("matchId") String matchId, 
			@RequestParam("division") String division) {
		try {
			Match match = matchService.findByPractiScoreId(matchId);
			model.addAttribute("matchResultData", matchResultDataService.findByMatchAndDivision(match.getId(), division));
			return "results/divisionResults/divisionResultsPage";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/divisionResults/divisionResultsPage";
		}
	}
}
