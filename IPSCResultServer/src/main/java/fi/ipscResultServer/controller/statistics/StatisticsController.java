package fi.ipscResultServer.controller.statistics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	StatisticsService statisticsService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	ScoreCardService scoreCardService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStatisticsPage(Model model, @RequestParam("matchId") String matchId,
			@RequestParam(value="division", required = false) String division) {
		Match match = matchService.getOne(matchService.getIdByPractiScoreId(matchId), false);
		
		if (division == null) {
			division = Constants.COMBINED_DIVISION;
			List<String> divisionsWithResults = scoreCardService.getDivisionsWithResults(match.getId());
			if (divisionsWithResults.size() == 1) {
					division = match.getDivisionsWithResults().get(0);
			}
		}
		List<CompetitorStatistics> statisticstatistics = statisticsService.get(match.getId(), division);
		boolean additionalPenaltiesColumn = false;
		for (CompetitorStatistics stats : statisticstatistics) {
			if (stats.getAdditionalPenalties() > 0) additionalPenaltiesColumn = true;
		}
		model.addAttribute("match", match);
		model.addAttribute("division", division);
		model.addAttribute("statistics", statisticstatistics);
		model.addAttribute("additionalPenaltiesColumn", additionalPenaltiesColumn);

		return "statistics/competitorStatistics/competitorStatisticsPage";
	}
}
