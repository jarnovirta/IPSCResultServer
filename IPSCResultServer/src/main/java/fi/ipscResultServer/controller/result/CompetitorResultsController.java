package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.resultDataService.CompetitorResultDataService;

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
			
			Match match = matchService.getOne(matchService.getIdByPractiScoreId(matchId));
//			Competitor competitor = competitorService.findByPractiScoreReferences(matchId, competitorId);
//			
//			CompetitorResultData resultData = competitorResultDataService.getCompetitorResultData(competitorId, matchId);
//			boolean additionalPenaltiesColumn = false;
//			for (ScoreCard card : resultData.getScoreCards().values()) {
//				if (card.getAdditionalPenalties() > 0) additionalPenaltiesColumn = true;
//			}
//		
//			List<ScoreCard> cards = new ArrayList<ScoreCard>();
//			for (Stage stage : match.getStages()) {
//				ScoreCard card = resultData.getScoreCards().get(stage.getId());
//				if (card != null) {
//					cards.add(card);
//				}
//			}
			
//			model.addAttribute("resultData", resultData);
//			model.addAttribute("additionalPenaltiesColumn", additionalPenaltiesColumn);
//			model.addAttribute("errorCostDataLines", CompetitorErrorCostDataService.getErrorCostTableLines(match, competitor, cards));
			
		
//		 Exception logged in repository
		
		return "results/competitorResults/competitorResultsPage";
	}
}
