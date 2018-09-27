package fi.ipscResultServer.domain.practiScore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fi.ipscResultServer.domain.Match;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PractiScoreMatchData {

	private Match match;
	private PractiScoreMatchScore matchScore;
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public PractiScoreMatchScore getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(PractiScoreMatchScore matchScore) {
		this.matchScore = matchScore;
	}
}
