package fi.ipsc_result_server.domain.ResultData;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.ScoreCard;

@Entity
public class StageResultDataLine implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne
	private StageResultData stageResultData;
	
	@ManyToOne
	private Competitor competitor;
	
	private int stageScorePercentage;
	
	private int stagePoints;
	
	private int stageRank;
		
	@OneToOne
	private ScoreCard scoreCard;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public int getStageScorePercentage() {
		return stageScorePercentage;
	}

	public void setStageScorePercentage(int stageScorePercentage) {
		this.stageScorePercentage = stageScorePercentage;
	}

	public int getStagePoints() {
		return stagePoints;
	}

	public void setStagePoints(int stagePoints) {
		this.stagePoints = stagePoints;
	}

	public int getStageRank() {
		return stageRank;
	}

	public void setStageRank(int stageRank) {
		this.stageRank = stageRank;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public StageResultData getStageResultData() {
		return stageResultData;
	}

	public void setStageResultData(StageResultData stageResultData) {
		this.stageResultData = stageResultData;
	}

	public ScoreCard getScoreCard() {
		return scoreCard;
	}

	public void setScoreCard(ScoreCard scoreCard) {
		this.scoreCard = scoreCard;
	}
	
}
