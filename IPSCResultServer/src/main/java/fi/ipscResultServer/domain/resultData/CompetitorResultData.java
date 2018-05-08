package fi.ipscResultServer.domain.resultData;

import java.util.Map;

import javax.persistence.Transient;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;

public class CompetitorResultData {

	private Match match; 
	
	private Competitor competitor;
	
	// ScoreCard instances mapped to Stage id's
	private Map<Long, ScoreCard> scoreCards;
	
	@Transient
	private int aHitsSum;
	
	@Transient
	private double aHitsPercentage;
	
	@Transient
	private int cHitsSum;
	
	@Transient
	private double cHitsPercentage;
	
	@Transient
	private int dHitsSum;
	
	@Transient
	private double dHitsPercentage;
	
	@Transient
	private int missSum;
	
	@Transient
	private double missPercentage;
	
	@Transient
	private int noshootHitsSum;
	
	@Transient
	private double noshootPercentage;
	
	@Transient 
	private int pointsSum; 
	
	@Transient
	private int proceduralPenaltiesSum;
	
	@Transient
	private double totalTime; 
		
	@Transient
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
		double totalHitfactor = 0;
		for (ScoreCard card : scoreCards.values()) {
			aHitsSum += card.getaHits();
			cHitsSum += card.getcHits();
			dHitsSum += card.getdHits();
			missSum += card.getMisses();
			pointsSum += card.getPoints();
			noshootHitsSum += card.getNoshootHits();
			totalTime += card.getTime();
			totalHitfactor += card.getHitFactor();
		}
		int totalShots = aHitsSum + cHitsSum + dHitsSum + missSum + noshootHitsSum;
		if (totalShots == 0) return;
		aHitsPercentage = (double) aHitsSum / (double) totalShots * 100.0;
		cHitsPercentage = (double) cHitsSum / (double) totalShots * 100.0;
		dHitsPercentage = (double) dHitsSum / (double) totalShots * 100.0;
		missPercentage = (double) missSum / (double) totalShots * 100.0;
		noshootPercentage = (double) noshootHitsSum / (double) totalShots * 100.0;
		if (scoreCards.size() > 0) hitFactorAverage = totalHitfactor / scoreCards.size();
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

	public double getNoshootPercentage() {
		return noshootPercentage;
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

}
