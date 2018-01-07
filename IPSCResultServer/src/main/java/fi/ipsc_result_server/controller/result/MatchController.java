package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.service.CompetitorService;
import fi.ipsc_result_server.service.MatchService;

@Controller
@RequestMapping("/match")
public class MatchController {
	@Autowired
	MatchService matchService;
	
	@Autowired
	CompetitorService competitorService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String TESTgetOverallResultsForReport(Model model, @PathVariable("id") String matchId) {
		System.out.println("Controller navigating to " + matchId);
		model.addAttribute("match", matchService.getOne(matchId));
		model.addAttribute("competitors", competitorService.findAll());
		return "results/matchResultsMainPage";
	}
}
