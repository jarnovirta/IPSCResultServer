package fi.ipscResultServer.service.resultDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;

@Service
public class CompetitorResultDataService {

	@Autowired
	ScoreCardService scoreCardService;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	public CompetitorResultData findByCompetitorAndMatchPractiScoreIds(String competitorPractiScoreId, String matchPractiScoreId) 
			throws DatabaseException {
		List<ScoreCard> cards = scoreCardService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
		Map<Long, ScoreCard> scoreCards = new HashMap<Long, ScoreCard>();
		for (ScoreCard card : cards) {
			scoreCards.put(card.getStage().getId(), card);
		}

		Competitor competitor = competitorService.findByPractiScoreReferences(matchPractiScoreId, competitorPractiScoreId);
		Match match = matchService.findByPractiScoreId(matchPractiScoreId);
		CompetitorResultData resultData = new CompetitorResultData();
		resultData.setScoreCards(scoreCards);
		resultData.setCompetitor(competitorService.getOne(competitor.getId()));
		resultData.setMatch(matchService.getOne(match.getId()));
		resultData.setStatistics();
		
		return resultData;
	}
	
	public void setCompetitorStagePercentages(CompetitorResultData resultData) {
		resultData.setStagePercentages(getStagePercentages(resultData, resultData.getCompetitor().getDivision()));
		if (resultData.getMatch().getDivisionsWithResults() != null && resultData.getMatch().getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
			resultData.setCombinedDivStagePercentages(getStagePercentages(resultData, Constants.COMBINED_DIVISION));
		}
	}
	public Map<Integer, Double> getStagePercentages(CompetitorResultData resultData, String division) {
		try {
			Map<Integer, Double> stagePercentages = new HashMap<Integer, Double>();
			List<StageResultDataLine> stageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitorAndDivision(resultData.getCompetitor(), division);
			for (ScoreCard card : resultData.getScoreCards().values()) {
				Double percentage = null;
				for (StageResultDataLine line : stageResultDataLines) {
					if (line.getScoreCard().getStage().getId().equals(card.getStage().getId())) {
						percentage = line.getStageScorePercentage();
					}
				}
				stagePercentages.put(card.getStage().getStageNumber(), percentage);
			}
			return stagePercentages;
		}
		catch (DatabaseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
