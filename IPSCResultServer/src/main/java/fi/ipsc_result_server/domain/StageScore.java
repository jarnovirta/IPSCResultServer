package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StageScore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("stage_uuid")
	private String stageId;	
	
	@JsonProperty("stage_number")
	private int stageNumber;
	
	private Stage stage;
	
	@JsonProperty("stage_stagescores")
	private List<ScoreCard> scoreCards;
	
	private MatchScore matchScore;

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(List<ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}

	public MatchScore getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(MatchScore matchScore) {
		this.matchScore = matchScore;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
