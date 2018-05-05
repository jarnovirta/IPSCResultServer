package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;

@Controller
@RequestMapping("/match/{matchId}")
public class MatchOverAllResultsController {
	@Autowired
	MatchResultDataService matchResultDataService;
	
	@RequestMapping(value="/division/{division}", method = RequestMethod.GET)
	public String getDivisionOverAllResultsPage(Model model, @PathVariable("matchId") Long matchId, 
			@PathVariable("division") String division) {
		try {
			model.addAttribute("matchResultData", matchResultDataService.findByMatchAndDivision(matchId, division));
			model.addAttribute("selectedDivision", division);
			return "results/matchOverAllResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchOverAllResults";
		}
	}
	
	@RequestMapping(value="/division", method = RequestMethod.GET)
	public String getCombinedOverAllResultsPage(Model model, @PathVariable("matchId") Long matchId) {
		try {
			String division = Constants.COMBINED_DIVISION;
			model.addAttribute("matchResultData", matchResultDataService.findByMatchAndDivision(matchId, division));
			model.addAttribute("selectedDivision", division);
			return "results/matchOverAllResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchOverAllResults";
		}
	}
	
}
