package fi.ipscResultServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StageResultDataService;
import fi.ipscResultServer.service.StageService;

@Service
public class StageResultDataServiceImpl implements StageResultDataService {
	
	@Autowired
	ScoreCardService scoreCardService;
	
	@Autowired
	StageService stageService;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	public StageResultData getStageResultListing(String matchPractiScoreId, String stagePractiScoreId, String division) {
		Stage stage = stageService.getOne(stageService.getIdByPractiScoreReference(matchPractiScoreId, stagePractiScoreId));
		Long matchId = matchService.getIdByPractiScoreId(matchPractiScoreId);
		stage.setMatch(matchService.getOne(matchId, true));
		List<ScoreCard> cards = scoreCardService.findByStageAndDivision(stage.getId(), division, false);
		setCompetitorsToCards(cards, stage.getMatch().getCompetitors());
		finalizeResultDataForView(cards, division);
		
		return new StageResultData(stage, division, cards);
		
	}
	private void setCompetitorsToCards(List<ScoreCard> cards, List<Competitor> competitors) {
		for (ScoreCard card : cards) {
			for (Competitor comp : competitors) {
				if (comp.getId().equals(card.getCompetitorId())) {
					card.setCompetitor(comp);
					break;
				}
			}
		}
	}
	private void finalizeResultDataForView(List<ScoreCard> cards, String division) {
		double topPoints = -1;
		for (ScoreCard card : cards) {
			// Set stage score to be shown in view
			double stagePoints;
			if (division.equals(Constants.COMBINED_DIVISION)) stagePoints =  card.getCombinedDivisionStagePoints();
			else stagePoints = card.getStagePoints();
			
			card.setInViewStagePoints(stagePoints);
			
			// Set score percentage
			if (stagePoints > topPoints) topPoints = stagePoints;
			if (topPoints > 0) card.setScorePercentage(stagePoints / topPoints * 100);
		}
	}
}
