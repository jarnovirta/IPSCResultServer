package fi.ipscResultServer.controller.matchAnalysis;

import fi.ipscResultServer.domain.ScoreCard;

public class ErrorCostTableLine {
	private ScoreCard scoreCard;
	private int stageValuePercentage;
	private double aTime = -1;
	private double cCost = -1;
	private double dCost = -1;
	private double proceduralPenaltyAndNoShootCost = -1;
	private double missCost = -1;
	public ScoreCard getScoreCard() {
		return scoreCard;
	}
	public void setScoreCard(ScoreCard scoreCard) {
		this.scoreCard = scoreCard;
	}
	public int getStageValuePercentage() {
		return stageValuePercentage;
	}
	public void setStageValuePercentage(int stageValuePercentage) {
		this.stageValuePercentage = stageValuePercentage;
	}
	public double getaTime() {
		return aTime;
	}
	public void setaTime(double aTime) {
		this.aTime = aTime;
	}
	public double getcCost() {
		return cCost;
	}
	public void setcCost(double cCost) {
		this.cCost = cCost;
	}
	public double getdCost() {
		return dCost;
	}
	public void setdCost(double dCost) {
		this.dCost = dCost;
	}
	public double getProceduralPenaltyAndNoShootCost() {
		return proceduralPenaltyAndNoShootCost;
	}
	public void setProceduralPenaltyAndNoShootCost(double proceduralPenaltyAndNoShootCost) {
		this.proceduralPenaltyAndNoShootCost = proceduralPenaltyAndNoShootCost;
	}
	public double getMissCost() {
		return missCost;
	}
	public void setMissCost(double missCost) {
		this.missCost = missCost;
	}

	
}
