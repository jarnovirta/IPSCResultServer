package fi.ipscResultServer.controller.result;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.resultDataService.CompetitorResultDataService;

@Controller
@RequestMapping("/match/{practiScoreMatchId}/competitor")
public class CompetitorResultsController {
	@Autowired
	CompetitorResultDataService competitorResultDataService;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	@RequestMapping(value = "/{practiScoreCompetitorId}", method = RequestMethod.GET)
	public String getCompetitorResultsPage(Model model, @PathVariable("practiScoreCompetitorId") String practiScoreCompetitorId, 
			@PathVariable("practiScoreMatchId") String practiScoreMatchId) {
		try {
			practiScoreCompetitorId = URLDecoder.decode(practiScoreCompetitorId, "UTF-8");
			model.addAttribute("resultData", competitorResultDataService.findByCompetitorAndMatch(
					competitorService.findByPractiScoreReferences(practiScoreMatchId, practiScoreCompetitorId), 
					matchService.findByPractiScoreId(practiScoreMatchId)));
			return "results/competitorResults";
		}
		// Exception logged in repository
		catch (Exception e) {
			return "results/competitorResults";
		}
	}
}
