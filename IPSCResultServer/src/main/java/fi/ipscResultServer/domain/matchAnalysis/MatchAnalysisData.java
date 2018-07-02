package fi.ipscResultServer.domain.matchAnalysis;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;

@Component
public class MatchAnalysisData {
	private Competitor competitor;
	private Match match;
	public Competitor getCompetitor() {
		return competitor;
	}
	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
}
