package fi.ipscResultServer.domain.ResultData;

import java.util.Map;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;

public class CompetitorResultData {

	private Match match; 
	
	private Competitor competitor;
	
	// ScoreCard instances mapped to Stage id's
	private Map<String, ScoreCard> scoreCards; 

	public Map<String, ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(Map<String, ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}
}
