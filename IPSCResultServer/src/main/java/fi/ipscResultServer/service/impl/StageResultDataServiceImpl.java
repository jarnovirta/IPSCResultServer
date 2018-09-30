package fi.ipscResultServer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
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
	
	public StageResultData getStageResultListing(Stage stage, String division) {
		
		// Get scorecards for stage and division
		List<ScoreCard> scoreCards = null;
		
		if (division.equals(Constants.COMBINED_DIVISION)) scoreCards = scoreCardService.findByStage(stage.getId());
		else scoreCards = scoreCardService.findByStageAndDivision(stage.getId(), division);
		
		StageResultData stageResultData = new StageResultData(stage, division);
		List<StageResultDataLine> dataLines = new ArrayList<StageResultDataLine>();
		stageResultData.setDataLines(dataLines);
		stageResultData.getStage().setMatch(matchService.getOne(stage.getMatchId(), false));
		stageResultData.getStage().getMatch().setStages(stageService.findByMatch(stage.getMatchId()));
		
		double topHitFactor = -1;
		double topPoints = -1;
		if (scoreCards.size() > 0) {
			topHitFactor = scoreCards.get(0).getHitFactor();
			topPoints = scoreCards.get(0).getPoints();
		}
		
		int rank = 1;
		
		// Generate result line for each scorecard
		
		List<Competitor> competitors = competitorService.findByMatch(stage.getMatchId());
		for (ScoreCard scoreCard : scoreCards) {
			
			// Discard results for DQ'ed competitor
			Competitor competitor = getCompetitor(competitors, scoreCard.getCompetitorId());
			if (competitor.isDisqualified()) continue;
			
			StageResultDataLine resultDataLine = new StageResultDataLine();
			resultDataLine.setStageRank(rank++);
			resultDataLine.setStageResultData(stageResultData);
			resultDataLine.setScoreCard(scoreCard);
			resultDataLine.setCompetitor(competitor);
			
			// Set stage points, rank and result percentage
			if (topHitFactor > 0) resultDataLine.setStagePoints((scoreCard.getHitFactor() / topHitFactor) * stage.getMaxPoints());
			else {
				resultDataLine.setStagePoints(stage.getMaxPoints());
			}
			scoreCard.setStageRank(rank);
			resultDataLine.setStageScorePercentage(resultDataLine.getStagePoints() / topPoints * 100);
			
			dataLines.add(resultDataLine);
		}
		return stageResultData;
	}
	private Competitor getCompetitor(List<Competitor> competitors, Long id) {
		for (Competitor comp : competitors) if (comp.getId().equals(id)) return comp;
		return null;
	}
}
