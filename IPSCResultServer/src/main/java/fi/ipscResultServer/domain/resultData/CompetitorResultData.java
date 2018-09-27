package fi.ipscResultServer.domain.resultData;

import java.util.Map;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;

public class CompetitorResultData {

	private Match match; 
	
	private Competitor competitor;
	
	// ScoreCard instances mapped to Stage id's
	private Map<Long, ScoreCard> scoreCards;
	
	// Competitor result percentages per stage for competitor's division
	private Map<Integer, Double> stagePercentages;
	
	// Competitor result percentages per stage for combined division
	private Map<Integer, Double> combinedDivStagePercentages;
	
	private int aHitsSum;
	
	private double aHitsPercentage;
	
	private int cHitsSum;
	
	private double cHitsPercentage;
	
	private int dHitsSum;
	
	private double dHitsPercentage;
	
	private int missSum;
	
	private double missPercentage;
	
	private int noshootHitsSum;
	
	private int pointsSum; 
	
	private int proceduralPenaltiesSum;
	
	private double totalTime; 
	
	private double hitFactorSum;
		
	private double hitFactorAverage;
	

	public Map<Long, ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(Map<Long, ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public void setStatistics() {
		
		aHitsSum = 0;
		cHitsSum = 0;
		dHitsSum = 0;
		missSum = 0;
		noshootHitsSum = 0;
		totalTime = 0.0;
		proceduralPenaltiesSum = 0;
		pointsSum = 0;
		hitFactorSum = 0;
		for (ScoreCard card : scoreCards.values()) {
			aHitsSum += card.getaHits();
			cHitsSum += card.getcHits();
			dHitsSum += card.getdHits();
			missSum += card.getMisses();
			pointsSum += card.getPoints();
			noshootHitsSum += card.getNoshootHits();
			totalTime += card.getTime();
			hitFactorSum += card.getHitFactor();
			proceduralPenaltiesSum += card.getProceduralPenalties();
		}
		int totalShots = aHitsSum + cHitsSum + dHitsSum + missSum + noshootHitsSum;
		if (totalShots == 0) return;
		aHitsPercentage = (double) aHitsSum / (double) totalShots * 100.0;
		cHitsPercentage = (double) cHitsSum / (double) totalShots * 100.0;
		dHitsPercentage = (double) dHitsSum / (double) totalShots * 100.0;
		missPercentage = (double) missSum / (double) totalShots * 100.0;
		if (scoreCards.size() > 0) hitFactorAverage = hitFactorSum / scoreCards.size();
	}
	public int getaHitsSum() {
		return aHitsSum;
	}

	public double getaHitsPercentage() {
		return aHitsPercentage;
	}

	public int getcHitsSum() {
		return cHitsSum;
	}

	public double getcHitsPercentage() {
		return cHitsPercentage;
	}
	
	public int getdHitsSum() {
		return dHitsSum;
	}

	public double getdHitsPercentage() {
		return dHitsPercentage;
	}

	public int getMissSum() {
		return missSum;
	}

	public double getMissPercentage() {
		return missPercentage;
	}

	public int getNoshootHitsSum() {
		return noshootHitsSum;
	}

	public double getHitFactorAverage() {
		return hitFactorAverage;
	}

	public int getPointsSum() {
		return pointsSum;
	}

	public int getProceduralPenaltiesSum() {
		return proceduralPenaltiesSum;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public double getHitFactorSum() {
		return hitFactorSum;
	}

	public Map<Integer, Double> getStagePercentages() {
		return stagePercentages;
	}

	public void setStagePercentages(Map<Integer, Double> stagePercentages) {
		this.stagePercentages = stagePercentages;
	}

	public Map<Integer, Double> getCombinedDivStagePercentages() {
		return combinedDivStagePercentages;
	}

	public void setCombinedDivStagePercentages(Map<Integer, Double> combinedDivStagePercentages) {
		this.combinedDivStagePercentages = combinedDivStagePercentages;
	}
	
}