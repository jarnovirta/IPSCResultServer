package fi.ipscResultServer.domain.practiScore;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PractiScoreMatchScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("match_scores")
	private List<PractiScoreStageScore> stageScores;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PractiScoreStageScore> getStageScores() {
		return stageScores;
	}

	public void setStageScores(List<PractiScoreStageScore> stageScores) {
		this.stageScores = stageScores;
	}

}
