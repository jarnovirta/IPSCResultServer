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
	
	public CompetitorResultData findByCompetitorAndMatch(Competitor competitor, Match match) 
			throws DatabaseException {
		List<ScoreCard> cards = scoreCardService.findByCompetitorAndMatch(competitor, match);
		Map<Long, ScoreCard> scoreCards = new HashMap<Long, ScoreCard>();
		for (ScoreCard card : cards) {
			scoreCards.put(card.getStage().getId(), card);
		}

		CompetitorResultData resultData = new CompetitorResultData();
		resultData.setScoreCards(scoreCards);
		resultData.setCompetitor(competitorService.getOne(competitor.getId()));
		resultData.setMatch(matchService.getOne(match.getId()));
		resultData.setStatistics();
		
		return resultData;
	}
}
