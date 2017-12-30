package fi.ipsc_result_server.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchScore {

	@JsonProperty("match_id")
	String matchId;
	@JsonProperty("match_scores")
	List<StageScore> stageScores;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public List<StageScore> getStageScores() {
		return stageScores;
	}

	public void setStageScores(List<StageScore> stageScores) {
		this.stageScores = stageScores;
	}

}
