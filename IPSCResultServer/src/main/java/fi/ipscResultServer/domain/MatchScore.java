package fi.ipscResultServer.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("match_id")
	private String matchId;

	private Match match;
	
	@JsonProperty("match_scores")
	private List<StageScore> stageScores;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<StageScore> getStageScores() {
		return stageScores;
	}

	public void setStageScores(List<StageScore> stageScores) {
		this.stageScores = stageScores;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

}
