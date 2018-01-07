package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipsc_result_server.service.CompetitorService;
import fi.ipsc_result_server.service.StageScoreService;

@Controller
@RequestMapping("/results/competitorResults")
public class CompetitorResultsController {
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	StageScoreService stageScoreService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getCompetitorResultsPage(Model model, @RequestParam("resultsForCompetitor") String competitorId) {
		model.addAttribute("competitor", competitorService.findOne(competitorId));
//		model.addAttribute("stageScores", )
		return "results/competitorResults";
	}
}
