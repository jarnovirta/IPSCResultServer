package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
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
			CompetitorResultData resultData = competitorResultDataService.findByCompetitorAndMatch(
				competitorService.getOne(competitorId), matchService.getOne(matchId));
			boolean additionalPenaltiesColumn = false;
			for (ScoreCard card : resultData.getScoreCards().values()) {
				if (card.getAdditionalPenalties() > 0) additionalPenaltiesColumn = true;
			}
			
			model.addAttribute("resultData", resultData);
			model.addAttribute("additionalPenaltiesColumn", additionalPenaltiesColumn);
			return "results/competitorResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/competitorResults";
		}
	}
}
