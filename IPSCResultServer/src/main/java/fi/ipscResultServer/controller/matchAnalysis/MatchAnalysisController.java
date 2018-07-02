package fi.ipscResultServer.controller.matchAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
			
			
			// POISTA:
			CompetitorResultData resultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			model.addAttribute("competitorResultData", resultData);
			///
			model.addAttribute("matchId", matchPractiScoreId);
			model.addAttribute("competitorId", competitorPractiScoreId);
		}
//		 Exception logged in repository
		catch (DatabaseException e) {
			
		}
		
		return "matchAnalysis/matchAnalysisPage";
	}
	@RequestMapping(value="/data", method = RequestMethod.GET)
	public @ResponseBody CompetitorResultData getMatchAnalysisData(@RequestParam("matchId") String matchPractiScoreId, 
			@RequestParam("competitorId") String competitorPractiScoreId) {
		
		try {
			CompetitorResultData resultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			competitorResultDataService.setCompetitorStagePercentages(resultData);
			return resultData;
		}
//		 Exception logged in repository
		catch (DatabaseException e) {
			return null;
		}
	}
}
