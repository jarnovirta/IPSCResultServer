package fi.ipscResultServer.controller.statistics;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	StatisticsService statisticsService;
	
	@Autowired
	MatchService matchService;
	
	private final static Logger LOGGER = Logger.getLogger(StatisticsController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStatisticsPage(Model model, @RequestParam("matchId") String matchId,
			@RequestParam(value="division", required = false) String division) {
		try {
			Match match = matchService.findByPractiScoreId(matchId);
			if (division == null) {
				if (match.getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
					division = Constants.COMBINED_DIVISION;
				}
				else {
					if (match.getDivisionsWithResults().size() > 0) {
						division = match.getDivisionsWithResults().get(0);
					}
				}
			}
			List<CompetitorStatistics> competitorStatistics = getCompetitorStatistics(match.getId(), division);
			boolean additionalPenaltiesColumn = false;
			for (CompetitorStatistics stats : competitorStatistics) {
				if (stats.getAdditionalPenalties() > 0) additionalPenaltiesColumn = true;
			}
			model.addAttribute("match", match);
			model.addAttribute("division", division);
			model.addAttribute("statistics", competitorStatistics);
			model.addAttribute("additionalPenaltiesColumn", additionalPenaltiesColumn);
		}
		catch (DatabaseException e) {
			LOGGER.error(e.getMessage());
		}
		return "statistics/competitorStatistics/competitorStatisticsPage";
	}
	
	private List<CompetitorStatistics> getCompetitorStatistics(Long matchId, String division) {
		try {
			if (division == null || division.equals(Constants.COMBINED_DIVISION)) {
				return statisticsService.findCompetitorStatisticsByMatch(matchId);
			}
			else return statisticsService.findCompetitorStatisticsByMatchAndDivision(matchId, division);
		}
		catch (DatabaseException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}
}
