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
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
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
				
				CompetitorMatchAnalysisData competitorMatchAnalysisData = getCompetitorMatchAnalysisData(competitorPractiScoreId, 
						matchPractiScoreId);
								
				CompetitorMatchAnalysisData compareToCompetitorMatchAnalysisData = getCompetitorMatchAnalysisData(compareToCompetitorId, 
						matchPractiScoreId);
				
				// Remove unnecessary data before stringifying to JSON
				removeUnnecessaryStageResultLineData(new ArrayList<StageResultDataLine>(competitorMatchAnalysisData.getStageResultDataLines().values()));
				removeUnnecessaryStageResultLineData(new ArrayList<StageResultDataLine>(compareToCompetitorMatchAnalysisData.getStageResultDataLines().values()));
				
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
					competitorResultDataService.getCompetitorResultData(competitorPractiScoreId, matchPractiScoreId);
			
			
			Competitor competitor = competitorResultData.getCompetitor();
			
			// Get stage result lines mapped to stage's PractiScoreId
			Map<String, StageResultDataLine> resultMap = getStageResultDataLines(competitorResultData); 

			// Get error cost table lines
			Map<String, ErrorCostTableLine> errorCostMap = CompetitorErrorCostDataService.getErrorCostTableLines(matchService.findByPractiScoreId(matchPractiScoreId), 
					competitor, new ArrayList<ScoreCard>(competitorResultData.getScoreCards().values()));
			
			// Get competitor stage result percentages for competitor's division
			Map<Integer, Double> stagePercentages = getStagePercentagesMap(competitorResultData, new ArrayList<StageResultDataLine>(resultMap.values()));
			
			// Get competitor stage result percentages for combined division
			Map<Integer, Double> combinedStagePercentages = null; 
			if (competitorResultData.getMatch().getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
				List<StageResultDataLine> combinedDivStageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitorAndDivision(competitor, 
						Constants.COMBINED_DIVISION);
				combinedStagePercentages = getStagePercentagesMap(competitorResultData, combinedDivStageResultDataLines);
			}
						 
			return new CompetitorMatchAnalysisData(competitor, resultMap, errorCostMap, stagePercentages, combinedStagePercentages);
		}
		//	 Exception logged in repository
		catch (DatabaseException e) {
			return null;
		}
	}
	
	private Map<String, StageResultDataLine> getStageResultDataLines(CompetitorResultData competitorResultData) {
		try {
			Map<String, StageResultDataLine> resultMap = new HashMap<String, StageResultDataLine>();
			List<StageResultDataLine> stageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitor(competitorResultData.getCompetitor());
			
			for (Stage stage : competitorResultData.getMatch().getStages()) {
				StageResultDataLine lineForStage = null;
				for (StageResultDataLine line : stageResultDataLines) {
					if (line.getScoreCard() != null && line.getScoreCard().getStage() != null) {
						if (line.getScoreCard().getStage().getPractiScoreId().equals(stage.getPractiScoreId())) {
							lineForStage = line;
						}
					}
				}
				resultMap.put(stage.getPractiScoreId(), lineForStage);
			}
			return resultMap;
		}
//		 Exception logged in repository
		catch (Exception e) {
			return null;
		}
	}

	public Map<Integer, Double> getStagePercentagesMap(CompetitorResultData resultData, List<StageResultDataLine> stageResultDataLines) {
			Map<Integer, Double> stagePercentages = new HashMap<Integer, Double>();
			for (ScoreCard card : resultData.getScoreCards().values()) {
				Double percentage = null;
				for (StageResultDataLine line : stageResultDataLines) {
					if (line != null && line.getScoreCard() != null && line.getScoreCard().getStage().getId().equals(card.getStage().getId())) {
						percentage = line.getStageScorePercentage();

					}
				}
				stagePercentages.put(card.getStage().getStageNumber(), percentage);
			}
			return stagePercentages;
	}

	private void removeUnnecessaryStageResultLineData(List<StageResultDataLine> lines) {
		if (lines == null) return;
		for (StageResultDataLine line : lines) {
			if (line == null) continue;
			if (line.getScoreCard() != null) {
				line.getScoreCard().setStage(null);
				line.getScoreCard().setCompetitor(null);
			}
			line.setStageResultData(null);
		}
	}
}
