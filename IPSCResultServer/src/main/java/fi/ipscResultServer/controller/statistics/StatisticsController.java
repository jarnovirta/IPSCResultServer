package fi.ipscResultServer.controller.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.StatisticsService;

@Controller
@RequestMapping("/match/{matchId}/statistics")
public class StatisticsController {

	@Autowired
	StatisticsService statisticsService;
	
	@Autowired
	MatchService matchService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStatisticsPage(Model model, @PathVariable("matchId") String matchId) {
		try {
			Match match = matchService.getOne(matchId);
			if (match.getDivisionsWithResults() == null || match.getDivisionsWithResults().size() == 0) {
				model.addAttribute("statistics", null);
				return "statistics/competitorStatistics";
			}
			String division;
			if (match.getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) division = Constants.COMBINED_DIVISION;
			else division = match.getDivisionsWithResults().get(0);
			
			model.addAttribute("statistics", statisticsService.findCompetitorStatisticsByMatchAndDivision(matchId, division));
			return "statistics/competitorStatistics";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "statistics/competitorStatistics";
		}
	}
	
	@RequestMapping(value="/division/{division}", method = RequestMethod.GET)
	public String getStatisticsPageForDivision(Model model, @PathVariable("matchId") String matchId, @PathVariable("division") String division) {
		try {
			model.addAttribute("statistics", statisticsService.findCompetitorStatisticsByMatchAndDivision(matchId, division));
			return "statistics/competitorStatistics";
		}
		
		// Exception logged in repository
		catch (DatabaseException e) {
			return "statistics/competitorStatistics";
		}
	}
}
