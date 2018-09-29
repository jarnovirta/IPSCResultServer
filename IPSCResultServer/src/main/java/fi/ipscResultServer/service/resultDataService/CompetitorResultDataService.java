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
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StageService;

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
	
	@Autowired
	StageService stageService;
	
	public CompetitorResultData getCompetitorResultData(Long competitorId) {
		List<ScoreCard> cards = scoreCardService.findByCompetitor(competitorId);
		
		Map<Long, ScoreCard> scoreCards = new HashMap<Long, ScoreCard>();
		if (cards != null) {
			for (ScoreCard card : cards) {
				scoreCards.put(card.getStage().getId(), card);
			}
		}
		
		Competitor competitor = competitorService.getOne(competitorId);
		Match match = matchService.lazyGetOne(competitor.getMatchId());
		match.setStages(stageService.findByMatch(match.getId()));

		return new CompetitorResultData(match, competitor, scoreCards);
	}
}
