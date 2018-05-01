package fi.ipscResultServer.controller.statistics;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
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
	
	final static Logger logger = Logger.getLogger(StatisticsController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStatisticsPage(Model model, @PathVariable("matchId") Long matchId) {
		try {
			Match match = matchService.getOne(matchId);
			String division = null;
			if (match.getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
				division = Constants.COMBINED_DIVISION;
			}
			else {
				if (match.getDivisionsWithResults().size() > 0) {
					division = match.getDivisionsWithResults().get(0);
				}
			}
			model.addAttribute("match", match);
			model.addAttribute("division", division);
			model.addAttribute("statistics", getCompetitorStatistics(match.getId(), division));
		}
		catch (DatabaseException e) {
			logger.error(e.getMessage());
		}
		return "statistics/competitorStatistics";
	}
	
	@RequestMapping(value="/division/{division}", method = RequestMethod.GET)
	public String getStatisticsPageForDivision(Model model, @PathVariable("matchId") Long matchId, @PathVariable("division") String division) {
		try {
			model.addAttribute("match", matchService.getOne(matchId));
			model.addAttribute("statistics", getCompetitorStatistics(matchId, division));
		}
		
		catch (DatabaseException e) {
			logger.error(e.getMessage());
		}
		
		return "statistics/competitorStatistics";
	}
	
	private List<CompetitorStatistics> getCompetitorStatistics(Long matchId, String division) {
		try {
			if (division == null || division.equals(Constants.COMBINED_DIVISION)) {
				return statisticsService.findCompetitorStatisticsByMatch(matchId);
			}
			else return statisticsService.findCompetitorStatisticsByMatchAndDivision(matchId, division);
		}
		catch (DatabaseException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
