package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;

@Controller
@RequestMapping("/match/{practiScoreMatchId}")
public class MatchOverAllResultsController {
	@Autowired
	MatchResultDataService matchResultDataService;
	
	@Autowired
	MatchService matchService;
	
	@RequestMapping(value="/division/{division}", method = RequestMethod.GET)
	public String getDivisionOverAllResultsPage(Model model, @PathVariable("practiScoreMatchId") String practiScoreMatchId, 
			@PathVariable("division") String division) {
		try {
			model.addAttribute("matchResultData", matchResultDataService.findByMatchAndDivision(matchService.findByPractiScoreId(practiScoreMatchId).getId(),
					division));
			model.addAttribute("selectedDivision", division);
			return "results/matchOverAllResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchOverAllResults";
		}
	}
	
	@RequestMapping(value="/division", method = RequestMethod.GET)
	public String getCombinedOverAllResultsPage(Model model, @PathVariable("practiScoreMatchId") String practiScoreMatchId) {
		try {
			String division = Constants.COMBINED_DIVISION;
			model.addAttribute("matchResultData", matchResultDataService.findByMatchAndDivision(matchService.findByPractiScoreId(practiScoreMatchId).getId(), 
					division));
			model.addAttribute("selectedDivision", division);
			return "results/matchOverAllResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchOverAllResults";
		}
	}
	
}
