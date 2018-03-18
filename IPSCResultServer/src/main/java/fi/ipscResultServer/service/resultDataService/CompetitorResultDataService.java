package fi.ipscResultServer.service.resultDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public CompetitorResultData findByCompetitorAndMatch(String competitorId, String matchId) 
			throws DatabaseException {
		List<ScoreCard> cards = scoreCardService.findByCompetitorAndMatch(competitorId, matchId);
		Map<String, ScoreCard> scoreCards = new HashMap<String, ScoreCard>();
		for (ScoreCard card : cards) {
			scoreCards.put(card.getStage().getId(), card);
		}

		CompetitorResultData resultData = new CompetitorResultData();
		resultData.setScoreCards(scoreCards);
		resultData.setCompetitor(competitorService.getOne(competitorId));
		resultData.setMatch(matchService.getOne(matchId));
		
		return resultData;
	}
}
