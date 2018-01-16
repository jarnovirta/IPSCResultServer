package fi.ipsc_result_server.controller.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.service.StatisticsService;

@Controller
@RequestMapping("/match/{matchId}/statistics")
public class StatisticsController {

	@Autowired
	StatisticsService statisticsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStatisticsPage(Model model, @PathVariable("matchId") String matchId) {
		List<IPSCDivision> availableDivisions = statisticsService.getAvailableDivisionsForStatistics(matchId);
		if (availableDivisions.size() == 0) {
			model.addAttribute("statistics", null);
			return "statistics/competitorStatistics";
		}
		IPSCDivision division;
		if (availableDivisions.contains(IPSCDivision.COMBINED)) division = IPSCDivision.COMBINED;
		else division = availableDivisions.get(0);
		
		model.addAttribute("statistics", statisticsService.getCompetitorStatistics(matchId, division));
		return "statistics/competitorStatistics";
	}
	
	@RequestMapping(value="/division/{division}", method = RequestMethod.GET)
	public String getStatisticsPageForDivision(Model model, @PathVariable("matchId") String matchId, @PathVariable("division") String divisionString) {
		model.addAttribute("statistics", statisticsService.getCompetitorStatistics(matchId, IPSCDivision.valueOf(divisionString)));
		return "statistics/competitorStatistics";
	}
	
}
