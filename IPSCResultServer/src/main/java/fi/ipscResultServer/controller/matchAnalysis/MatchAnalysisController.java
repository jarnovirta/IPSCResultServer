package fi.ipscResultServer.controller.matchAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.resultDataService.CompetitorErrorCostDataService;
import fi.ipscResultServer.service.resultDataService.CompetitorResultDataService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Controller
@RequestMapping("/matchAnalysis")
public class MatchAnalysisController {

	@Autowired
	private CompetitorResultDataService competitorResultDataService;
	
	@Autowired
	private CompetitorService competitorService;
	
	@Autowired
	private StageResultDataService stageResultDataService;
	
	@Autowired
	private MatchService matchService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMatchAnalysisPage(Model model, @RequestParam("competitorId") String competitorPractiScoreId,
			@RequestParam("matchId") String matchPractiScoreId) {
		try {
			
			CompetitorResultData resultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			
			Competitor competitor = competitorService.findByPractiScoreReferences(matchPractiScoreId, competitorPractiScoreId);
			Match match = matchService.findByPractiScoreId(matchPractiScoreId);
			CompetitorResultData competitorResultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			List<ScoreCard> cards = new ArrayList<ScoreCard>();
			for (Stage stage : match.getStages()) {
				ScoreCard card = competitorResultData.getScoreCards().get(stage.getId());
				if (card != null) {
					cards.add(card);
				}
			}
			model.addAttribute("competitorResultData", resultData);
			model.addAttribute("matchId", matchPractiScoreId);
			model.addAttribute("competitorId", competitorPractiScoreId);
			model.addAttribute("errorCostDataLines", CompetitorErrorCostDataService.getErrorCostTableLines(match, competitor, cards));
		}
//		 Exception logged in repository
		catch (DatabaseException e) {
			
		}
		
		return "matchAnalysis/matchAnalysisPage";
	}
	@RequestMapping(value="/data", method = RequestMethod.GET)
	public @ResponseBody MatchAnalysisData getMatchAnalysisData(@RequestParam("matchId") String matchPractiScoreId, 
			@RequestParam("competitorId") String competitorPractiScoreId) {
		
		try {
			CompetitorResultData competitorResultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			competitorResultDataService.setCompetitorStagePercentages(competitorResultData);
			
			Competitor competitor = competitorService.findByPractiScoreReferences(matchPractiScoreId, competitorPractiScoreId);
			
			List<StageResultDataLine> stageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitor(competitor);
			
//			Remove circular references before stringifying to JSON
			for (StageResultDataLine line : stageResultDataLines) {
				line.getCompetitor().setMatch(null);
				line.setStageResultData(null);
				line.getScoreCard().getStage().setMatch(null);
			}
			
			return new MatchAnalysisData(competitorResultData, stageResultDataLines);
		}
//		 Exception logged in repository
		catch (DatabaseException e) {
			return null;
		}
	}
}
