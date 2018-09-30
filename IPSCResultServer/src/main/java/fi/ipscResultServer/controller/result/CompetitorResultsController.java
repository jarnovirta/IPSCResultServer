package fi.ipscResultServer.controller.result;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.controller.matchAnalysis.CompetitorErrorCostDataGenerator;
import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.service.CompetitorResultDataService;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;

@Controller
@RequestMapping("/competitorResults")
public class CompetitorResultsController {
	@Autowired
	CompetitorResultDataService competitorResultDataService;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getCompetitorResultsPage(Model model, @RequestParam("competitorId") String competitorId, 
			@RequestParam("matchId") String matchId) {
			
		Competitor competitor = competitorService.findByPractiScoreReferences(matchId, competitorId);
		CompetitorResultData resultData = competitorResultDataService.getCompetitorResultData(competitor.getId());
		
		boolean additionalPenaltiesColumn = false;
		for (ScoreCard card : resultData.getScoreCards().values()) {
			if (card.getAdditionalPenalties() > 0) additionalPenaltiesColumn = true;
		}
		
		model.addAttribute("resultData", resultData);
		model.addAttribute("additionalPenaltiesColumn", additionalPenaltiesColumn);
		model.addAttribute("errorCostDataLines", CompetitorErrorCostDataGenerator.getErrorCostTableLines(resultData.getMatch(), competitor, new ArrayList<ScoreCard>(resultData.getScoreCards().values())));
		return "results/competitorResults/competitorResultsPage";
	}
}
