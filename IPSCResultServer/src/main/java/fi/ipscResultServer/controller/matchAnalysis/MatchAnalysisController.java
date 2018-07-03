package fi.ipscResultServer.controller.matchAnalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			
			Match match = matchService.findByPractiScoreId(matchPractiScoreId);
			
			model.addAttribute("matchId", matchPractiScoreId);
			model.addAttribute("competitorId", competitorPractiScoreId);
			model.addAttribute("compareToCompetitorId", match.getCompetitors().get(0).getPractiScoreId());
			
		}
//		 Exception logged in repository
		catch (DatabaseException e) {
			
		}
		
		return "matchAnalysis/matchAnalysisPage";
	}
	@RequestMapping(value="/data", method = RequestMethod.GET)
	public @ResponseBody MatchAnalysisData getMatchAnalysisData(@RequestParam("matchId") String matchPractiScoreId, 
			@RequestParam("competitorId") String competitorPractiScoreId,
			@RequestParam("compareToCompetitorId") String compareToCompetitorId) {
		
			try {
				Match match = matchService.findByPractiScoreId(matchPractiScoreId);
				if (match != null && match.getCompetitors() != null) Collections.sort(match.getCompetitors());
				CompetitorMatchAnalysisData competitorMatchAnalysisData = getCompetitorMatchAnalysisData(competitorPractiScoreId, matchPractiScoreId);
				for (ScoreCard card : competitorMatchAnalysisData.getCompetitorResultData().getScoreCards().values()) {
					card.setStage(null);
				}
				CompetitorMatchAnalysisData compareToCompetitorMatchAnalysisData = getCompetitorMatchAnalysisData(compareToCompetitorId, matchPractiScoreId);
				for (ScoreCard card : compareToCompetitorMatchAnalysisData.getCompetitorResultData().getScoreCards().values()) {
					card.setStage(null);
				}
				MatchAnalysisData matchData = new MatchAnalysisData(match, competitorMatchAnalysisData, 
						compareToCompetitorMatchAnalysisData);
				return matchData;
			}
//			 Exception logged in repository
			catch (DatabaseException e) {
				return null;
			}
	}
	
	private CompetitorMatchAnalysisData getCompetitorMatchAnalysisData(String competitorPractiScoreId, String matchPractiScoreId) {
		try {
			
			CompetitorResultData competitorResultData = 
					competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
			competitorResultDataService.setCompetitorStagePercentages(competitorResultData);
			competitorResultData.setMatch(null);
			
			Competitor competitor = competitorService.findByPractiScoreReferences(matchPractiScoreId, competitorPractiScoreId);
			
			List<StageResultDataLine> stageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitor(competitor);
			
			// Remove circular references before stringifying to JSON
			for (StageResultDataLine line : stageResultDataLines) {
				line.getCompetitor().setMatch(null);
				line.setStageResultData(null);
			}
			
			// Map result lines to stage practiScoreId's
			Map<String, StageResultDataLine> resultMap = new HashMap<String, StageResultDataLine>();
			
			for (Stage stage : competitor.getMatch().getStages()) {
				StageResultDataLine lineForStage = null;
				for (StageResultDataLine line : stageResultDataLines) {
					if (line.getScoreCard() != null && line.getScoreCard().getStage() != null) {
						if (line.getScoreCard().getStage().getPractiScoreId().equals(stage.getPractiScoreId())) {
							lineForStage = line;
							lineForStage.getScoreCard().setStage(null);
						}
					}
				}
				resultMap.put(stage.getPractiScoreId(), lineForStage);
			}

			// Get error cost table lines
			List<ScoreCard> cards = new ArrayList<ScoreCard>();
			for (Stage stage : competitor.getMatch().getStages()) {
				ScoreCard card = competitorResultData.getScoreCards().get(stage.getId());
				if (card != null) {
					cards.add(card);
				}
			}
			
			Map<String, ErrorCostTableLine> errorCostMap = CompetitorErrorCostDataService.getErrorCostTableLines(competitor.getMatch(), competitor, cards);
			return new CompetitorMatchAnalysisData(competitorResultData, resultMap, errorCostMap);
		}
		//	 Exception logged in repository
		catch (DatabaseException e) {
			return null;
		}
	}
}
