package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.service.MatchResultDataService;
import fi.ipscResultServer.service.MatchService;

@Controller
@RequestMapping("/divisionResults")
public class DivisionResultsController {
	@Autowired
	MatchResultDataService matchResultDataService;
	
	@Autowired
	private MatchService matchService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getDivisionOverAllResultsPage(Model model, @RequestParam("matchId") String matchPractiScoreId, 
			@RequestParam("division") String division) {
					
			model.addAttribute("matchResultData", matchResultDataService.findByMatchAndDivision(matchPractiScoreId, division));
			return "results/divisionResults/divisionResultsPage";
		
	}
}
