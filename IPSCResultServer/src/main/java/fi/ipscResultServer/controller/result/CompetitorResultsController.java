package fi.ipscResultServer.controller.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.resultDataService.CompetitorErrorCostDataService;
import fi.ipscResultServer.service.resultDataService.CompetitorResultDataService;

@Controller
@RequestMapping("/match/{matchId}/competitor")
public class CompetitorResultsController {
	@Autowired
	CompetitorResultDataService competitorResultDataService;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getCompetitorResultsPage(Model model, @PathVariable("id") Long competitorId, 
			@PathVariable("matchId") Long matchId) {
		
		try { 
			Match match = matchService.getOne(matchId);
			Competitor competitor = competitorService.getOne(competitorId);
			CompetitorResultData resultData = competitorResultDataService.findByCompetitorAndMatch(competitor, match);
			boolean additionalPenaltiesColumn = false;
			for (ScoreCard card : resultData.getScoreCards().values()) {
				if (card.getAdditionalPenalties() > 0) additionalPenaltiesColumn = true;
			}
			
			List<ScoreCard> cards = new ArrayList<ScoreCard>();
			for (Stage stage : match.getStages()) {
				ScoreCard card = resultData.getScoreCards().get(stage.getId());
				if (card != null) {
					cards.add(card);
				}
			}
			
			model.addAttribute("resultData", resultData);
			model.addAttribute("additionalPenaltiesColumn", additionalPenaltiesColumn);
			model.addAttribute("errorCostDataLines", CompetitorErrorCostDataService.getErrorCostTableLines(match, competitor, cards));
			return "results/competitorResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/competitorResults";
		}
	}
}
