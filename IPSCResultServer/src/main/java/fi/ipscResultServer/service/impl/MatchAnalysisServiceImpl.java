package fi.ipscResultServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.controller.matchAnalysis.CompetitorMatchAnalysisData;
import fi.ipscResultServer.controller.matchAnalysis.ErrorCostDataGenerator;
import fi.ipscResultServer.controller.matchAnalysis.ErrorCostTableLine;
import fi.ipscResultServer.controller.matchAnalysis.MatchAnalysis;
import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchAnalysisService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;

@Service
public class MatchAnalysisServiceImpl implements MatchAnalysisService {

	@Autowired
	private ScoreCardService scoreCardService;
	
	@Autowired
	private CompetitorService competitorService;
	
	@Autowired
	private MatchService matchService;
	
	public MatchAnalysis getMatchAnalysisData(String matchPractiScoreId, 
			String competitorPractiScoreId, String compareToCompetitorPractiScoreId) {
		Match match = matchService.getOne(matchService.getIdByPractiScoreId(matchPractiScoreId), true);
		CompetitorMatchAnalysisData competitorData = getCompetitorMatchAnalysisData(match, competitorPractiScoreId);
		CompetitorMatchAnalysisData compareToCompetitorData = getCompetitorMatchAnalysisData(match, 
				compareToCompetitorPractiScoreId);
		return new MatchAnalysis(match, competitorData, compareToCompetitorData);
	}

	public CompetitorMatchAnalysisData getCompetitorMatchAnalysisData(Match match, String competitorPractiScoreId) {
		Competitor competitor = competitorService.findByPractiScoreReferences(match.getPractiScoreId(), competitorPractiScoreId);
		List<ScoreCard> cards = scoreCardService.findByCompetitor(competitor.getId(), false);
		for (ScoreCard card : cards) {
			card.setCompetitor(competitor);
			for (Stage stage : match.getStages()) {
				if (stage.getId().equals(card.getStageId())) {
					card.setStage(stage);
				}
			}
		}
		List<ErrorCostTableLine> errorLines = ErrorCostDataGenerator.getErrorCostTableLines(competitor, cards);
		return new CompetitorMatchAnalysisData(competitor, cards, errorLines);	
	}

}
