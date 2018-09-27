package fi.ipscResultServer.domain.resultData;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.ScoreCard;

public class StageResultDataLine {

	private Long id;

	private StageResultData stageResultData;

	private Competitor competitor;
	
	private double stageScorePercentage = 0.0;
	
	private double stagePoints = 0.0;
	
	private int stageRank;
		
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

	public double getStageScorePercentage() {
		return stageScorePercentage;
	}

	public void setStageScorePercentage(double stageScorePercentage) {
		this.stageScorePercentage = stageScorePercentage;
	}

	public double getStagePoints() {
		return stagePoints;
	}

	public void setStagePoints(double stagePoints) {
		this.stagePoints = stagePoints;
	}

	public int getStageRank() {
		return stageRank;
	}

	public void setStageRank(int stageRank) {
		this.stageRank = stageRank;
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
