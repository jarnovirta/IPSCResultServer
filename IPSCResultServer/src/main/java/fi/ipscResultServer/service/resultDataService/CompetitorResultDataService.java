package fi.ipscResultServer.service.resultDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
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
	
	public CompetitorResultData getCompetitorResultData(String competitorPractiScoreId, String matchPractiScoreId) 
			throws DatabaseException {
		List<ScoreCard> cards = scoreCardService.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);
				
		Map<Long, ScoreCard> scoreCards = new HashMap<Long, ScoreCard>();
		for (ScoreCard card : cards) {
			scoreCards.put(card.getStage().getId(), card);
		}

		Competitor competitor = competitorService.findByPractiScoreReferences(matchPractiScoreId, competitorPractiScoreId);
		if (competitor == null) {
			System.out.println("Competitor " + competitorPractiScoreId + " match " + matchPractiScoreId);
		}
		Match match = matchService.findByPractiScoreId(matchPractiScoreId);
		CompetitorResultData resultData = new CompetitorResultData();
		resultData.setScoreCards(scoreCards);
		resultData.setCompetitor(competitorService.getOne(competitor.getId()));
		resultData.setMatch(matchService.getOne(match.getId()));
		resultData.setStatistics();
		
		return resultData;
	}
	

}
