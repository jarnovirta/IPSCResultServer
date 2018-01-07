package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.service.CompetitorService;
import fi.ipsc_result_server.service.StageScoreService;

@Controller
@RequestMapping("/match/{matchId}/competitor")
public class CompetitorResultsController {
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	StageScoreService stageScoreService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getCompetitorResultsPage(Model model, @PathVariable("id") String competitorId) {
		System.out.println("Competitor id: " + competitorId);
		model.addAttribute("competitor", competitorService.getOne(competitorId));
//		model.addAttribute("stageScores", )
		return "results/competitorResults";
	}
}
