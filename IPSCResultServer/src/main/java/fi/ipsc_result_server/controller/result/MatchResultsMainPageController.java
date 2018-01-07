package fi.ipsc_result_server.controller.result;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipsc_result_server.service.CompetitorService;
import fi.ipsc_result_server.service.MatchService;

@Controller
@RequestMapping("/results")
public class MatchResultsMainPageController {
	@Autowired
	MatchService matchService;
	
	@Autowired
	CompetitorService competitorService;
	
	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String TESTgetOverallResultsForReport(Model model, @RequestParam("match") String matchId) {
		model.addAttribute("match", matchService.findOne(matchId));
		model.addAttribute("competitors", competitorService.findAll());
		return "results/matchResultsMainPage";
	}
}
