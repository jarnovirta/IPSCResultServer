package fi.ipscResultServer.controller.matchAnalysis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.service.MatchAnalysisService;
import fi.ipscResultServer.service.MatchService;

@Controller
@RequestMapping("/matchAnalysis")
public class MatchAnalysisController {

	@Autowired
	private MatchService matchService;
	
	@Autowired
	private MatchAnalysisService matchAnalysisService;
	
	private final static Logger LOGGER = Logger.getLogger(MatchAnalysisController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMatchAnalysisPage(Model model, @RequestParam("competitorId") String competitorPractiScoreId,
			@RequestParam("matchId") String matchPractiScoreId) {
		
		Match match = matchService.getOne(matchService.getIdByPractiScoreId(matchPractiScoreId), true);
		
		model.addAttribute("matchId", matchPractiScoreId);
		model.addAttribute("competitorId", competitorPractiScoreId);
		model.addAttribute("compareToCompetitorId", match.getCompetitors().get(0).getPractiScoreId());
		
		return "matchAnalysis/matchAnalysisPage";
	}
	@RequestMapping(value="/data", method = RequestMethod.GET)
	public @ResponseBody MatchAnalysis getMatchAnalysisData(@RequestParam("matchId") String matchPractiScoreId, 
			@RequestParam("competitorId") String competitorPractiScoreId,
			@RequestParam("compareToCompetitorId") String compareToCompetitorPractiScoreId) {
			long startTime = System.currentTimeMillis();
			MatchAnalysis analysis = matchAnalysisService.getMatchAnalysisData(matchPractiScoreId, competitorPractiScoreId, compareToCompetitorPractiScoreId);
			long estimatedTime = System.currentTimeMillis() - startTime;
			LOGGER.info("Match analysis data fetch in " + estimatedTime + " ms.");
			return analysis;
	}

}
