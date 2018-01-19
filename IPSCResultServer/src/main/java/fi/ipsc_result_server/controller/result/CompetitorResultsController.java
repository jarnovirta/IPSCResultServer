package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.service.resultDataService.CompetitorResultDataService;

@Controller
@RequestMapping("/match/{matchId}/competitor")
public class CompetitorResultsController {
	@Autowired
	CompetitorResultDataService competitorResultDataService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getCompetitorResultsPage(Model model, @PathVariable("id") String competitorId, 
			@PathVariable("matchId") String matchId) {
		model.addAttribute("resultData", competitorResultDataService.getCompetitorResultData(competitorId, matchId));
		return "results/competitorResults";
	}
}
