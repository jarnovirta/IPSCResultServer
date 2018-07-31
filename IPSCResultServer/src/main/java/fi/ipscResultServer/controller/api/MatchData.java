package fi.ipscResultServer.controller.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchScore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchData {

	private Match match;
	private MatchScore matchScore;
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public MatchScore getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(MatchScore matchScore) {
		this.matchScore = matchScore;
	}
}
