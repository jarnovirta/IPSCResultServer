package fi.ipscResultServer.domain.statistics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;

@Entity
public class CompetitorStatistics implements Serializable, Comparable<CompetitorStatistics> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@OneToOne
	private Match match;
		
	@ManyToOne
	private Competitor competitor;

	private double matchTime;
	
	private int aHits;
	
	private double aHitPercentage;
	
	private int cHits;
	
	private int dHits;
	
	private int misses;
	
	private int proceduralPenalties;
	
	private int additionalPenalties;
	
	private int noShootHits;
	
	private int sumOfPoints;
	
	private int divisionRank;
	
	private double divisionPoints;
	
	private double divisionScorePercentage;
	
	public CompetitorStatistics() { }
	
	public CompetitorStatistics(Competitor competitor, Match match) { 
		this.competitor = competitor;
		this.match = match;
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public int compareTo(CompetitorStatistics competitorStatisticsLine) {
	    final int EQUAL = 0;
	    if (this == competitorStatisticsLine) return EQUAL;
	    if (this.divisionRank < competitorStatisticsLine.getDivisionRank()) return -1;
	    return 1;
	}
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(double matchTime) {
		this.matchTime = matchTime;
	}

	public int getaHits() {
		return aHits;
	}

	public void setaHits(int aHits) {
		this.aHits = aHits;
	}

	public double getaHitPercentage() {
		return aHitPercentage;
	}

	public void setaHitPercentage(double aHitPercentage) {
		this.aHitPercentage = aHitPercentage;
	}

	public int getcHits() {
		return cHits;
	}

	public void setcHits(int cHits) {
		this.cHits = cHits;
	}

	public int getdHits() {
		return dHits;
	}

	public void setdHits(int dHits) {
		this.dHits = dHits;
	}

	public int getMisses() {
		return misses;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public int getProceduralPenalties() {
		return proceduralPenalties;
	}

	public void setProceduralPenalties(int proceduralPenalties) {
		this.proceduralPenalties = proceduralPenalties;
	}

	public int getNoShootHits() {
		return noShootHits;
	}

	public void setNoShootHits(int noShootHits) {
		this.noShootHits = noShootHits;
	}

	public int getSumOfPoints() {
		return sumOfPoints;
	}

	public void setSumOfPoints(int sumOfPoints) {
		this.sumOfPoints = sumOfPoints;
	}

	public int getDivisionRank() {
		return divisionRank;
	}

	public void setDivisionRank(int divisionRank) {
		this.divisionRank = divisionRank;
	}

	public double getDivisionPoints() {
		return divisionPoints;
	}

	public void setDivisionPoints(double divisionPoints) {
		this.divisionPoints = divisionPoints;
	}

	public double getDivisionScorePercentage() {
		return divisionScorePercentage;
	}

	public void setDivisionScorePercentage(double divisionScorePercentage) {
		this.divisionScorePercentage = divisionScorePercentage;
	}

	public int getAdditionalPenalties() {
		return additionalPenalties;
	}

	public void setAdditionalPenalties(int additionalPenalties) {
		this.additionalPenalties = additionalPenalties;
	}

}
