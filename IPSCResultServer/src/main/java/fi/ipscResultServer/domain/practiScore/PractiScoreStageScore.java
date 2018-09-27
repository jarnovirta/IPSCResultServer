package fi.ipscResultServer.domain.practiScore;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PractiScoreStageScore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("stage_uuid")
	private String stagePractiScoreId;	
	
	@JsonProperty("stage_number")
	private int stageNumber;
	
	private Stage stage;
	
	@JsonProperty("stage_stagescores")
	private List<ScoreCard> scoreCards;
	
	private PractiScoreMatchScore matchScore;


	public String getStagePractiScoreId() {
		return stagePractiScoreId;
	}

	public void setStagePractiScoreId(String stagePractiScoreId) {
		this.stagePractiScoreId = stagePractiScoreId;
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

	public PractiScoreMatchScore getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(PractiScoreMatchScore matchScore) {
		this.matchScore = matchScore;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
