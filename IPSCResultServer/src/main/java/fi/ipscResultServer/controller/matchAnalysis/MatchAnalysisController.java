package fi.ipscResultServer.controller.matchAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.resultDataService.CompetitorResultDataService;

@Controller
@RequestMapping("/matchAnalysis")
public class MatchAnalysisController {

	@Autowired
	CompetitorResultDataService competitorResultDataService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMatchAnalysisPage(Model model, @RequestParam("competitorId") String competitorPractiScoreId,
			@RequestParam("matchId") String matchPractiScoreId) {
		try {
			CompetitorResultData resultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			model.addAttribute("competitorResultData", resultData);
		}
//		 Exception logged in repository
		catch (DatabaseException e) {
			
		}
		
		return "matchAnalysis/matchAnalysisPage";
	}
}
