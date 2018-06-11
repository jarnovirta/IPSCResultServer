package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;

@Controller
@RequestMapping("/matchResults")
public class MatchOverAllResultsController {
	@Autowired
	MatchResultDataService matchResultDataService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getDivisionOverAllResultsPage(Model model, @RequestParam("matchId") Long matchId, 
			@RequestParam("division") String division) {
		try {
//			if (division == null) division = Constants.COMBINED_DIVISION;
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
